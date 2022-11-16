package org.pl.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Basic extends ClientType {
    public Basic() {
        setFactor(1.0f);
        setMaxRepairs(2);
        setTypeName("Basic");
    }
}
