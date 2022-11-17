package org.pl.model;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
public class Vip extends ClientType {
    public Vip() {
        setFactor(0.8f);
        setMaxRepairs(10);
        setTypeName("Vip");
    }
}
