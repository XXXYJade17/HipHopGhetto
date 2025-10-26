package com.xxxyjade.hiphopghetto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HiphopGhettoApplication {

    public static void main(String[] args) {
        SpringApplication.run(HiphopGhettoApplication.class, args);
    }

}
