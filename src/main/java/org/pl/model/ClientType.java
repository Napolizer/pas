package org.pl.model;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.pl.exceptions.ClientException;

import java.util.UUID;

@Data
public abstract class ClientType {
    @Id
    private UUID id;
    @NotNull
    protected Float factor;
    @NotNull
    protected Integer maxRepairs;
    @NotBlank
    protected String typeName;

    public double calculateDiscount(int price) throws ClientException {
        if (price < 0) {
            throw new ClientException(ClientException.CLIENT_TYPE_CALCULATE_DISCOUNT_EXCEPTION);
        }
        return price - (getFactor() * price);
    }
}
