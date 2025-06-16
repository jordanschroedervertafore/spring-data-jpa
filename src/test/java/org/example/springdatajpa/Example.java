package org.example.springdatajpa;

import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@SpringBootTest
//@TestPropertySource(properties = {
//    "spring.test.database.replace=none",
//    "spring.datasource.url=jdbc:tc:postgresql:16-alpine:///db"
//})
public class Example {

    @Autowired
    protected ProducerRepository producerRepository;

    @Autowired
    protected TransactionTemplate transactionTemplate;

    protected static final Faker FAKER = new Faker();

    protected List<UUID> createProducers(int count) {
        List<UUID> uuids = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Producer producer = new Producer();
            producer.setName(FAKER.name().fullName());
            producer.setEmail(FAKER.internet().emailAddress());
            producer.setHomeState(FAKER.address().stateAbbr());

            producerRepository.save(producer);

            uuids.add(producer.getId());
        }

        return uuids;
    }
}
