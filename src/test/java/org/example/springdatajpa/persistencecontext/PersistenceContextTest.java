package org.example.springdatajpa.persistencecontext;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.springdatajpa.BusinessUnit;
import org.example.springdatajpa.Example;
import org.example.springdatajpa.License;
import org.example.springdatajpa.Producer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class PersistenceContextTest extends Example {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void test() {
        // New/transient state
        Producer producer = new Producer();
        producer.setName(FAKER.name().fullName());
        BusinessUnit businessUnit = new BusinessUnit();
        businessUnit.setName("Test");
        producer.addBusinessUnit(new BusinessUnit());

        System.out.println("Managed state");
        // Managed
        transactionTemplate.executeWithoutResult(_ -> {
            producerRepository.save(producer);
            producer.setEmail("ABCDEF");
            producer.setNpn("123");
        });

        // Detached
        System.out.println("Detached state");
        producer.setHomeState("IL");

        // Entities retrieved from the repository are in a managed state
        System.out.println("Managed state");
        transactionTemplate.executeWithoutResult(_ -> {
            Producer managedProducer = producerRepository.findById(producer.getId()).orElseThrow();
            managedProducer.setEmail("hello");
        });

        System.out.println("Removed state");
        // Removed
        transactionTemplate.executeWithoutResult(_ -> {
            Producer managedProducer = producerRepository.findById(producer.getId()).orElseThrow();
            producerRepository.delete(managedProducer);
        });
    }
}
