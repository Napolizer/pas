package org.pl.model;

import lombok.Data;
import org.pl.exceptions.ClientException;

@Data
public abstract class ClientType {
    protected float factor;
    protected int maxRepairs;
    protected String typeName;

    public double calculateDiscount(int price) throws ClientException {
        if (price < 0) {
            throw new ClientException(ClientException.CLIENT_TYPE_CALCULATE_DISCOUNT_EXCEPTION);
        }
        return price - (getFactor() * price);
    }
}
