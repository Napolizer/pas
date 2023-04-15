package org.pl.adapter.data;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.adapter.data.model.*;
import org.pl.adapter.data.model.exceptions.ClientEntException;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ClientEntTest {

    ClientEnt validClient;
    AddressEnt validAddress;
    ValidatorFactory validatorFactory;
    Validator validator;

    List<ClientTypeEnt> validClientTypes;

    @BeforeEach
    void setup() {
        validAddress = AddressEnt.builder()
                .city("Warsaw")
                .number("123456789")
                .street("Street")
                .build();

        validClient = ClientEnt.builder()
                .id(UUID.randomUUID())
                .username("TestUser")
                .archive(false)
                .balance(100.0)
                .firstName("Władek")
                .lastName("Tester")
                .phoneNumber("123456789")
                .clientTypeEnt(new BasicEnt())
                .addressEnt(validAddress)
                .clientAccessTypeEnt(ClientAccessTypeEnt.ADMINISTRATORS)
                .build();

        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator= validatorFactory.getValidator();

        validClientTypes = List.of(
                new BasicEnt(),
                new VipEnt(),
                new PremiumEnt()
        );
    }

    @Test
    void fieldUsernamePositiveTest() {
        List<String> validUsernames = new ArrayList<>(List.of(
                "BestTesterInLodzCity",
                "JuniorTester",
                "TestMaster",
                " ",
                "   ",
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
        validBalanceValues.add(null);
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
                "      ",
                "  ",
                ""
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
                "      ",
                "  ",
                ""
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
                "      ",
                "  ",
                ""
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
            validClient.setClientTypeEnt(clientType);
            assertEquals(clientType, validClient.getClientTypeEnt());
            assertTrue(validator.validate(validClient).isEmpty());
        }
        validClient.setClientTypeEnt(null);
        assertNull(validClient.getClientTypeEnt());
        assertTrue(validator.validate(validClient).isEmpty());
    }

    @Test
    void fieldAddressPositiveTest() {
        List<AddressEnt> validAddresses = List.of(
                validAddress,
                AddressEnt.builder()
                        .city("123")
                        .street("321")
                        .number("1")
                        .build(),
                AddressEnt.builder()
                        .city("aaaaaa")
                        .street("bbbbbb")
                        .number("cccccc")
                        .build()
        );
        for (var address : validAddresses) {
            validClient.setAddressEnt(address);
            assertEquals(address, validClient.getAddressEnt());
            assertTrue(validator.validate(validClient).isEmpty());
        }
        validClient.setAddressEnt(null);
        assertNull(validClient.getAddressEnt());
        assertTrue(validator.validate(validClient).isEmpty());
    }

    @Test
    void fieldClientAccessTypePositiveTest() {
        List<ClientAccessTypeEnt> validAccessTypes = List.of(
                ClientAccessTypeEnt.USERS,
                ClientAccessTypeEnt.EMPLOYEES,
                ClientAccessTypeEnt.ADMINISTRATORS
        );
        for (var clientAccessType : validAccessTypes) {
            validClient.setClientAccessTypeEnt(clientAccessType);
            assertEquals(clientAccessType, validClient.getClientAccessTypeEnt());
            assertTrue(validator.validate(validClient).isEmpty());
        }
        validClient.setClientAccessTypeEnt(null);
        assertNull(validClient.getClientAccessTypeEnt());
        assertTrue(validator.validate(validClient).isEmpty());
    }

    @Test
    void calculateDiscountPositiveTest() throws ClientEntException {
        assertDoesNotThrow(() -> {
            validClient.calculateDiscount(0);
            validClient.calculateDiscount(100);
        });

        validClient.setClientTypeEnt(validClientTypes.get(0));
        assertEquals(0.0, validClient.calculateDiscount(0));
        assertEquals(0.0, validClient.calculateDiscount(100), 0.001);
        validClient.setClientTypeEnt(validClientTypes.get(1));
        assertEquals(0.0, validClient.calculateDiscount(0));
        assertEquals(20.0, validClient.calculateDiscount(100), 0.001);
        validClient.setClientTypeEnt(validClientTypes.get(2));
        assertEquals(0.0, validClient.calculateDiscount(0));
        assertEquals(10.0, validClient.calculateDiscount(100), 0.001);
    }

    @Test
    void calculateDiscountNegativeTest() {
        assertThrows(ClientEntException.class, () -> validClient.calculateDiscount(-100));
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
        ClientEnt theSameClient = ClientEnt.builder()
                .id(validClient.getId())
                .username("TestUser")
                .archive(false)
                .balance(100.0)
                .firstName("Władek")
                .lastName("Tester")
                .phoneNumber("123456789")
                .clientTypeEnt(new BasicEnt())
                .addressEnt(validAddress)
                .clientAccessTypeEnt(ClientAccessTypeEnt.ADMINISTRATORS)
                .build();

        assertEquals(validClient, theSameClient);
        assertEquals(validClient.hashCode(), theSameClient.hashCode());
    }

    @Test
    void equalsAndHashcodeNegativeTest() {
        ClientEnt clientWithDifferentId = ClientEnt.builder()
                .id(UUID.randomUUID())
                .username("TestUser")
                .archive(false)
                .balance(100.0)
                .firstName("Władek")
                .lastName("Tester")
                .phoneNumber("123456789")
                .clientTypeEnt(new BasicEnt())
                .addressEnt(validAddress)
                .clientAccessTypeEnt(ClientAccessTypeEnt.ADMINISTRATORS)
                .build();

        assertNotEquals(validClient, clientWithDifferentId);
        assertNotEquals(validClient.hashCode(), clientWithDifferentId.hashCode());
        assertNotEquals(validClient, null);
    }
}
