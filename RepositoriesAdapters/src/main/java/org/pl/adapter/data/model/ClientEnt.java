package org.pl.adapter.data.model;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.pl.adapter.data.exceptions.ClientEntException;


import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Access(AccessType.FIELD)
public class ClientEnt implements EntityEnt, Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;
    @Column(unique = true)
    private String username;
    private String password;
    private Boolean archive;
    private Double balance;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private ClientTypeEnt clientTypeEnt;
    @Embedded
    private AddressEnt addressEnt;
    private ClientAccessTypeEnt clientAccessType;

    public double calculateDiscount(int price) throws ClientEntException {
        return getClientTypeEnt().calculateDiscount(price);
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

        ClientEnt clientEnt = (ClientEnt) o;

        return Objects.equals(id, clientEnt.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
