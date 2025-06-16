package org.example.springdatajpa.onetomany;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import org.example.springdatajpa.Example;
import org.example.springdatajpa.License;
import org.example.springdatajpa.Producer;
import org.example.springdatajpa.ProducerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class OneToManyTest extends Example {

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
    @Transactional
    void oneToMany() {
        entityManager.getTransaction().begin();
        for (int i = 0; i < 5; i++) {
            Producer producer = new Producer();
            producer.setName("jordan");
            producer.setEmail(FAKER.internet().emailAddress());
            producer.setHomeState(FAKER.address().state());
            for (int j = 0; j < 5; j++) {
                License license = new License();
                license.setLicenseNumber("num");
                producer.addLicense(license);
            }
            producerRepository.save(producer);
        }

        entityManager.getTransaction().commit();
        System.out.println("New transaction begins here \n---------");
        entityManager.getTransaction().begin();

        producerRepository.findByName("jordan").size();
    }

    @Test
    @Transactional
    void deleting() {
        for (int i = 0; i < 5; i++) {
            Producer producer = new Producer();
            producer.setName("jordan");
            producer.setEmail(FAKER.internet().emailAddress());
            producer.setHomeState(FAKER.address().state());
            for (int j = 0; j < 5; j++) {
                License license = new License();
                license.setLicenseNumber("num");
                producer.addLicense(license);
            }
            producerRepository.save(producer);
        }
        producerRepository.flush();

        Producer producer = producerRepository.findByName("jordan").get(0);
        License license = producer.getLicenses().stream().findFirst().get();
        producer.removeLicense(license);

        producerRepository.flush();
    }
}
