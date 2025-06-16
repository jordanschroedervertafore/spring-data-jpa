package org.example.springdatajpa;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
public class BusinessUnit {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @Version
    private Integer version;

    @ManyToMany(mappedBy = "businessUnits")
    private Set<Producer> producers = new HashSet<>();

    @Override
    public String toString() {
        return "BusinessUnit{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", version=" + version +
               '}';
    }
}
