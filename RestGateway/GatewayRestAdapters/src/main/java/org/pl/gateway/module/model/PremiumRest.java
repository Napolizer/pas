package org.pl.gateway.module.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class PremiumRest extends ClientTypeRest implements Serializable {
    public PremiumRest() {
        setFactor(0.9);
        setMaxRepairs(5);
        setType("PREMIUM");
    }
}
