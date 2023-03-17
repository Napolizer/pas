package org.pl.model;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pl.adapters.ClientTypeRestAdapter;
import org.pl.model.exceptions.ClientRestException;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientRest implements EntityRest, Serializable {
    private UUID id;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotNull
    private Boolean archive;
    @NotNull
    @Min(0)
    private Double balance;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String phoneNumber;
    @NotNull
    @JsonbTypeAdapter(ClientTypeRestAdapter.class)
    private ClientTypeRest clientType;
    @NotNull
    @Valid
    private AddressRest address;
    @NotNull
    @Valid
    private ClientAccessTypeRest clientAccessType;

    public double calculateDiscount(int price) throws ClientRestException {
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
    public boolean isArchive() {
        return archive;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @JsonbTransient
    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientRest client = (ClientRest) o;

        return Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
