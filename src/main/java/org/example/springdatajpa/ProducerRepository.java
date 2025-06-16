package org.example.springdatajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProducerRepository extends JpaRepository<Producer, UUID> {

    @Query("select p from Producer p left join fetch p.businessUnits")
    List<Producer> findByName(String name);
}
