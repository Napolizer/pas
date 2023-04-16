package org.pl.repair.module.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class VipRest extends ClientTypeRest implements Serializable {
    public VipRest() {
        setFactor(0.8);
        setMaxRepairs(10);
        setType("VIP");
    }
}
