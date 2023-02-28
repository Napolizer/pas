package org.pl.adapter.data.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCredentialsEnt implements Serializable {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
