package com.neo4flix.movie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.neo4flix.movie", "com.neo4flix.common"})
public class MovieServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MovieServiceApplication.class, args);
    }
}
