package org.pl.model;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.pl.model.exceptions.ClientSoapException;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@XmlRootElement(name = "client")
@XmlAccessorType(XmlAccessType.FIELD)
public class ClientSoap implements EntitySoap, Serializable {
    @XmlAttribute
    private UUID id;
    @NotBlank
    @XmlElement(required = true)
    private String username;
    @NotBlank
    @XmlElement(required = true)
    private String password;
    @NotNull
    @XmlElement(required = true)
    private Boolean archive;
    @NotNull
    @Min(0)
    @XmlElement(required = true)
    private Double balance;
    @NotBlank
    @XmlElement(required = true)
    private String firstName;
    @NotBlank
    @XmlElement(required = true)
    private String lastName;
    @NotBlank
    @XmlElement(required = true)
    private String phoneNumber;
    @NotNull
    @XmlElement(required = true)
    private ClientTypeSoap clientType;
    @NotNull
    @Valid
    @XmlElement(required = true)
    private AddressSoap address;
    @NotNull
    @Valid
    @XmlElement(required = true)
    private ClientAccessTypeSoap clientAccessType;

    public ClientSoap() {
    }

    public ClientSoap(UUID id,
                      String username,
                      String password,
                      Boolean archive,
                      Double balance,
                      String firstName,
                      String lastName,
                      String phoneNumber,
                      ClientTypeSoap clientType,
                      AddressSoap address,
                      ClientAccessTypeSoap clientAccessType) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.archive = archive;
        this.balance = balance;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.clientType = clientType;
        this.address = address;
        this.clientAccessType = clientAccessType;
    }

    public double calculateDiscount(int price) throws ClientSoapException {
        return getClientType().calculateDiscount(price);
    }

    public void changeBalance(double amount) {
        setBalance(getBalance() + amount);
    }

    @Override
    public void setArchive(Boolean archive) {
        this.archive = archive;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Boolean getArchive() {
        return archive;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ClientTypeSoap getClientType() {
        return clientType;
    }

    public void setClientType(ClientTypeSoap clientType) {
        this.clientType = clientType;
    }

    public AddressSoap getAddress() {
        return address;
    }

    public void setAddress(AddressSoap address) {
        this.address = address;
    }

    public ClientAccessTypeSoap getClientAccessType() {
        return clientAccessType;
    }

    public void setClientAccessType(ClientAccessTypeSoap clientAccessType) {
        this.clientAccessType = clientAccessType;
    }

    @JsonbTransient
    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientSoap client = (ClientSoap) o;

        return Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("username", username)
                .append("password", password)
                .append("archive", archive)
                .append("balance", balance)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("phoneNumber", phoneNumber)
                .append("clientType", clientType)
                .append("address", address)
                .append("clientAccessType", clientAccessType)
                .toString();
    }
}
