package org.pl.user.module.converters;

import org.junit.jupiter.api.Test;
import org.pl.user.module.model.UserAccessType;
import org.pl.user.module.model.UserAccessTypeRest;

import static org.junit.jupiter.api.Assertions.*;

class UserAccessTypeConverterTest {
    private final UserAccessTypeConverter userAccessTypeConverter = new UserAccessTypeConverter();
    @Test
    void convertClientAccessTypeFromDomainToRestModelTest() {
        UserAccessType clientAccessTypeAdministrator = UserAccessType.ADMINISTRATORS;
        UserAccessType clientAccessTypeEmployee = UserAccessType.EMPLOYEES;
        UserAccessType clientAccessTypeUser = UserAccessType.USERS;
        UserAccessTypeRest clientAccessTypeAdministratorRest = userAccessTypeConverter.convert(clientAccessTypeAdministrator);
        UserAccessTypeRest clientAccessTypeEmployeeRest = userAccessTypeConverter.convert(clientAccessTypeEmployee);
        UserAccessTypeRest clientAccessTypeUserRest = userAccessTypeConverter.convert(clientAccessTypeUser);
        assertEquals(UserAccessTypeRest.ADMINISTRATORS, clientAccessTypeAdministratorRest);
        assertEquals(UserAccessTypeRest.EMPLOYEES, clientAccessTypeEmployeeRest);
        assertEquals(UserAccessTypeRest.USERS, clientAccessTypeUserRest);
    }

    @Test
    void convertClientAccessTypeFromRestToDomainModelTest() {
        UserAccessTypeRest clientAccessTypeAdministratorRest = UserAccessTypeRest.ADMINISTRATORS;
        UserAccessTypeRest clientAccessTypeEmployeeRest = UserAccessTypeRest.EMPLOYEES;
        UserAccessTypeRest clientAccessTypeUserRest = UserAccessTypeRest.USERS;
        UserAccessType clientAccessTypeAdministrator = userAccessTypeConverter.convert(clientAccessTypeAdministratorRest);
        UserAccessType clientAccessTypeEmployee = userAccessTypeConverter.convert(clientAccessTypeEmployeeRest);
        UserAccessType clientAccessTypeUser = userAccessTypeConverter.convert(clientAccessTypeUserRest);
        assertEquals(UserAccessType.ADMINISTRATORS, clientAccessTypeAdministrator);
        assertEquals(UserAccessType.EMPLOYEES, clientAccessTypeEmployee);
        assertEquals(UserAccessType.USERS, clientAccessTypeUser);
    }
}