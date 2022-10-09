package org.pl.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Vip extends ClientType {
    public Vip() {
        setFactor(0.8f);
        setMaxRepairs(10);
        setTypeName("Vip");
    }
}
