package org.pl.repair.module.converters;

import jakarta.enterprise.context.ApplicationScoped;
import org.pl.repair.module.model.ClientAccessType;
import org.pl.repair.module.model.ClientAccessTypeRest;

@ApplicationScoped
public class ClientAccessTypeConverter {
    public ClientAccessTypeRest convert(ClientAccessType clientAccessType) {
        return switch (clientAccessType) {
            case ADMINISTRATORS -> ClientAccessTypeRest.ADMINISTRATORS;
            case EMPLOYEES -> ClientAccessTypeRest.EMPLOYEES;
            case USERS -> ClientAccessTypeRest.USERS;
        };
    }

    public ClientAccessType convert(ClientAccessTypeRest clientAccessTypeEnt) {
        return switch (clientAccessTypeEnt) {
            case ADMINISTRATORS -> ClientAccessType.ADMINISTRATORS;
            case EMPLOYEES -> ClientAccessType.EMPLOYEES;
            case USERS -> ClientAccessType.USERS;
        };
    }
}
