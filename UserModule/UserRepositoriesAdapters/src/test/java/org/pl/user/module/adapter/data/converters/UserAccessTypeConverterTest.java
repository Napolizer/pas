package org.pl.user.module.adapter.data.converters;

import org.junit.jupiter.api.Test;
import org.pl.user.module.adapter.data.model.UserAccessTypeEnt;
import org.pl.user.module.model.UserAccessType;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserAccessTypeConverterTest {
    private final UserAccessTypeConverter clientAccessTypeConverter = new UserAccessTypeConverter();

    @Test
    void convertClientAccessTypeFromDomainToEntModelTest() {
        UserAccessType clientAccessTypeAdministrator = UserAccessType.ADMINISTRATORS;
        UserAccessType clientAccessTypeEmployee = UserAccessType.EMPLOYEES;
        UserAccessType clientAccessTypeUser = UserAccessType.USERS;
        UserAccessTypeEnt clientAccessTypeAdministratorEnt = clientAccessTypeConverter.convert(clientAccessTypeAdministrator);
        UserAccessTypeEnt clientAccessTypeEmployeeEnt = clientAccessTypeConverter.convert(clientAccessTypeEmployee);
        UserAccessTypeEnt clientAccessTypeUserEnt = clientAccessTypeConverter.convert(clientAccessTypeUser);
        assertEquals(UserAccessTypeEnt.ADMINISTRATORS, clientAccessTypeAdministratorEnt);
        assertEquals(UserAccessTypeEnt.EMPLOYEES, clientAccessTypeEmployeeEnt);
        assertEquals(UserAccessTypeEnt.USERS, clientAccessTypeUserEnt);
    }

    @Test
    void convertClientAccessTypeFromEntToDomainModelTest() {
        UserAccessTypeEnt clientAccessTypeAdministratorEnt = UserAccessTypeEnt.ADMINISTRATORS;
        UserAccessTypeEnt clientAccessTypeEmployeeEnt = UserAccessTypeEnt.EMPLOYEES;
        UserAccessTypeEnt clientAccessTypeUserEnt = UserAccessTypeEnt.USERS;
        UserAccessType clientAccessTypeAdministrator = clientAccessTypeConverter.convert(clientAccessTypeAdministratorEnt);
        UserAccessType clientAccessTypeEmployee = clientAccessTypeConverter.convert(clientAccessTypeEmployeeEnt);
        UserAccessType clientAccessTypeUser = clientAccessTypeConverter.convert(clientAccessTypeUserEnt);
        assertEquals(UserAccessType.ADMINISTRATORS, clientAccessTypeAdministrator);
        assertEquals(UserAccessType.EMPLOYEES, clientAccessTypeEmployee);
        assertEquals(UserAccessType.USERS, clientAccessTypeUser);
    }
}
