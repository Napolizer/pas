package org.pl.user.module.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class TokenClaims implements Serializable {
    private String username;
    private String group;
}