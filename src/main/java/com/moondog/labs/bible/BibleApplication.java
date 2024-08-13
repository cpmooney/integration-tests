package com.moondog.labs.bible;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackageClasses = BibleClient.class)
public class BibleApplication {

    public static void main(String[] args) {
        SpringApplication.run(BibleApplication.class, args);
    }
}
