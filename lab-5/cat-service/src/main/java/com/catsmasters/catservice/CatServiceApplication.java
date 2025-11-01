package com.catsmasters.catservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class CatServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CatServiceApplication.class, args);
    }
}