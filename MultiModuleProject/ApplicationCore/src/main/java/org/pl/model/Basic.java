package org.pl.model;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
public class Basic extends ClientType implements Serializable {
    public Basic() {
        setFactor(1.0);
        setMaxRepairs(2);
        setType("BASIC");
    }
}
