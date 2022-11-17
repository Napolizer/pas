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
public class Basic extends ClientType {
    public Basic() {
        setFactor(1.0f);
        setMaxRepairs(2);
        setTypeName("Basic");
    }
}
