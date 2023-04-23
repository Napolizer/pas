package org.pl.gateway.module.model;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pl.gateway.module.jsonb.adapters.ClientTypeRestJsonbAdapter;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientRestWithPassword {
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
    @JsonbTypeAdapter(ClientTypeRestJsonbAdapter.class)
    private ClientTypeRest clientType;
    @NotNull
    @Valid
    private AddressRest address;
    @NotNull
    @Valid
    private ClientAccessTypeRest clientAccessType;


    public ClientRestWithPassword(ClientRest clientRest) {
        this.setId(clientRest.getId());
        this.setUsername(clientRest.getUsername());
        this.setPassword(clientRest.getPassword());
        this.setArchive(clientRest.getArchive());
        this.setBalance(clientRest.getBalance());
        this.setFirstName(clientRest.getFirstName());
        this.setLastName(clientRest.getLastName());
        this.setPhoneNumber(clientRest.getPhoneNumber());
        this.setClientType(clientRest.getClientType());
        this.setAddress(clientRest.getAddress());
        this.setClientAccessType(clientRest.getClientAccessType());
    }
}
