package org.pl.repair.module.adapter.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.pl.repair.module.adapter.data.model.exceptions.ClientEntException;


import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Data
@SuperBuilder
@Entity
@Access(AccessType.FIELD)
public abstract class ClientTypeEnt implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;
    protected Double factor;
    protected Integer maxRepairs;
    protected String type;

    public ClientTypeEnt() {

    }

    public double calculateDiscount(double price) throws ClientEntException {
        if (price < 0) {
            throw new ClientEntException(ClientEntException.CLIENT_TYPE_CALCULATE_DISCOUNT_EXCEPTION);
        }
        return price - (getFactor() * price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientTypeEnt that = (ClientTypeEnt) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
