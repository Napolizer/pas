package org.pl.gateway.module.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.gateway.module.model.*;
import org.pl.gateway.module.model.exceptions.ClientRestException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ClientRestTest {

    ClientRest validClient;
    AddressRest validAddress;
    ValidatorFactory validatorFactory;
    Validator validator;

    List<ClientTypeRest> validClientTypes;

    @BeforeEach
    void setup() {
        validAddress = AddressRest.builder()
                .city("Warsaw")
                .number("123456789")
                .street("Street")
                .build();

        validClient = ClientRest.builder()
                .id(UUID.randomUUID())
                .username("TestUser")
                .password("TestPassword")
                .archive(false)
                .balance(100.0)
                .firstName("Władek")
                .lastName("Tester")
                .phoneNumber("123456789")
                .clientType(new BasicRest())
                .address(validAddress)
                .clientAccessType(ClientAccessTypeRest.ADMINISTRATORS)
                .build();

        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator= validatorFactory.getValidator();

        validClientTypes = List.of(
                new BasicRest(),
                new VipRest(),
                new PremiumRest()
        );
    }

    @Test
    void fieldUsernamePositiveTest() {
        List<String> validUsernames = List.of(
                "BestTesterInLodzCity",
                "JuniorTester",
                "TestMaster"
        );
        for (var username : validUsernames) {
            validClient.setUsername(username);
            assertEquals(username, validClient.getUsername());
            assertTrue(validator.validate(validClient).isEmpty());
        }
    }

    @Test
    void fieldUsernameNegativeTest() {
        List<String> invalidUsernames = new ArrayList<>(List.of(
                " ",
                "   ",
                "      ",
                "  ",
                ""
        ));
        invalidUsernames.add(null);
        for (var username : invalidUsernames) {
            validClient.setUsername(username);
            assertEquals(username, validClient.getUsername());
            assertFalse(validator.validate(validClient).isEmpty());
        }
    }

    @Test
    void fieldPasswordPositiveTest() {
        List<String> validPasswords = List.of(
                "BestTesterInLodzCity",
                "JuniorTester",
                "TestMaster"
        );
        for (var password : validPasswords) {
            validClient.setPassword(password);
            assertEquals(password, validClient.getPassword());
            assertTrue(validator.validate(validClient).isEmpty());
        }
    }

    @Test
    void fieldPasswordNegativeTest() {
        List<String> invalidPasswords = new ArrayList<>(List.of(
                " ",
                "   ",
                "      ",
                "  ",
                ""
        ));
        invalidPasswords.add(null);
        for (var password : invalidPasswords) {
            validClient.setPassword(password);
            assertEquals(password, validClient.getPassword());
            assertFalse(validator.validate(validClient).isEmpty());
        }
    }

    @Test
    void fieldArchivePositiveTest() {
        List<Boolean> validArchiveValues = List.of(
                true,
                false
        );
        for (var archive : validArchiveValues) {
            validClient.setArchive(archive);
            assertEquals(archive, validClient.getArchive());
            assertTrue(validator.validate(validClient).isEmpty());
        }
    }

    @Test
    void fieldBalancePositiveTest() {
        List<Double> validBalanceValues = List.of(
                100.0,
                20.5,
                0.00001,
                0.0
        );
        for (var balance : validBalanceValues) {
            validClient.setBalance(balance);
            assertEquals(balance, validClient.getBalance());
            assertTrue(validator.validate(validClient).isEmpty());
        }
    }

    @Test
    void fieldBalanceNegativeTest() {
        List<Double> invalidBalanceValues = new ArrayList<>(List.of(
                -100.0,
                -20.5,
                -0.0001
        ));
        invalidBalanceValues.add(null);
        for (var balance : invalidBalanceValues) {
            validClient.setBalance(balance);
            assertEquals(balance, validClient.getBalance());
            assertFalse(validator.validate(validClient).isEmpty());
        }
    }

    @Test
    void fieldFirstNamePositiveTest() {
        List<String> validFirstNames = List.of(
                "Adam",
                "KELVIN",
                "X Æ A-12"
        );
        for (var firstName : validFirstNames) {
            validClient.setFirstName(firstName);
            assertEquals(firstName, validClient.getFirstName());
            assertTrue(validator.validate(validClient).isEmpty());
        }
    }

    @Test
    void fieldFirstNameNegativeTest() {
        List<String> invalidFirstNames = new ArrayList<>(List.of(
                " ",
                "   ",
                "      ",
                "  ",
                ""
        ));
        invalidFirstNames.add(null);
        for (var firstName : invalidFirstNames) {
            validClient.setFirstName(firstName);
            assertEquals(firstName, validClient.getFirstName());
            assertFalse(validator.validate(validClient).isEmpty());
        }
    }

    @Test
    void fieldLastNamePositiveTest() {
        List<String> validLastNames = List.of(
                "Dudzisz",
                "Gonigroszek",
                "Musk"
        );
        for (var lastName : validLastNames) {
            validClient.setLastName(lastName);
            assertEquals(lastName, validClient.getLastName());
            assertTrue(validator.validate(validClient).isEmpty());
        }
    }

    @Test
    void fieldLastNameNegativeTest() {
        List<String> invalidLastNames = new ArrayList<>(List.of(
                " ",
                "   ",
                "      ",
                "  ",
                ""
        ));
        invalidLastNames.add(null);
        for (var lastName : invalidLastNames) {
            validClient.setLastName(lastName);
            assertEquals(lastName, validClient.getLastName());
            assertFalse(validator.validate(validClient).isEmpty());
        }
    }

    @Test
    void fieldPhoneNumberPositiveTest() {
        List<String> validPhoneNumbers = List.of(
                "1",
                "976543211",
                "1111111111111111111111111111111111111"
        );
        for (var phoneNumber : validPhoneNumbers) {
            validClient.setPhoneNumber(phoneNumber);
            assertEquals(phoneNumber, validClient.getPhoneNumber());
            assertTrue(validator.validate(validClient).isEmpty());
        }
    }

    @Test
    void fieldPhoneNumberNegativeTest() {
        List<String> invalidPhoneNumbers = new ArrayList<>(List.of(
                " ",
                "   ",
                "      ",
                "  ",
                ""
        ));
        invalidPhoneNumbers.add(null);
        for (var phoneNumber : invalidPhoneNumbers) {
            validClient.setPhoneNumber(phoneNumber);
            assertEquals(phoneNumber, validClient.getPhoneNumber());
            assertFalse(validator.validate(validClient).isEmpty());
        }
    }

    @Test
    void fieldClientTypePositiveTest() {
        for (var clientType : validClientTypes) {
            validClient.setClientType(clientType);
            assertEquals(clientType, validClient.getClientType());
            assertTrue(validator.validate(validClient).isEmpty());
        }
    }

    @Test
    void fieldClientTypeNegativeTest() {
        validClient.setClientType(null);
        assertNull(validClient.getClientType());
        assertFalse(validator.validate(validClient).isEmpty());
    }

    @Test
    void fieldAddressPositiveTest() {
        List<AddressRest> validAddresses = List.of(
                validAddress,
                AddressRest.builder()
                        .city("123")
                        .street("321")
                        .number("1")
                        .build(),
                AddressRest.builder()
                        .city("aaaaaa")
                        .street("bbbbbb")
                        .number("cccccc")
                        .build()
        );
        for (var address : validAddresses) {
            validClient.setAddress(address);
            assertEquals(address, validClient.getAddress());
            assertTrue(validator.validate(validClient).isEmpty());
        }
    }

    @Test
    void fieldAddressNegativeTest() {
        validClient.setAddress(null);
        assertNull(validClient.getAddress());
        assertFalse(validator.validate(validClient).isEmpty());
    }

    @Test
    void fieldClientAccessTypePositiveTest() {
        List<ClientAccessTypeRest> validAccessTypes = List.of(
                ClientAccessTypeRest.USERS,
                ClientAccessTypeRest.EMPLOYEES,
                ClientAccessTypeRest.ADMINISTRATORS
        );
        for (var clientAccessType : validAccessTypes) {
            validClient.setClientAccessType(clientAccessType);
            assertEquals(clientAccessType, validClient.getClientAccessType());
            assertTrue(validator.validate(validClient).isEmpty());
        }
    }

    @Test
    void fieldClientAccessTypeNegativeTest() {
        validClient.setClientAccessType(null);
        assertNull(validClient.getClientAccessType());
        assertFalse(validator.validate(validClient).isEmpty());
    }

    @Test
    void calculateDiscountPositiveTest() throws ClientRestException {
        assertDoesNotThrow(() -> {
            validClient.calculateDiscount(0);
            validClient.calculateDiscount(100);
        });

        validClient.setClientType(validClientTypes.get(0));
        assertEquals(0.0, validClient.calculateDiscount(0));
        assertEquals(0.0, validClient.calculateDiscount(100), 0.001);
        validClient.setClientType(validClientTypes.get(1));
        assertEquals(0.0, validClient.calculateDiscount(0));
        assertEquals(20.0, validClient.calculateDiscount(100), 0.001);
        validClient.setClientType(validClientTypes.get(2));
        assertEquals(0.0, validClient.calculateDiscount(0));
        assertEquals(10.0, validClient.calculateDiscount(100), 0.001);
    }

    @Test
    void calculateDiscountNegativeTest() {
        assertThrows(ClientRestException.class, () -> validClient.calculateDiscount(-100));
    }

    @Test
    void changeBalancePositiveTest() {
        assertEquals(100.0, validClient.getBalance(), 0.001);
        validClient.changeBalance(200.0);
        assertEquals(300.0, validClient.getBalance(), 0.001);
        validClient.changeBalance(-150.0);
        assertEquals(150.0, validClient.getBalance(), 0.001);
        validClient.changeBalance(0.0);
        assertEquals(150.0, validClient.getBalance(), 0.001);
    }

    @Test
    void equalsAndHashcodePositiveTest() {
        ClientRest theSameClient = ClientRest.builder()
                .id(validClient.getId())
                .username("TestUser")
                .password("TestPassword")
                .archive(false)
                .balance(100.0)
                .firstName("Władek")
                .lastName("Tester")
                .phoneNumber("123456789")
                .clientType(new BasicRest())
                .address(validAddress)
                .clientAccessType(ClientAccessTypeRest.ADMINISTRATORS)
                .build();

        assertEquals(validClient, theSameClient);
        assertEquals(validClient.hashCode(), theSameClient.hashCode());
    }

    @Test
    void equalsAndHashcodeNegativeTest() {
        ClientRest clientWithDifferentId = ClientRest.builder()
                .id(UUID.randomUUID())
                .username("TestUser")
                .password("TestPassword")
                .archive(false)
                .balance(100.0)
                .firstName("Władek")
                .lastName("Tester")
                .phoneNumber("123456789")
                .clientType(new BasicRest())
                .address(validAddress)
                .clientAccessType(ClientAccessTypeRest.ADMINISTRATORS)
                .build();

        assertNotEquals(validClient, clientWithDifferentId);
        assertNotEquals(validClient.hashCode(), clientWithDifferentId.hashCode());
        assertNotEquals(validClient, null);
    }
}
