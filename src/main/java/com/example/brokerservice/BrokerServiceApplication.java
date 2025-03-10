package com.example.brokerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class BrokerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BrokerServiceApplication.class, args);
    }

}
