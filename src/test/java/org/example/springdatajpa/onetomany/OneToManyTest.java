package org.example.springdatajpa.onetomany;

import org.example.springdatajpa.Example;
import org.example.springdatajpa.License;
import org.example.springdatajpa.Producer;
import org.example.springdatajpa.ProducerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

public class OneToManyTest extends Example {

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void oneToMany() {
        transactionTemplate.executeWithoutResult(_ -> {
            for (int i = 0; i < 1; i++) {
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
        });

        transactionTemplate.executeWithoutResult(_ -> {
            producerRepository.findAll().forEach(producer -> System.out.println(producer.getLicenses()));
        });
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void nPlus1() {
        transactionTemplate.executeWithoutResult(_ -> {
            for (int i = 0; i < 10; i++) {
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
        });

        transactionTemplate.executeWithoutResult(_ -> {
            producerRepository.findAll().forEach(producer -> producer.getLicenses().size());
            //producerRepository.findByNameFixed("jordan").forEach(producer -> producer.getLicenses().size());
        });
    }
}
