package org.pl.model;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class Address {
    private String city;
    private String number;
    private String street;
}
