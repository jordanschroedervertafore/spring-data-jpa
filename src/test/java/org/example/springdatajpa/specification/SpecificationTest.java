package org.example.springdatajpa.specification;

import org.example.springdatajpa.Example;
import org.example.springdatajpa.Producer;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

public class SpecificationTest extends Example {


    @Test
    @Transactional
    void test() {
        producerRepository.findAll(inState("MI").and(licenseCountGreaterThan(10)).or(inState("IL").and(licenseCountGreaterThan(20))));
    }

    private static Specification<Producer> licenseCountGreaterThan(int count) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(criteriaBuilder.size(root.get("licenses")), 10);
    }

    private static Specification<Producer> inState(String state) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("homeState"), state);
    }
}
