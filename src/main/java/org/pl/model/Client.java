package org.pl.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.pl.exceptions.ClientException;

import java.util.Objects;
import java.util.UUID;

@Data
@Builder
public class Client implements Entity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private UUID id;
    @NotBlank
    private String username;
    @NotNull
    private Boolean archive;
    @NotNull
    private Double balance;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String phoneNumber;
    @NotNull
    private ClientType clientType;
    @NotNull
    private Address address;
    @NotNull
    private ClientAccessType clientAccessType;

    public double calculateDiscount(int price) throws ClientException {
        return getClientType().calculateDiscount(price);
    }

    public void changeBalance(double amount) {
        setBalance(getBalance() + amount);
    }

    @Override
    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    @Override
    public boolean isArchive() {
        return getArchive();
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        return Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
