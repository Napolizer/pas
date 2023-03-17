package org.pl.adapter.data.converters;

import org.junit.jupiter.api.Test;
import org.pl.adapter.data.model.ClientAccessTypeEnt;
import org.pl.model.ClientAccessType;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientAccessTypeConverterTest {
    private final ClientAccessTypeConverter clientAccessTypeConverter = new ClientAccessTypeConverter();

    @Test
    void convertClientAccessTypeFromDomainToEntModelTest() {
        ClientAccessType clientAccessTypeAdministrator = ClientAccessType.ADMINISTRATORS;
        ClientAccessType clientAccessTypeEmployee = ClientAccessType.EMPLOYEES;
        ClientAccessType clientAccessTypeUser = ClientAccessType.USERS;
        ClientAccessTypeEnt clientAccessTypeAdministratorEnt = clientAccessTypeConverter.convert(clientAccessTypeAdministrator);
        ClientAccessTypeEnt clientAccessTypeEmployeeEnt = clientAccessTypeConverter.convert(clientAccessTypeEmployee);
        ClientAccessTypeEnt clientAccessTypeUserEnt = clientAccessTypeConverter.convert(clientAccessTypeUser);
        assertEquals(ClientAccessTypeEnt.ADMINISTRATORS, clientAccessTypeAdministratorEnt);
        assertEquals(ClientAccessTypeEnt.EMPLOYEES, clientAccessTypeEmployeeEnt);
        assertEquals(ClientAccessTypeEnt.USERS, clientAccessTypeUserEnt);
    }

    @Test
    void convertClientAccessTypeFromEntToDomainModelTest() {
        ClientAccessTypeEnt clientAccessTypeAdministratorEnt = ClientAccessTypeEnt.ADMINISTRATORS;
        ClientAccessTypeEnt clientAccessTypeEmployeeEnt = ClientAccessTypeEnt.EMPLOYEES;
        ClientAccessTypeEnt clientAccessTypeUserEnt = ClientAccessTypeEnt.USERS;
        ClientAccessType clientAccessTypeAdministrator = clientAccessTypeConverter.convert(clientAccessTypeAdministratorEnt);
        ClientAccessType clientAccessTypeEmployee = clientAccessTypeConverter.convert(clientAccessTypeEmployeeEnt);
        ClientAccessType clientAccessTypeUser = clientAccessTypeConverter.convert(clientAccessTypeUserEnt);
        assertEquals(ClientAccessType.ADMINISTRATORS, clientAccessTypeAdministrator);
        assertEquals(ClientAccessType.EMPLOYEES, clientAccessTypeEmployee);
        assertEquals(ClientAccessType.USERS, clientAccessTypeUser);
    }
}
