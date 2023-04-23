package org.pl.gateway.module.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.pl.gateway.module.model.exceptions.ClientRestException;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Data
@SuperBuilder
public abstract class ClientTypeRest implements Serializable {
    private UUID id;
    @NotNull
    @Min(0)
    @Max(1)
    protected Double factor;
    @NotNull
    @Min(value = 1)
    protected Integer maxRepairs;
    @NotBlank
    protected String type;

    public ClientTypeRest() {

    }

    public double calculateDiscount(double price) throws ClientRestException {
        if (price < 0) {
            throw new ClientRestException(ClientRestException.CLIENT_TYPE_CALCULATE_DISCOUNT_EXCEPTION);
        }
        return price - (getFactor() * price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientTypeRest that = (ClientTypeRest) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
