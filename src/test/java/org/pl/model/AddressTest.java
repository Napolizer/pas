package org.pl.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {
    ValidatorFactory validatorFactory;
    Validator validator;

    Address validAddress;

    @BeforeEach
    void setup() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();

        validAddress = Address.builder()
                .city("Warsaw")
                .number("123456789")
                .street("Street")
                .build();
    }

    @Test
    void fieldCityPositiveTest() {
        List<String> validTowns = List.of(
                "Warsaw",
                "Lodz",
                "IO"
        );
        for (var city : validTowns) {
            validAddress.setCity(city);
            assertEquals(city, validAddress.getCity());
            assertTrue(validator.validate(validAddress).isEmpty());
        }
    }

    @Test
    void fieldCityNegativeTest() {
        List<String> invalidTowns = new ArrayList<>(List.of(
                " ",
                "   ",
                "      ",
                "  ",
                ""
        ));
        invalidTowns.add(null);
        for (var city : invalidTowns) {
            validAddress.setCity(city);
            assertEquals(city, validAddress.getCity());
            assertFalse(validator.validate(validAddress).isEmpty());
        }
    }

    @Test
    void fieldNumberPositiveTest() {
        List<String> validPhoneNumbers = List.of(
                "123456789",
                "987654321",
                "000000000",
                "1",
                "123"
        );
        for (var phoneNumber : validPhoneNumbers) {
            validAddress.setNumber(phoneNumber);
            assertEquals(phoneNumber, validAddress.getNumber());
            assertTrue(validator.validate(validAddress).isEmpty());
        }
    }

    @Test
    void fieldNumberNegativeTest() {
        List<String> invalidStreetNumbers = new ArrayList<>(List.of(
                " ",
                "   ",
                "      ",
                "  ",
                ""
        ));
        invalidStreetNumbers.add(null);
        for (var phoneNumber : invalidStreetNumbers) {
            validAddress.setNumber(phoneNumber);
            assertEquals(phoneNumber, validAddress.getNumber());
            assertFalse(validator.validate(validAddress).isEmpty());
        }
    }

    @Test
    void fieldStreetPositiveTest() {
        List<String> validStreets = List.of(
                "ValidStreet",
                " --- ",
                "     I am still valid    "
        );
        for (var street : validStreets) {
            validAddress.setStreet(street);
            assertEquals(street, validAddress.getStreet());
            assertTrue(validator.validate(validAddress).isEmpty());
        }
    }

    @Test
    void fieldStreetNegativeTest() {
        List<String> invalidStreets = new ArrayList<>(List.of(
                "",
                " ",
                "         "
        ));
        invalidStreets.add(null);
        for (var street : invalidStreets) {
            validAddress.setStreet(street);
            assertEquals(street, validAddress.getStreet());
            assertFalse(validator.validate(validAddress).isEmpty());
        }
    }

    @Test
    void equalsPositiveTest() {
        Address address = Address.builder()
                .city("Warsaw")
                .number("123456789")
                .street("Street")
                .build();
         Address addressWithSameId = Address.builder()
                .city("Warsaw")
                .number("123456789")
                .street("Street")
                .build();

         assertEquals(address, addressWithSameId);
    }

    @Test
    void equalsNegativeTest() {
        Address address = Address.builder()
                .city("Warsaw")
                .number("123456789")
                .street("Street")
                .build();

        Address addressWithDifferentId = Address.builder()
                .city("Warsaw")
                .number("023456789")
                .street("Street")
                .build();

        assertNotEquals(address, addressWithDifferentId);
        assertNotEquals(address, null);
    }
}