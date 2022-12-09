package org.example;

import com.google.api.gax.paging.Page;
import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import com.google.cloud.logging.LogEntry;
import com.google.cloud.logging.LogEntryServerStream;
import com.google.cloud.logging.Logging;
import com.google.cloud.logging.LoggingOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class HelloWorld implements HttpFunction {
    private static final Gson gson = new Gson();

    //Logging options = LoggingOptions.getDefaultInstance()
    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            final Logging gcloudLogs = LoggingOptions.getDefaultInstance().getService();
            LogEntryServerStream stream;

            JsonObject inputBody = gson.fromJson(httpRequest.getReader(), JsonObject.class);
            IntegrationRequest input = gson.fromJson(inputBody, IntegrationRequest.class);

            System.out.println(input);
            System.out.println(input.getBody());

            System.out.println("---------------------------------");

//            System.out.println(httpRequest.getHeaders());
//            System.out.println(httpRequest.getReader());
//            System.out.println(httpRequest.getMethod());
//            //System.out.println(httpRequest.getParts());
//            System.out.println(httpRequest.getPath());
//            System.out.println(httpRequest.getQuery());
//            //System.out.println(httpRequest.getQuery().get());
//            System.out.println(httpRequest.getUri());
//            System.out.println(httpRequest.getCharacterEncoding());
//            //System.out.println(httpRequest.getContentType());
//            System.out.println(httpRequest.getContentLength());
//            System.out.println(httpRequest.getClass());
//            System.out.println(httpRequest.hashCode());
//            System.out.println(httpRequest);

            String recordEvent = inputBody.get("event").getAsString();
            System.out.println("---------------------------------");
            System.out.println(inputBody);

//          Below was an attempt to extract function's execution ID from logs in gcp

//            stream = gcloudLogs.tailLogEntries();
//            System.out.println("start streaming..");
//            for (LogEntry log : stream) {
//                System.out.println(log);
//                // cancel infinite streaming after receiving first entry
//                //stream.cancel();
//            }

            //System.out.println("done streaming");

            //System.out.println("Execution ID:: " + new ConfigUtils().getExecutionId(gcloudLogs));
            System.out.println("Execution ID:: " + getExecutionId(gcloudLogs));

            // Open given file in append mode by creating an
            // object of BufferedWriter class
            BufferedWriter out = new BufferedWriter(
                    new FileWriter("record.txt", true));

            // Writing on output stream
            out.write("\n-- " + recordEvent);

            BufferedWriter writer = httpResponse.getWriter();
            writer.write("\n-- Record event :: " + recordEvent + " :: dumped into text file records!");

            System.out.println("\n-- Record event :: " + recordEvent + " :: dumped into text file records!");

            System.out.println(httpResponse);

            // Closing the connection
            writer.close();
            out.close();
        }

        // Catch block to handle the exceptions
        catch (IOException e) {

            // Display message when exception occurs
            System.out.println("exception occurred" + e);
        }
    }

    private String getExecutionId(Logging logs) {
        Page<LogEntry> entries = logs.listLogEntries();
        String id = null;

        for (LogEntry logName : entries.iterateAll()) {
            id = logName.getLabels().get("execution_id");
//            if (id != null) {
//                break;
//            }
        }
        return id;
    }
}