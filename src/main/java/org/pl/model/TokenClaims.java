package org.pl.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenClaims {
    private String username;
    private String group;
}
