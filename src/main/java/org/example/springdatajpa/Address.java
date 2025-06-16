package org.example.springdatajpa;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Address {

    @Id
    private UUID producerId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Producer producer;

    private String address;

    @Override
    public String toString() {
        return "Address{" +
               "address='" + address + '\'' +
               ", producerId=" + producerId +
               '}';
    }
}
