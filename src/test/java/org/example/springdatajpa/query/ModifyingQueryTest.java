package org.example.springdatajpa.query;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.springdatajpa.Example;
import org.example.springdatajpa.Producer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.UUID;

public class ModifyingQueryTest extends Example {

    private UUID producerId;

    @BeforeEach
    void setUp() {
        producerId = createProducers(1).getFirst();
    }

    @Test
    @Transactional
    void modifyingQuery() {
        Producer producer = producerRepository.findById(producerId).orElseThrow();
        producerRepository.updateEmail();
        producer = producerRepository.findById(producerId).orElseThrow();
        System.out.println(producer.getEmail());
    }
}
