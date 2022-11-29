package org.pl.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Access(AccessType.FIELD)
public class Address {
    @NotBlank
    @Size(min=2)
    private String city;
    @NotBlank
    private String number;
    @NotBlank
    private String street;
}
