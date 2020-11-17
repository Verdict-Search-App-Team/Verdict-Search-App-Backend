package com.szalay.opencourtwebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class OpencourtwebappApplication {

    private static boolean read = false;
    private static boolean scrape = false;

    public static void main(String[] args) {

        if (args.length >= 1){
            for (String arg : args) {
                if (new File(arg).isDirectory()) {
                    InitialDataFilePaths.DECISIONS_FILESYSTEM_LOCATION.setFilePath(arg);
                }
                if (arg.equals("read")) {
                    read = true;
                    System.out.println("read is " + read);
                }
                if (arg.equals("scrape")) {
                    scrape = true;
                    System.out.println("scrape is " + scrape);
                }
            }
        }

        //Start Spring app
        SpringApplication.run(OpencourtwebappApplication.class, args);

        // Start scraper, if set
        if (scrape) {
            ScraperHU.start();
        }

    }

    public static boolean isRead() {
        return read;
    }

}
