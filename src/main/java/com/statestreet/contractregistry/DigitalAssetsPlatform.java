package com.statestreet.contractregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DigitalAssetsPlatform {

    public static void main(String[] args) {
        SpringApplication.run(DigitalAssetsPlatform.class, args);
    }
}
