package org.pl.converters;

import org.junit.jupiter.api.Test;
import org.pl.model.ClientAccessType;
import org.pl.model.ClientAccessTypeRest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientAccessTypeConverterTest {
    private final ClientAccessTypeConverter clientAccessTypeConverter = new ClientAccessTypeConverter();

    @Test
    void convertClientAccessTypeFromDomainToEntModelTest() {
        ClientAccessType clientAccessTypeAdministrator = ClientAccessType.ADMINISTRATORS;
        ClientAccessType clientAccessTypeEmployee = ClientAccessType.EMPLOYEES;
        ClientAccessType clientAccessTypeUser = ClientAccessType.USERS;
        ClientAccessTypeRest clientAccessTypeAdministratorEnt = clientAccessTypeConverter.convert(clientAccessTypeAdministrator);
        ClientAccessTypeRest clientAccessTypeEmployeeEnt = clientAccessTypeConverter.convert(clientAccessTypeEmployee);
        ClientAccessTypeRest clientAccessTypeUserEnt = clientAccessTypeConverter.convert(clientAccessTypeUser);
        assertEquals(ClientAccessTypeRest.ADMINISTRATORS, clientAccessTypeAdministratorEnt);
        assertEquals(ClientAccessTypeRest.EMPLOYEES, clientAccessTypeEmployeeEnt);
        assertEquals(ClientAccessTypeRest.USERS, clientAccessTypeUserEnt);
    }

    @Test
    void convertClientAccessTypeFromEntToDomainModelTest() {
        ClientAccessTypeRest clientAccessTypeAdministratorEnt = ClientAccessTypeRest.ADMINISTRATORS;
        ClientAccessTypeRest clientAccessTypeEmployeeEnt = ClientAccessTypeRest.EMPLOYEES;
        ClientAccessTypeRest clientAccessTypeUserEnt = ClientAccessTypeRest.USERS;
        ClientAccessType clientAccessTypeAdministrator = clientAccessTypeConverter.convert(clientAccessTypeAdministratorEnt);
        ClientAccessType clientAccessTypeEmployee = clientAccessTypeConverter.convert(clientAccessTypeEmployeeEnt);
        ClientAccessType clientAccessTypeUser = clientAccessTypeConverter.convert(clientAccessTypeUserEnt);
        assertEquals(ClientAccessType.ADMINISTRATORS, clientAccessTypeAdministrator);
        assertEquals(ClientAccessType.EMPLOYEES, clientAccessTypeEmployee);
        assertEquals(ClientAccessType.USERS, clientAccessTypeUser);
    }
}
