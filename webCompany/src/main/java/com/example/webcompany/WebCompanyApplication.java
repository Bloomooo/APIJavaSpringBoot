package com.example.webcompany;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.example.webcompany", "Controllers" })
public class WebCompanyApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebCompanyApplication.class, args);
    }

}
