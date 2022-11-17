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
    @Pattern(regexp = "^[^0-9]+$")
    private String city;
    @NotBlank
    @Size(min=1,max=9)
    private String number;
    @NotBlank
    private String street;
}
