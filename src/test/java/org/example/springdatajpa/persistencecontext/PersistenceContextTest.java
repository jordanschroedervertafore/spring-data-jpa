package org.example.springdatajpa.persistencecontext;

import org.example.springdatajpa.BusinessUnit;
import org.example.springdatajpa.Example;
import org.example.springdatajpa.Producer;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class PersistenceContextTest extends Example {

    @Test
    @Transactional(propagation = Propagation.NEVER)
    @Commit
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

        // Entities retrieved from the repository are in a managed state
        System.out.println("Managed state");
        transactionTemplate.executeWithoutResult(_ -> {
            Producer managedProducer = producerRepository.findById(producer.getId()).orElseThrow();
            // Note that since managedProducer is already cached, the following select won't be executed
            managedProducer = producerRepository.findById(producer.getId()).orElseThrow();

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
