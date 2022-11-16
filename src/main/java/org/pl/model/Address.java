package org.pl.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Builder;

import java.util.Objects;
import java.util.UUID;

@Data
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private UUID id;
    @NotBlank
    @Size(min=2)
    @Pattern(regexp = "^[^0-9]+$")
    private String city;
    @NotBlank
    @Size(min=9,max=9)
    @Pattern(regexp="(^$|[0-9]{9})")
    private String phoneNumber;
    @NotBlank
    private String street;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        return Objects.equals(id, address.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
