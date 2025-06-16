package org.example.springdatajpa.specification;

import org.example.springdatajpa.Example;
import org.example.springdatajpa.Producer;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.data.jpa.domain.Specification.not;

public class SpecificationTest extends Example {

    private static Specification<Producer> licenseCountGreaterThan(int count) {
        return (root, _, criteriaBuilder) ->
            criteriaBuilder.greaterThan(criteriaBuilder.size(root.get("licenses")), count);
    }

    private static Specification<Producer> inState(String state) {
        return (root, _, criteriaBuilder) ->
            criteriaBuilder.equal(root.get("homeState"), state);
    }

    private static Specification<Producer> emailIsNull() {
        return (root, _, criteriaBuilder) ->
            criteriaBuilder.isNull(root.get("email"));
    }

    @Test
    @Transactional
    void test() {
        producerRepository.findAll(
            inState("MI").and(licenseCountGreaterThan(10)).and(not(emailIsNull()))
                .or(
                    inState("IL").and(licenseCountGreaterThan(20)).and(emailIsNull())));
    }
}
