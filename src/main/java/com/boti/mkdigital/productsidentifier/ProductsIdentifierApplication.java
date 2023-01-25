package com.boti.mkdigital.productsidentifier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableFeignClients
@EnableScheduling
@SpringBootApplication
public class ProductsIdentifierApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductsIdentifierApplication.class, args);
    }

}
