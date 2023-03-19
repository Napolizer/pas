package org.pl.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

@XmlRootElement(name = "address")
@XmlAccessorType(XmlAccessType.FIELD)
public class AddressSoap implements Serializable {
    @NotBlank
    @Size(min=2)
    @XmlElement(required = true)
    private String city;
    @NotBlank
    @XmlElement(required = true)
    private String number;
    @NotBlank
    @XmlElement(required = true)
    private String street;

    public AddressSoap() {
    }

    public AddressSoap(String city, String number, String street) {
        this.city = city;
        this.number = number;
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        AddressSoap that = (AddressSoap) o;

        return new EqualsBuilder().append(city, that.city).append(number, that.number).append(street, that.street).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(city).append(number).append(street).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("city", city)
                .append("number", number)
                .append("street", street)
                .toString();
    }
}
