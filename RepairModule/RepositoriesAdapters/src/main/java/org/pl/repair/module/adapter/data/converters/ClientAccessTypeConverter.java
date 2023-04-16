package org.pl.repair.module.adapter.data.converters;

import jakarta.enterprise.context.ApplicationScoped;
import org.pl.repair.module.adapter.data.model.ClientAccessTypeEnt;
import org.pl.repair.module.model.ClientAccessType;

@ApplicationScoped
public class ClientAccessTypeConverter {
    public ClientAccessTypeEnt convert(ClientAccessType clientAccessType) {
        return switch (clientAccessType) {
            case ADMINISTRATORS -> ClientAccessTypeEnt.ADMINISTRATORS;
            case EMPLOYEES -> ClientAccessTypeEnt.EMPLOYEES;
            case USERS -> ClientAccessTypeEnt.USERS;
        };
    }

    public ClientAccessType convert(ClientAccessTypeEnt clientAccessTypeEnt) {
        return switch (clientAccessTypeEnt) {
            case ADMINISTRATORS -> ClientAccessType.ADMINISTRATORS;
            case EMPLOYEES -> ClientAccessType.EMPLOYEES;
            case USERS -> ClientAccessType.USERS;
        };
    }
}
