package com.sovtech.test.graphql.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "com.sovtech.test.graphql.server")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}