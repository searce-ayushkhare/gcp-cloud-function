package org.example;

import com.google.api.gax.paging.Page;
import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import com.google.cloud.logging.LogEntry;
import com.google.cloud.logging.Logging;
import com.google.cloud.logging.LoggingOptions;
import org.utils.ConfigUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class NamasteDunia implements HttpFunction {

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
        try {
            final Logging gcloudLogs = LoggingOptions.getDefaultInstance().getService();
            final File myObj = new File("record.txt");
            final Scanner myReader = new Scanner(myObj);

            StringBuilder data = new StringBuilder("");

            while (myReader.hasNextLine()) {
                String str = myReader.nextLine();
                data.append(str);
                System.out.println(str);
            }

            BufferedWriter writer = httpResponse.getWriter();
            writer.write(data.toString());

//            System.out.println(new ConfigUtils().getExecutionId(gcloudLogs));
            System.out.println("Execution ID:: " + getExecutionId(gcloudLogs));

            writer.close();
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private String getExecutionId(Logging logs) {
        //Page<LogEntry> entries = logs.listLogEntries();
        Page<LogEntry> entries = (Page<LogEntry>) logs.tailLogEntries();
        String id = null;
        for (LogEntry logName : entries.iterateAll()) {
            id = logName.getLabels().get("execution_id");
            if (id != null) {
                break;
            }
        }
        return id;
    }
}
