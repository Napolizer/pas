package org.pl.user.module.model;

import jakarta.json.bind.annotation.JsonbTransient;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
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
