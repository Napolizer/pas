package org.pl.gateway.module.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.gateway.module.model.exceptions.ClientException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {

    Client validClient;
    Address validAddress;
    ValidatorFactory validatorFactory;
    Validator validator;

    List<ClientType> validClientTypes;

    @BeforeEach
    void setup() {
        validAddress = Address.builder()
                .city("Warsaw")
                .number("123456789")
                .street("Street")
                .build();

        validClient = Client.builder()
                .id(UUID.randomUUID())
                .username("TestUser")
                .password("TestPassword")
                .archive(false)
                .balance(100.0)
                .firstName("Władek")
                .lastName("Tester")
                .phoneNumber("123456789")
                .clientType(new Basic())
                .address(validAddress)
                .clientAccessType(ClientAccessType.ADMINISTRATORS)
                .build();

        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator= validatorFactory.getValidator();

        validClientTypes = List.of(
                new Basic(),
                new Vip(),
                new Premium()
        );
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
            validClient.setUsername(username);
            assertEquals(username, validClient.getUsername());
            assertTrue(validator.validate(validClient).isEmpty());
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
            validClient.setPassword(password);
            assertEquals(password, validClient.getPassword());
            assertTrue(validator.validate(validClient).isEmpty());
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
        List<Double> validBalanceValues = new ArrayList<>(List.of(
                100.0,
                20.5,
                0.00001,
                0.0,
                -100.0,
                -20.5,
                -0.0001
        ));
        for (var balance : validBalanceValues) {
            validClient.setBalance(balance);
            assertEquals(balance, validClient.getBalance());
            assertTrue(validator.validate(validClient).isEmpty());
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
            validClient.setFirstName(firstName);
            assertEquals(firstName, validClient.getFirstName());
            assertTrue(validator.validate(validClient).isEmpty());
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
            validClient.setLastName(lastName);
            assertEquals(lastName, validClient.getLastName());
            assertTrue(validator.validate(validClient).isEmpty());
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
            validClient.setPhoneNumber(phoneNumber);
            assertEquals(phoneNumber, validClient.getPhoneNumber());
            assertTrue(validator.validate(validClient).isEmpty());
        }
    }

    @Test
    void fieldClientTypePositiveTest() {
        for (var clientType : validClientTypes) {
            validClient.setClientType(clientType);
            assertEquals(clientType, validClient.getClientType());
            assertTrue(validator.validate(validClient).isEmpty());
            validClient.setClientType(null);
            assertNull(validClient.getClientType());
            assertTrue(validator.validate(validClient).isEmpty());
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
            validClient.setAddress(address);
            assertEquals(address, validClient.getAddress());
            assertTrue(validator.validate(validClient).isEmpty());
        }
        validClient.setAddress(null);
        assertNull(validClient.getAddress());
        assertTrue(validator.validate(validClient).isEmpty());
    }

    @Test
    void fieldClientAccessTypePositiveTest() {
        List<ClientAccessType> validAccessTypes = List.of(
                ClientAccessType.USERS,
                ClientAccessType.EMPLOYEES,
                ClientAccessType.ADMINISTRATORS
        );
        for (var clientAccessType : validAccessTypes) {
            validClient.setClientAccessType(clientAccessType);
            assertEquals(clientAccessType, validClient.getClientAccessType());
            assertTrue(validator.validate(validClient).isEmpty());
        }
        validClient.setClientAccessType(null);
        assertNull(validClient.getClientAccessType());
        assertTrue(validator.validate(validClient).isEmpty());
    }

    @Test
    void calculateDiscountPositiveTest() throws ClientException {
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
        assertThrows(ClientException.class, () -> validClient.calculateDiscount(-100));
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
        Client theSameClient = Client.builder()
                .id(validClient.getId())
                .username("TestUser")
                .password("TestPassword")
                .archive(false)
                .balance(100.0)
                .firstName("Władek")
                .lastName("Tester")
                .phoneNumber("123456789")
                .clientType(new Basic())
                .address(validAddress)
                .clientAccessType(ClientAccessType.ADMINISTRATORS)
                .build();

        assertEquals(validClient, theSameClient);
        assertEquals(validClient.hashCode(), theSameClient.hashCode());
    }

    @Test
    void equalsAndHashcodeNegativeTest() {
        Client clientWithDifferentId = Client.builder()
                .id(UUID.randomUUID())
                .username("TestUser")
                .password("TestPassword")
                .archive(false)
                .balance(100.0)
                .firstName("Władek")
                .lastName("Tester")
                .phoneNumber("123456789")
                .clientType(new Basic())
                .address(validAddress)
                .clientAccessType(ClientAccessType.ADMINISTRATORS)
                .build();

        assertNotEquals(validClient, clientWithDifferentId);
        assertNotEquals(validClient.hashCode(), clientWithDifferentId.hashCode());
        assertNotEquals(validClient, null);
    }
}
