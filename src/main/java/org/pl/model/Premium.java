package org.pl.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Premium extends ClientType {
    public Premium() {
        setFactor(0.9f);
        setMaxRepairs(5);
        setTypeName("Premium");
    }
}
