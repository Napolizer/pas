package org.pl.repair.module.adapter.data;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.repair.module.adapter.data.model.AddressEnt;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AddressEntTest {
    ValidatorFactory validatorFactory;
    Validator validator;

    AddressEnt validAddress;

    @BeforeEach
    void setup() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();

        validAddress = AddressEnt.builder()
                .city("Warsaw")
                .number("123456789")
                .street("Street")
                .build();
    }

    @Test
    void fieldCityPositiveTest() {
        List<String> validTowns = new ArrayList<>(List.of(
                "Warsaw",
                "Lodz",
                "IO",
                " ",
                "   ",
                "      ",
                "  ",
                ""
        ));
        validTowns.add(null);
        for (var city : validTowns) {
            validAddress.setCity(city);
            assertEquals(city, validAddress.getCity());
            assertTrue(validator.validate(validAddress).isEmpty());
        }
    }

    @Test
    void fieldNumberPositiveTest() {
        List<String> validPhoneNumbers = new ArrayList<>(List.of(
                "123456789",
                "987654321",
                "000000000",
                "1",
                "123",
                " ",
                "   ",
                "      ",
                "  ",
                ""
        ));
        validPhoneNumbers.add(null);
        for (var phoneNumber : validPhoneNumbers) {
            validAddress.setNumber(phoneNumber);
            assertEquals(phoneNumber, validAddress.getNumber());
            assertTrue(validator.validate(validAddress).isEmpty());
        }
    }

    @Test
    void fieldStreetPositiveTest() {
        List<String> validStreets = new ArrayList<>(List.of(
                "ValidStreet",
                " --- ",
                "     I am still valid    ",
                "",
                " ",
                "        "
        ));
        validStreets.add(null);
        for (var street : validStreets) {
            validAddress.setStreet(street);
            assertEquals(street, validAddress.getStreet());
            assertTrue(validator.validate(validAddress).isEmpty());
        }
    }

    @Test
    void equalsPositiveTest() {
        AddressEnt address = AddressEnt.builder()
                .city("Warsaw")
                .number("123456789")
                .street("Street")
                .build();
        AddressEnt addressWithSameId = AddressEnt.builder()
                .city("Warsaw")
                .number("123456789")
                .street("Street")
                .build();

         assertEquals(address, addressWithSameId);
    }

    @Test
    void equalsNegativeTest() {
        AddressEnt address = AddressEnt.builder()
                .city("Warsaw")
                .number("123456789")
                .street("Street")
                .build();

        AddressEnt addressWithDifferentId = AddressEnt.builder()
                .city("Warsaw")
                .number("023456789")
                .street("Street")
                .build();

        assertNotEquals(address, addressWithDifferentId);
        assertNotEquals(address, null);
    }
}