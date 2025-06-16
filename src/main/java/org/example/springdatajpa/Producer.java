package org.example.springdatajpa;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@Entity
@NamedEntityGraph(
        name = "Producer.address",
        attributeNodes = {
                @NamedAttributeNode("address")
        }
)
public class Producer {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String email;
    private String homeState;
    private String npn;

    @Version
    private Instant version;

    @ManyToMany(cascade = {
        CascadeType.MERGE,
        CascadeType.PERSIST
    })
    @JoinTable(name = "producer_business_unit",
        joinColumns = @JoinColumn(name = "producer_id"),
        inverseJoinColumns = @JoinColumn(name = "business_unit_id"))
    private Set<BusinessUnit> businessUnits = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
        mappedBy = "producer")
//    @JoinColumn(name = "producer_id")
    private Set<License> licenses = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "producer")
    private Address address;

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
               "address=" + address +
               ", version=" + version +
               ", npn='" + npn + '\'' +
               ", homeState='" + homeState + '\'' +
               ", email='" + email + '\'' +
               ", name='" + name + '\'' +
               ", id=" + id +
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
        license.setProducer(this);
    }

    public void removeLicense(License license) {
        licenses.remove(license);
        license.setProducer(null);
    }
}
