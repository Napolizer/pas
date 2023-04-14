package org.pl.converters;

import jakarta.enterprise.context.ApplicationScoped;
import org.pl.model.ClientAccessType;
import org.pl.model.ClientAccessTypeSoap;

@ApplicationScoped
public class ClientAccessTypeConverter {
    public ClientAccessTypeSoap convert(ClientAccessType clientAccessType) {
        return switch (clientAccessType) {
            case ADMINISTRATORS -> ClientAccessTypeSoap.ADMINISTRATORS;
            case EMPLOYEES -> ClientAccessTypeSoap.EMPLOYEES;
            case USERS -> ClientAccessTypeSoap.USERS;
        };
    }

    public ClientAccessType convert(ClientAccessTypeSoap clientAccessTypeSoap) {
        return switch (clientAccessTypeSoap) {
            case ADMINISTRATORS -> ClientAccessType.ADMINISTRATORS;
            case EMPLOYEES -> ClientAccessType.EMPLOYEES;
            case USERS -> ClientAccessType.USERS;
        };
    }
}
