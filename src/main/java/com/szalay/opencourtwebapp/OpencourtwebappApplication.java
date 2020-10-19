package com.szalay.opencourtwebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EntityScan("com.szalay.opencourtwebapp")
@SpringBootApplication
public class OpencourtwebappApplication {


    public static void main(String[] args) {
        SpringApplication.run(OpencourtwebappApplication.class, args);
        ScraperHU.start();
    }

}
