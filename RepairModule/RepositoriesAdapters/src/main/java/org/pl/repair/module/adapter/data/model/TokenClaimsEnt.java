package org.pl.repair.module.adapter.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class TokenClaimsEnt implements Serializable {
    private String username;
    private String group;
}
