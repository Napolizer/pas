package org.pl.repair.module.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressRest implements Serializable {
    @NotBlank
    @Size(min=2)
    private String city;
    @NotBlank
    private String number;
    @NotBlank
    private String street;
}
