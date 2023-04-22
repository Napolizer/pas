package org.pl.gateway.module.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.gateway.module.model.AddressRest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
class AddressRestTest {
    ValidatorFactory validatorFactory;
    Validator validator;

    AddressRest validAddress;

    @BeforeEach
    void setup() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();

        validAddress = AddressRest.builder()
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
        AddressRest address = AddressRest.builder()
                .city("Warsaw")
                .number("123456789")
                .street("Street")
                .build();
         AddressRest addressWithSameId = AddressRest.builder()
                .city("Warsaw")
                .number("123456789")
                .street("Street")
                .build();

         assertEquals(address, addressWithSameId);
    }

    @Test
    void equalsNegativeTest() {
        AddressRest address = AddressRest.builder()
                .city("Warsaw")
                .number("123456789")
                .street("Street")
                .build();

        AddressRest addressWithDifferentId = AddressRest.builder()
                .city("Warsaw")
                .number("023456789")
                .street("Street")
                .build();

        assertNotEquals(address, addressWithDifferentId);
        assertNotEquals(address, null);
    }
}