package org.pl.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class Basic extends ClientType {
    public Basic() {
        setFactor(1.0f);
        setMaxRepairs(2);
        setTypeName("Basic");
    }
}
