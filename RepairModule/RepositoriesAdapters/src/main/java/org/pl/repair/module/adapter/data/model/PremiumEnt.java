package org.pl.repair.module.adapter.data.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
public class PremiumEnt extends ClientTypeEnt implements Serializable {
    public PremiumEnt() {
        setFactor(0.9);
        setMaxRepairs(5);
        setType("PREMIUM");
    }
}
