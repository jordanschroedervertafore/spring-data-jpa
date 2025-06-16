package org.example.springdatajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface ProducerRepository extends JpaRepository<Producer, UUID>, JpaSpecificationExecutor<Producer> {

    //@Query("select p from Producer p left join fetch p.businessUnits")
    List<Producer> findByName(String name);

    @Query("select p from Producer p left join fetch p.businessUnits")
    List<Producer> findByNameFixed(String name);

    List<Name> getNameByHomeState(String homeState);

    List<NameRecord> getNameRecordByHomeState(String homeState);

    @Query("update Producer p set p.email = upper(p.email)")
    @Modifying(
            flushAutomatically = true,
            clearAutomatically = true
    )
    void updateEmail();
}
