package org.example.springdatajpa.querybyexample;

import org.example.springdatajpa.Example;
import org.example.springdatajpa.Producer;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.transaction.annotation.Transactional;

public class QueryByExampleTest extends Example {

    @Test
    @Transactional
    void test() {
        Producer exampleProducer = new Producer();
        exampleProducer.setName("jordan");
        exampleProducer.setHomeState("MI");
        exampleProducer.setNpn("100");

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
            .withMatcher("npn", ExampleMatcher.GenericPropertyMatchers.startsWith())
            .withIgnoreCase();

        org.springframework.data.domain.Example<Producer> producerExample = org.springframework.data.domain.Example.of(exampleProducer, matcher);

        producerRepository.findAll(producerExample);
    }
}
