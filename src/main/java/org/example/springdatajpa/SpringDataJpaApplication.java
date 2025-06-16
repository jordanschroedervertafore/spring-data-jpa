package org.example.springdatajpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;


@SpringBootApplication
public class SpringDataJpaApplication {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(SpringDataJpaApplication.class, args);
    }
}
