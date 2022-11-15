package org.pl.model;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Builder;

import java.util.UUID;

@Data
@Builder
public class Address {
    @Id
    private UUID id;
    @NotBlank
    private String city;
    @NotBlank
    private String number;
    @NotBlank
    private String street;
}
