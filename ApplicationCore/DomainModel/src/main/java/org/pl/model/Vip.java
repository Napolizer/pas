package org.pl.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Vip extends ClientType implements Serializable {
    public Vip() {
        setFactor(0.8);
        setMaxRepairs(10);
        setType("VIP");
    }
}
