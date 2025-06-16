package org.example.springdatajpa;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public interface ProducerRepository extends JpaRepository<Producer, UUID>, JpaSpecificationExecutor<Producer> {
    //@EntityGraph(value = "Producer.address")
    List<Producer> findByName(String name);

    @Query("select p from Producer p where p.email ilike :email")
    Stream<Producer> findByEmail(String email);

    @Query(value = "select * from producer where email ilike :email", nativeQuery = true)
    Stream<Producer> findByEmail2(String email);

    Stream<Producer> findByHomeState(String homeState);

    int deleteByBusinessUnitsContainingAndHomeStateIsLikeIgnoreCaseAndNameStartingWith(List<BusinessUnit> businessUnits, String homeState, String name);

    List<Name> getNameByHomeState(String homeState);

    List<NameRecord> getNameRecordByHomeState(String homeState);

    @Query("update Producer p set p.email = upper(p.email)")
    @Modifying(
            flushAutomatically = true,
            clearAutomatically = true
    )
    void updateEmail();

    @Query("select p from Producer p left join fetch p.businessUnits left join fetch p.address left join fetch p.licenses")
    List<Producer> findByNameFixed(String name);
}
