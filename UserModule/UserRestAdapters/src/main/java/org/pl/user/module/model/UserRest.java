package org.pl.user.module.model;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserRest implements Serializable {
    private UUID id;
    private String username;
    private String password;
    private Boolean archive;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private UserAccessType userAccessType;
    private Address address;
}
