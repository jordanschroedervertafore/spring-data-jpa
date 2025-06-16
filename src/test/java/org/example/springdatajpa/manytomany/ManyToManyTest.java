package org.example.springdatajpa.manytomany;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.springdatajpa.BusinessUnit;
import org.example.springdatajpa.Example;
import org.example.springdatajpa.Producer;
import org.example.springdatajpa.ProducerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class ManyToManyTest extends Example {

    @Autowired
    private ProducerRepository producerRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TransactionTemplate transactionTemplate;

    private static final Random RANDOM = new Random();

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
                for (int j = 0; j < 50; j++) {
                    producer.addBusinessUnit(businessUnits.get(RANDOM.nextInt(0, businessUnits.size())));
                }

                producerRepository.save(producer);
            }
            entityManager.flush();
            entityManager.clear();
        });
    }
//
//    @Test
//    @Transactional(propagation = Propagation.NEVER)
//    void nplus1() {
//        transactionTemplate.executeWithoutResult(_ -> {
//            System.out.println("New transaction begins here \n---------");
//            var producers = producerRepository.findByName("jordan");
//            producers.forEach(producer -> producer.getBusinessUnits().size());
//        });
//    }
//
//    @Test
//    @Transactional(propagation = Propagation.NEVER)
//    void nplus1Fixed() {
//        transactionTemplate.executeWithoutResult(_ -> {
//            System.out.println("New transaction begins here \n---------");
//            var producers = producerRepository.findByNameFixed("jordan");
//            producers.forEach(producer -> producer.getBusinessUnits().size());
//        });
//    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void dontUseLists() {
        transactionTemplate.executeWithoutResult(_ -> {
            System.out.println("New transaction begins here \n---------");
            var producers = producerRepository.findByNameFixed("jordan");
            Producer producer = producers.get(0);
            //producer.removeBusinessUnit(producer.getBusinessUnits().iterator().next());
            producer.addBusinessUnit(new BusinessUnit());
        });
    }
}
