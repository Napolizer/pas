package org.pl.model;

import jakarta.json.bind.annotation.JsonbTransient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pl.adapter.data.model.UserAccessTypeEnt;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEnt {
    private UUID id;
    private String username;
    private String password;
    private Boolean archive;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private UserAccessTypeEnt userAccessType;

    @JsonbTransient
    public String getPassword() {
        return password;
    }
}
