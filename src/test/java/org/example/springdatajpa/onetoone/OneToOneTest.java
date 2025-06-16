package org.example.springdatajpa.onetoone;

import org.example.springdatajpa.Address;
import org.example.springdatajpa.BusinessUnit;
import org.example.springdatajpa.Example;
import org.example.springdatajpa.Producer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class OneToOneTest extends Example {

    @BeforeEach
    void setUp() {
        transactionTemplate.executeWithoutResult(_ -> {
            List<BusinessUnit> businessUnits = new ArrayList<>();
            for (int j = 0; j < 100; j++) {
                BusinessUnit businessUnit = new BusinessUnit();
                businessUnit.setName("Testing" + j);
                businessUnits.add(businessUnit);
            }

            for (int i = 0; i < 500; i++) {
                Producer producer = new Producer();
                producer.setName("jordan");
                producer.setEmail(FAKER.internet().emailAddress());
                producer.setHomeState(FAKER.address().state());
                for (int j = 0; j < 30; j++) {
                    producer.addBusinessUnit(businessUnits.get(RANDOM.nextInt(0, businessUnits.size())));
                }
                Address address = new Address();
                address.setAddress(FAKER.address().fullAddress());
                address.setProducer(producer);
                producer.setAddress(address);

                producerRepository.save(producer);
            }
        });
    }

//    @Test
//    @Transactional(propagation = Propagation.NEVER)
//    void test1() {
//        AtomicReference<UUID> producerId = new AtomicReference<>();
//        transactionTemplate.executeWithoutResult(_ -> {
//            producerId.set(createProducers(1).get(0));
//        });
//
//        transactionTemplate.executeWithoutResult(_ -> {
//            producerRepository.findById(producerId.get());
//        });
//    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void nPlus1() {
        System.out.println("Transaction starts here\n---------------");
        transactionTemplate.executeWithoutResult(_ -> {
            producerRepository.findByNameFixed("jordan");
        });
    }
}
