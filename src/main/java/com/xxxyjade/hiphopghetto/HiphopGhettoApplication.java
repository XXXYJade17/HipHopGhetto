package com.xxxyjade.hiphopghetto;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.xxxyjade.hiphopghetto.mapper")
@SpringBootApplication
public class HiphopGhettoApplication {

    public static void main(String[] args) {
        SpringApplication.run(HiphopGhettoApplication.class, args);
    }

}
