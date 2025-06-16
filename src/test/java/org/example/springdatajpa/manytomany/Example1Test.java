package org.example.springdatajpa.manytomany;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import org.example.springdatajpa.BusinessUnit;
import org.example.springdatajpa.Example;
import org.example.springdatajpa.Producer;
import org.example.springdatajpa.ProducerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class Example1Test extends Example {

    @Autowired
    private ProducerRepository producerRepository;

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Test
    void nplus1() {
        entityManager.getTransaction().begin();
        for (int i = 0; i < 50; i++) {
            Producer producer = new Producer();
            producer.setName("jordan");
            producer.setEmail(FAKER.internet().emailAddress());
            producer.setHomeState(FAKER.address().state());
            for (int j = 0; j < 10; j++) {
                BusinessUnit businessUnit = new BusinessUnit();
                businessUnit.setName("Testing" + j);
                producer.addBusinessUnit(businessUnit);
            }
            producerRepository.save(producer);
        }

        entityManager.getTransaction().commit();
        System.out.println("New transaction begins here \n---------");
        entityManager.getTransaction().begin();

        var producers = producerRepository.findByName("jordan");
        producers.forEach(producer -> producer.getBusinessUnits().forEach(System.out::println));
    }
}
