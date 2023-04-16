package org.pl.repair.module.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class BasicRest extends ClientTypeRest implements Serializable {
    public BasicRest() {
        setFactor(1.0);
        setMaxRepairs(2);
        setType("BASIC");
    }
}
