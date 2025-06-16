package org.example.springdatajpa.query;

import org.example.springdatajpa.Example;
import org.example.springdatajpa.Producer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public class ModifyingQueryTest extends Example {

    private UUID producerId;

    @BeforeEach
    void setUp() {
        transactionTemplate.executeWithoutResult(_ -> {
            Producer producer = new Producer();
            producer.setName("jordan");
            producer.setEmail("test@test.com");
            producer.setHomeState("MI");
            producerId = producerRepository.save(producer).getId();
        });
    }

    @Test
    @Transactional
    void customQueries() {
        List<Producer> producer = producerRepository.findByHomeState("MI").toList();
        //List<Producer> producer2 = producerRepository.findByEmail("MI").toList();
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void modifyingQuery() {
        transactionTemplate.executeWithoutResult(_ -> {
            Producer producer = producerRepository.findById(producerId).orElseThrow();
            System.out.println(producer.getEmail());
            producerRepository.updateEmail();
            producer = producerRepository.findById(producerId).orElseThrow();
            System.out.println(producer.getEmail());
        });
    }
}
