package org.example;

import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class NamasteDunia implements HttpFunction {

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
        try {
            File myObj = new File("record.txt");
            Scanner myReader = new Scanner(myObj);

            StringBuilder data = new StringBuilder("");

            while (myReader.hasNextLine()) {
                data.append(myReader.nextLine());
                System.out.println(data);
            }

            BufferedWriter writer = httpResponse.getWriter();
            writer.write(data.toString());

            writer.close();
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
