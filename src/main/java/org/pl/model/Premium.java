package org.pl.model;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Entity
public class Premium extends ClientType {
    public Premium() {
        setFactor(0.9f);
        setMaxRepairs(5);
        setTypeName("Premium");
    }
}
