package org.example.springdatajpa.projections;

import org.example.springdatajpa.Example;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

public class ProjectionTest extends Example {

    @BeforeEach
    void setUp() {
        createProducers(1);
    }

    @Test
    @Transactional
    void getNamesByHomeState() {
        producerRepository.getNameByHomeState("MI");
    }

    @Test
    @Transactional
    void getNameRecordByHomeState() {
        producerRepository.getNameRecordByHomeState("MI").forEach(name -> System.out.println(name.name()));
    }
}
