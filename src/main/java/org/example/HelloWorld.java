package org.example;

import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class HelloWorld implements HttpFunction {
    private static final Gson gson = new Gson();

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            JsonObject body = gson.fromJson(httpRequest.getReader(), JsonObject.class);
            String recordEvent = body.get("event").getAsString();

            // Open given file in append mode by creating an
            // object of BufferedWriter class
            BufferedWriter out = new BufferedWriter(
                    new FileWriter("record.txt", true));

            // Writing on output stream
            out.write("\n-- " + recordEvent);

            BufferedWriter writer = httpResponse.getWriter();
            writer.write("\n-- Record event :: " + recordEvent + " :: dumped into text file records!");

            System.out.println("\n-- Record event :: " + recordEvent + " :: dumped into text file records!");

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
}