package com.szalay.opencourtwebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EntityScan("com.szalay.opencourtwebapp")
@SpringBootApplication
public class OpencourtwebappApplication {


    public static void main(String[] args) {

        InitialDataFilePaths.DECISIONS_FILESYSTEM_LOCATION.setFilePath(args[0]);
        SpringApplication.run(OpencourtwebappApplication.class, args);

        //ScraperHU.start();


    }

}
