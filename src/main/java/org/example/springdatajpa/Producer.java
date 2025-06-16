package org.example.springdatajpa;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@Entity
public class Producer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String email;
    private String homeState;

    @ManyToMany(cascade = {
        CascadeType.MERGE,
        CascadeType.PERSIST,
    })
    @JoinTable(name = "producer_business_unit",
        joinColumns = @JoinColumn(name = "producer_id"),
        inverseJoinColumns = @JoinColumn(name = "business_unit_id"))
    private Set<BusinessUnit> businessUnits = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    mappedBy = "producer")
    @JoinColumn(name = "producer_id")
    private Set<License> licenses = new HashSet<>();

    @Version
    private Integer version;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Producer producer = (Producer) o;
        return Objects.equals(id, producer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Producer{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", email='" + email + '\'' +
               ", homeState='" + homeState + '\'' +
               ", version=" + version +
               '}';
    }

    public void addBusinessUnit(BusinessUnit businessUnit) {
        businessUnits.add(businessUnit);
        businessUnit.getProducers().add(this);
    }

    public void removeBusinessUnit(BusinessUnit businessUnit) {
        businessUnits.remove(businessUnit);
        businessUnit.getProducers().remove(this);
    }

    public void addLicense(License license) {
        licenses.add(license);
//        license.setProducer(this);
    }

    public void removeLicense(License license) {
        licenses.remove(license);
    }
}
