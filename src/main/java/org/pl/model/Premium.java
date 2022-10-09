package org.pl.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class Premium extends ClientType {
    public Premium() {
        setFactor(0.9f);
        setMaxRepairs(5);
        setTypeName("Premium");
    }
}
