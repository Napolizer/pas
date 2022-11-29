package org.pl.model;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
public class Premium extends ClientType {
    public Premium() {
        setFactor(0.9);
        setMaxRepairs(5);
        setType("PREMIUM");
    }
}
