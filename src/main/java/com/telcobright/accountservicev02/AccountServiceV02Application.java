package com.telcobright.accountservicev02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AccountServiceV02Application {

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceV02Application.class, args);
    }


}
