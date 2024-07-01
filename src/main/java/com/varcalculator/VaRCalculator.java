package com.varcalculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class VaRCalculator{

    public static void main(String[] args) {
        SpringApplication.run(VaRCalculator.class, args);
    }
}
