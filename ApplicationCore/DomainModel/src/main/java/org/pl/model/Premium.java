package org.pl.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Premium extends ClientType implements Serializable {
    public Premium() {
        setFactor(0.9);
        setMaxRepairs(5);
        setType("PREMIUM");
    }
}
