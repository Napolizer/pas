package org.pl.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Access(AccessType.FIELD)
public class Address {
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
}
