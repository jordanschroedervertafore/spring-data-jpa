package org.example.springdatajpa;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface LicenseRepository extends CrudRepository<License, UUID> {
}
