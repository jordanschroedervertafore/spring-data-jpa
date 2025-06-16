package org.example.springdatajpa;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Transactional
public class TestRepo {

    private final ProducerRepository producerRepository;

    public TestRepo(ProducerRepository producerRepository) {
        this.producerRepository = producerRepository;
    }

    @GetMapping(path = "/test")
    public void test() {
        UUID id = UUID.randomUUID();
        Producer entity = new Producer();
        entity.setId(id);
        producerRepository.saveAndFlush(entity);

        producerRepository.findAll().forEach(System.out::println);
        System.out.println(id);
        producerRepository.deleteById(id);

        Producer newProducer = new Producer();
        newProducer.setId(id);
        newProducer.setName("test");
        producerRepository.saveAndFlush(newProducer);
    }
}
