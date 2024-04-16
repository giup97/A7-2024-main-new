package com.example.db_setup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.T8.social", "com.T8.social.Controller", "com.T8.social.Service", "com.example"})
public class DbSetupApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbSetupApplication.class, args);
    }
}
