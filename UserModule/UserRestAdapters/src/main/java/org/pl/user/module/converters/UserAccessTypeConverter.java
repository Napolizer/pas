package org.pl.user.module.converters;

import jakarta.enterprise.context.ApplicationScoped;
import org.pl.user.module.model.UserAccessType;
import org.pl.user.module.model.UserAccessTypeRest;

@ApplicationScoped
public class UserAccessTypeConverter {
    public UserAccessTypeRest convert(UserAccessType userAccessType) {
        return switch (userAccessType) {
            case ADMINISTRATORS -> UserAccessTypeRest.ADMINISTRATORS;
            case EMPLOYEES -> UserAccessTypeRest.EMPLOYEES;
            case USERS -> UserAccessTypeRest.USERS;
        };
    }

    public UserAccessType convert(UserAccessTypeRest userAccessTypeRest) {
        return switch (userAccessTypeRest) {
            case ADMINISTRATORS -> UserAccessType.ADMINISTRATORS;
            case EMPLOYEES -> UserAccessType.EMPLOYEES;
            case USERS -> UserAccessType.USERS;
        };
    }
}
