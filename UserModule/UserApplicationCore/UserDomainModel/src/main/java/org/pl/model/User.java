package org.pl.model;

import jakarta.json.bind.annotation.JsonbTransient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private UUID id;
    private String username;
    private String password;
    private Boolean archive;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private UserAccessType userAccessType;
    private Address address;

    @JsonbTransient
    public String getPassword() {
        return password;
    }
}
