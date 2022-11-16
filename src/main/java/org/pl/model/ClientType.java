package org.pl.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Range;
import org.pl.exceptions.ClientException;

import java.util.Objects;
import java.util.UUID;

@Data
@SuperBuilder
public abstract class ClientType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    @NotNull
    @Range(min = 0, max = 1)
    protected Float factor;
    @NotNull
    @Min(value = 1)
    protected Integer maxRepairs;
    @NotBlank
    protected String typeName;

    public ClientType() {

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

        ClientType that = (ClientType) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
