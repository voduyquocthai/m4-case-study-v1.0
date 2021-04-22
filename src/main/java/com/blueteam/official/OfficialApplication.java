package com.blueteam.official;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.blueteam")
public class OfficialApplication {

    public static void main(String[] args) {
        SpringApplication.run(OfficialApplication.class, args);
    }

}
