package org.example.springdatajpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
public class License {
    @Id
    @GeneratedValue
    private UUID id;

    private String licenseNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producer_id")
    private Producer producer;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        License license = (License) o;
        return id != null && Objects.equals(id, license.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
