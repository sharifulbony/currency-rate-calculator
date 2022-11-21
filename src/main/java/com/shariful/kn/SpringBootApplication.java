package com.shariful.kn;
import org.springframework.boot.SpringApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@org.springframework.boot.autoconfigure.SpringBootApplication
@EnableAsync
public class SpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplication.class, args);
    }

}
