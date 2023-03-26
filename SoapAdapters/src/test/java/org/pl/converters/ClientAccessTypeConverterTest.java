package org.pl.converters;

import org.junit.jupiter.api.Test;
import org.pl.model.ClientAccessType;
import org.pl.model.ClientAccessTypeSoap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientAccessTypeConverterTest {
    private final ClientAccessTypeConverter clientAccessTypeConverter = new ClientAccessTypeConverter();

    @Test
    void convertClientAccessTypeFromDomainToSoapModelTest() {
        ClientAccessType clientAccessTypeAdministrator = ClientAccessType.ADMINISTRATORS;
        ClientAccessType clientAccessTypeEmployee = ClientAccessType.EMPLOYEES;
        ClientAccessType clientAccessTypeUser = ClientAccessType.USERS;
        ClientAccessTypeSoap clientAccessTypeAdministratorSoap = clientAccessTypeConverter.convert(clientAccessTypeAdministrator);
        ClientAccessTypeSoap clientAccessTypeEmployeeSoap = clientAccessTypeConverter.convert(clientAccessTypeEmployee);
        ClientAccessTypeSoap clientAccessTypeUserSoap = clientAccessTypeConverter.convert(clientAccessTypeUser);
        assertEquals(ClientAccessTypeSoap.ADMINISTRATORS, clientAccessTypeAdministratorSoap);
        assertEquals(ClientAccessTypeSoap.EMPLOYEES, clientAccessTypeEmployeeSoap);
        assertEquals(ClientAccessTypeSoap.USERS, clientAccessTypeUserSoap);
    }

    @Test
    void convertClientAccessTypeFromSoapToDomainModelTest() {
        ClientAccessTypeSoap clientAccessTypeAdministratorSoap = ClientAccessTypeSoap.ADMINISTRATORS;
        ClientAccessTypeSoap clientAccessTypeEmployeeSoap = ClientAccessTypeSoap.EMPLOYEES;
        ClientAccessTypeSoap clientAccessTypeUserSoap = ClientAccessTypeSoap.USERS;
        ClientAccessType clientAccessTypeAdministrator = clientAccessTypeConverter.convert(clientAccessTypeAdministratorSoap);
        ClientAccessType clientAccessTypeEmployee = clientAccessTypeConverter.convert(clientAccessTypeEmployeeSoap);
        ClientAccessType clientAccessTypeUser = clientAccessTypeConverter.convert(clientAccessTypeUserSoap);
        assertEquals(ClientAccessType.ADMINISTRATORS, clientAccessTypeAdministrator);
        assertEquals(ClientAccessType.EMPLOYEES, clientAccessTypeEmployee);
        assertEquals(ClientAccessType.USERS, clientAccessTypeUser);
    }
}
