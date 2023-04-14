package org.pl.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.pl.model.exceptions.ClientException;
import org.pl.model.exceptions.ClientSoapException;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@XmlRootElement(name = "clientType")
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class ClientTypeSoap implements Serializable {
    @XmlAttribute
    private UUID id;
    @NotNull
    @Min(0)
    @Max(1)
    @XmlElement(required = true)
    protected Double factor;
    @NotNull
    @Min(value = 1)
    @XmlElement(required = true)
    protected Integer maxRepairs;
    @NotBlank
    @XmlElement(required = true)
    protected String type;

    public ClientTypeSoap() {

    }

    public ClientTypeSoap(UUID id, Double factor, Integer maxRepairs, String type) {
        this.id = id;
        this.factor = factor;
        this.maxRepairs = maxRepairs;
        this.type = type;
    }

    public double calculateDiscount(double price) throws ClientSoapException {
        if (price < 0) {
            throw new ClientSoapException(ClientException.CLIENT_TYPE_CALCULATE_DISCOUNT_EXCEPTION);
        }
        return price - (getFactor() * price);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Double getFactor() {
        return factor;
    }

    public void setFactor(Double factor) {
        this.factor = factor;
    }

    public Integer getMaxRepairs() {
        return maxRepairs;
    }

    public void setMaxRepairs(Integer maxRepairs) {
        this.maxRepairs = maxRepairs;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientTypeSoap that = (ClientTypeSoap) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("factor", factor)
                .append("maxRepairs", maxRepairs)
                .append("type", type)
                .toString();
    }
}
