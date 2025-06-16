package org.example.springdatajpa;

import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@SpringBootTest
//@TestPropertySource(properties = {
//    "spring.test.database.replace=none",
//    "spring.datasource.url=jdbc:tc:postgresql:16-alpine:///db"
//})
public class Example {

    protected static final Random RANDOM = new Random();
    protected static final Faker FAKER = new Faker();
    @Autowired
    protected ProducerRepository producerRepository;
    @Autowired
    protected TransactionTemplate transactionTemplate;
    @Autowired
    protected LicenseRepository licenseRepository;
    @Autowired
    protected AddressRepository addressRepository;
    @Autowired
    protected BusinessUnitRepository businessUnitRepository;

    protected List<UUID> createProducers(int count) {
        List<UUID> uuids = new ArrayList<>();

        transactionTemplate.executeWithoutResult(_ -> {
            List<BusinessUnit> businessUnits = new ArrayList<>();
            for (int j = 0; j < 100; j++) {
                BusinessUnit businessUnit = new BusinessUnit();
                businessUnit.setName("Testing" + j);
                businessUnits.add(businessUnit);
            }

            for (int i = 0; i < count; i++) {
                Producer producer = new Producer();
                producer.setName(FAKER.name().fullName());
                producer.setEmail(FAKER.internet().emailAddress());
                producer.setHomeState(FAKER.address().stateAbbr());

                for (int j = 0; j < 10; j++) {
                    producer.addBusinessUnit(businessUnits.get(RANDOM.nextInt(0, businessUnits.size())));
                }

                for (int j = 0; j < 30; j++) {
                    License license = new License();
                    license.setLicenseNumber(FAKER.random().hex());
                    producer.addLicense(license);
                }

                Address address = new Address();
                address.setAddress(FAKER.address().fullAddress());

                //producer.setAddress(address);
                address.setProducer(producer);

                producerRepository.save(producer);

                uuids.add(producer.getId());
            }

        });

        return uuids;
    }
}
