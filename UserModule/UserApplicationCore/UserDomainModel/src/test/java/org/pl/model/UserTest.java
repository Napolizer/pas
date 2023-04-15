package org.pl.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    User validUser;
    Address validAddress;
    ValidatorFactory validatorFactory;
    Validator validator;

    @BeforeEach
    void setup() {
        validAddress = Address.builder()
                .city("Warsaw")
                .number("123456789")
                .street("Street")
                .build();

        validUser = User.builder()
                .id(UUID.randomUUID())
                .username("TestUser")
                .password("TestPassword")
                .archive(false)
                .firstName("Władek")
                .lastName("Tester")
                .phoneNumber("123456789")
                .address(validAddress)
                .userAccessType(UserAccessType.ADMINISTRATORS)
                .build();

        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator= validatorFactory.getValidator();
    }

    @Test
    void fieldUsernamePositiveTest() {
        List<String> validUsernames = new ArrayList<>(List.of(
                "BestTesterInLodzCity",
                "JuniorTester",
                "TestMaster",
                "      ",
                "  ",
                ""
        ));
        validUsernames.add(null);
        for (var username : validUsernames) {
            validUser.setUsername(username);
            assertEquals(username, validUser.getUsername());
            assertTrue(validator.validate(validUser).isEmpty());
        }
    }

    @Test
    void fieldPasswordPositiveTest() {
        List<String> validPasswords = new ArrayList<>(List.of(
                "BestTesterInLodzCity",
                "JuniorTester",
                "TestMaster",
                " ",
                "   ",
                "      "
        ));
        validPasswords.add(null);
        for (var password : validPasswords) {
            validUser.setPassword(password);
            assertEquals(password, validUser.getPassword());
            assertTrue(validator.validate(validUser).isEmpty());
        }
    }

    @Test
    void fieldArchivePositiveTest() {
        List<Boolean> validArchiveValues = List.of(
                true,
                false
        );
        for (var archive : validArchiveValues) {
            validUser.setArchive(archive);
            assertEquals(archive, validUser.getArchive());
            assertTrue(validator.validate(validUser).isEmpty());
        }
    }

    @Test
    void fieldFirstNamePositiveTest() {
        List<String> validFirstNames = new ArrayList<>(List.of(
                "Adam",
                "KELVIN",
                "X Æ A-12",
                " ",
                "   ",
                "      "
        ));
        validFirstNames.add(null);
        for (var firstName : validFirstNames) {
            validUser.setFirstName(firstName);
            assertEquals(firstName, validUser.getFirstName());
            assertTrue(validator.validate(validUser).isEmpty());
        }
    }

    @Test
    void fieldLastNamePositiveTest() {
        List<String> validLastNames = new ArrayList<>(List.of(
                "Dudzisz",
                "Gonigroszek",
                "Musk",
                " ",
                "   ",
                "      "
        ));
        validLastNames.add(null);
        for (var lastName : validLastNames) {
            validUser.setLastName(lastName);
            assertEquals(lastName, validUser.getLastName());
            assertTrue(validator.validate(validUser).isEmpty());
        }
    }

    @Test
    void fieldPhoneNumberPositiveTest() {
        List<String> validPhoneNumbers = new ArrayList<>(List.of(
                "1",
                "976543211",
                "1111111111111111111111111111111111111",
                " ",
                "   ",
                "      "
        ));
        validPhoneNumbers.add(null);
        for (var phoneNumber : validPhoneNumbers) {
            validUser.setPhoneNumber(phoneNumber);
            assertEquals(phoneNumber, validUser.getPhoneNumber());
            assertTrue(validator.validate(validUser).isEmpty());
        }
    }

    @Test
    void fieldAddressPositiveTest() {
        List<Address> validAddresses = List.of(
                validAddress,
                Address.builder()
                        .city("123")
                        .street("321")
                        .number("1")
                        .build(),
                Address.builder()
                        .city("aaaaaa")
                        .street("bbbbbb")
                        .number("cccccc")
                        .build()
        );
        for (var address : validAddresses) {
            validUser.setAddress(address);
            assertEquals(address, validUser.getAddress());
            assertTrue(validator.validate(validUser).isEmpty());
        }
        validUser.setAddress(null);
        assertNull(validUser.getAddress());
        assertTrue(validator.validate(validUser).isEmpty());
    }

    @Test
    void fieldUserAccessTypePositiveTest() {
        List<UserAccessType> validAccessTypes = List.of(
                UserAccessType.USERS,
                UserAccessType.EMPLOYEES,
                UserAccessType.ADMINISTRATORS
        );
        for (var userAccessType : validAccessTypes) {
            validUser.setUserAccessType(userAccessType);
            assertEquals(userAccessType, validUser.getUserAccessType());
            assertTrue(validator.validate(validUser).isEmpty());
        }
        validUser.setUserAccessType(null);
        assertNull(validUser.getUserAccessType());
        assertTrue(validator.validate(validAccessTypes).isEmpty());
    }

    @Test
    void equalsAndHashcodePositiveTest() {
        User theSameUser = User.builder()
                .id(validUser.getId())
                .username("TestUser")
                .password("TestPassword")
                .archive(false)
                .firstName("Władek")
                .lastName("Tester")
                .phoneNumber("123456789")
                .address(validAddress)
                .userAccessType(UserAccessType.ADMINISTRATORS)
                .build();

        assertEquals(validUser, theSameUser);
        assertEquals(validUser.hashCode(), theSameUser.hashCode());
    }

    @Test
    void equalsAndHashcodeNegativeTest() {
        User userWithDifferentId = User.builder()
                .id(UUID.randomUUID())
                .username("TestUser")
                .password("TestPassword")
                .archive(false)
                .firstName("Władek")
                .lastName("Tester")
                .phoneNumber("123456789")
                .address(validAddress)
                .userAccessType(UserAccessType.ADMINISTRATORS)
                .build();

        assertNotEquals(validUser, userWithDifferentId);
        assertNotEquals(validUser.hashCode(), userWithDifferentId.hashCode());
        assertNotEquals(validUser, null);
    }
}
