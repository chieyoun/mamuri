package com.chieyoun.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class mamuriYearBookApplication {
    public static void main(String[] args) {
        SpringApplication.run(mamuriYearBookApplication.class, args);
    }
}
