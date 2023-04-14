package org.pl.adapter.data.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
public class VipEnt extends ClientTypeEnt implements Serializable {
    public VipEnt() {
        setFactor(0.8);
        setMaxRepairs(10);
        setType("VIP");
    }
}
