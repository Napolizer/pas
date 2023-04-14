package org.pl.adapter.data.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
public class BasicEnt extends ClientTypeEnt implements Serializable {
    public BasicEnt() {
        setFactor(1.0);
        setMaxRepairs(2);
        setType("BASIC");
    }
}
