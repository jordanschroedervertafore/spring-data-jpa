package org.example.springdatajpa;

import net.datafaker.Faker;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
//@TestPropertySource(properties = {
//    "spring.test.database.replace=none",
//    "spring.datasource.url=jdbc:tc:postgresql:16-alpine:///db"
//})
public class Example {

    protected static final Faker FAKER = new Faker();
}
