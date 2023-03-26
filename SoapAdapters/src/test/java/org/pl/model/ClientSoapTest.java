package org.pl.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.model.exceptions.ClientException;
import org.pl.model.exceptions.ClientSoapException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ClientSoapTest {

    ClientSoap validClient;
    AddressSoap validAddress;
    ValidatorFactory validatorFactory;
    Validator validator;

    List<ClientTypeSoap> validClientTypes;

    @BeforeEach
    void setup() {
        validAddress = new AddressSoap("Warsaw","123456789", "Street");

        validClient = new ClientSoap(UUID.randomUUID(), "TestUser", "TestPassword", false, 100.0, "Władek", "Tester",
                "123456789", new BasicSoap(), validAddress, ClientAccessTypeSoap.ADMINISTRATORS);

        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator= validatorFactory.getValidator();

        validClientTypes = List.of(
                new BasicSoap(),
                new VipSoap(),
                new PremiumSoap()
        );
    }

    @Test
    void fieldUsernamePositiveTest() {
        List<String> validUsernames = new ArrayList<>(List.of(
                "BestTesterInLodzCity",
                "JuniorTester",
                "TestMaster"
        ));
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
                "TestMaster"
        ));
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
                0.0
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
                "X Æ A-12"
        ));
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
                "Musk"
        ));

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
                "1111111111111111111111111111111111111"
        ));
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
        }
    }

    @Test
    void fieldAddressPositiveTest() {
        List<AddressSoap> validAddresses = List.of(
                validAddress,
                new AddressSoap("123", "321", "1"),
                new AddressSoap("aaaaa", "bbbbb", "ccccc")
        );
        for (var address : validAddresses) {
            validClient.setAddress(address);
            assertEquals(address, validClient.getAddress());
            assertTrue(validator.validate(validClient).isEmpty());
        }
    }

    @Test
    void fieldClientAccessTypePositiveTest() {
        List<ClientAccessTypeSoap> validAccessTypes = List.of(
                ClientAccessTypeSoap.USERS,
                ClientAccessTypeSoap.EMPLOYEES,
                ClientAccessTypeSoap.ADMINISTRATORS
        );
        for (var clientAccessType : validAccessTypes) {
            validClient.setClientAccessType(clientAccessType);
            assertEquals(clientAccessType, validClient.getClientAccessType());
            assertTrue(validator.validate(validClient).isEmpty());
        }
    }

    @Test
    void calculateDiscountPositiveTest() throws ClientSoapException {
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
        assertThrows(ClientSoapException.class, () -> validClient.calculateDiscount(-100));
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
        ClientSoap theSameClient = new ClientSoap(validClient.getId(), "TestUser", "TestPassword", false, 100.0, "Władek",
                "Tester", "123456789", new BasicSoap(), validAddress, ClientAccessTypeSoap.ADMINISTRATORS);
        assertEquals(validClient, theSameClient);
        assertEquals(validClient.hashCode(), theSameClient.hashCode());
    }

    @Test
    void equalsAndHashcodeNegativeTest() {
        ClientSoap clientWithDifferentId = new ClientSoap(UUID.randomUUID(), "TestUser", "TestPassword", false, 100.0, "Władek",
                "Tester", "123456789", new BasicSoap(), validAddress, ClientAccessTypeSoap.ADMINISTRATORS);
        assertNotEquals(validClient, clientWithDifferentId);
        assertNotEquals(validClient.hashCode(), clientWithDifferentId.hashCode());
        assertNotEquals(validClient, null);
    }
}
