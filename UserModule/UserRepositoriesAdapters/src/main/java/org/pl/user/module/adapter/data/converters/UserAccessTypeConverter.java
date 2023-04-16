package org.pl.user.module.adapter.data.converters;

import jakarta.enterprise.context.ApplicationScoped;
import org.pl.user.module.adapter.data.model.UserAccessTypeEnt;
import org.pl.user.module.model.UserAccessType;

@ApplicationScoped
public class UserAccessTypeConverter {
    public UserAccessTypeEnt convert(UserAccessType userAccessType) {
        return switch (userAccessType) {
            case ADMINISTRATORS -> UserAccessTypeEnt.ADMINISTRATORS;
            case EMPLOYEES -> UserAccessTypeEnt.EMPLOYEES;
            case USERS -> UserAccessTypeEnt.USERS;
        };
    }

    public UserAccessType convert(UserAccessTypeEnt userAccessTypeEnt) {
        return switch (userAccessTypeEnt) {
            case ADMINISTRATORS -> UserAccessType.ADMINISTRATORS;
            case EMPLOYEES -> UserAccessType.EMPLOYEES;
            case USERS -> UserAccessType.USERS;
        };
    }
}
