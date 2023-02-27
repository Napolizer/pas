package org.pl.adapter.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Range;
import org.pl.exceptions.ClientException;


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
    @NotNull
    @Range(min = 0, max = 1)
    protected Double factor;
    @NotNull
    @Min(value = 1)
    protected Integer maxRepairs;
    @NotBlank
    protected String type;

    public ClientTypeEnt() {

    }

    public double calculateDiscount(double price) throws ClientException {
        if (price < 0) {
            throw new ClientException(ClientException.CLIENT_TYPE_CALCULATE_DISCOUNT_EXCEPTION);
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
