package org.example.springdatajpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BusinessUnitRepository  extends JpaRepository<BusinessUnit, UUID> {
}
