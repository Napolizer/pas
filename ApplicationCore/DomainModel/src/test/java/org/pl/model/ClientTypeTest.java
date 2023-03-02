package org.pl.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.model.exceptions.ClientException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ClientTypeTest {
    ValidatorFactory validatorFactory;
    Validator validator;

    UUID uuid;
    ClientType validClientType;
    List<ClientType> validClientTypes;

    @BeforeEach
    void setup() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();

        uuid = UUID.fromString("5fc03087-d265-11e7-b8c6-83e29cd24f4c");
        validClientType = new Basic();

        validClientTypes = List.of(
                new Basic(),
                new Premium(),
                new Vip()
        );
    }

    @Test
    void fieldFactorPositiveTest() {
        List<Double> validFactors = List.of(
                0.0, 0.01, 0.1, 0.5, 0.9, 0.99, 1.0
        );
        for (var factor : validFactors) {
            validClientType.setFactor(factor);
            assertEquals(factor, validClientType.getFactor());
            assertTrue(validator.validate(validClientType).isEmpty());
        }
    }

    @Test
    void fieldFactorNegativeTest() {
        List<Double> invalidFactors = new ArrayList<>(List.of(
                -100.0, -10.0, -1.0, -0.1, -0.01, 1.01, 1.1, 10.0, 100.0
        ));
        invalidFactors.add(null);
        for (var factor : invalidFactors) {
            validClientType.setFactor(factor);
            assertEquals(factor, validClientType.getFactor());
            assertFalse(validator.validate(validClientType).isEmpty());
        }
    }

    @Test
    void fieldMaxRepairsPositiveTest() {
        List<Integer> validMaxRepairs = List.of(
                1, 2, 3, 4, 5, 6,
                10, 100, 1000
        );
        for (var maxRepairs : validMaxRepairs) {
            validClientType.setMaxRepairs(maxRepairs);
            assertEquals(maxRepairs, validClientType.getMaxRepairs());
            assertTrue(validator.validate(validClientType).isEmpty());
        }
    }

    @Test
    void fieldMaxRepairsNegativeTest() {
        List<Integer> invalidMaxRepairs = new ArrayList<>(List.of(
                -1000,
                -100,
                -10,
                -1,
                0
        ));
        invalidMaxRepairs.add(null);
        for (var maxRepairs : invalidMaxRepairs) {
            validClientType.setMaxRepairs(maxRepairs);
            assertEquals(maxRepairs, validClientType.getMaxRepairs());
            assertFalse(validator.validate(validClientType).isEmpty());
        }
    }

    @Test
    void fieldTypeNamePositiveTest() {
        List<String> validTypeNames = List.of(
                "some name",
                "a",
                " other name 123 "
        );
        for (var typeName : validTypeNames) {
            validClientType.setType(typeName);
            assertEquals(typeName, validClientType.getType());
            assertTrue(validator.validate(validClientType).isEmpty());
        }
    }

    @Test
    void fieldTypeNameNegativeTest() {
        List<String> invalidTypeNames = new ArrayList<>(List.of(
                "",
                " ",
                "         "
        ));
        invalidTypeNames.add(null);
        for (var typeName : invalidTypeNames) {
            validClientType.setType(typeName);
            assertEquals(typeName, validClientType.getType());
            assertFalse(validator.validate(validClientType).isEmpty());
        }
    }

    @Test
    void calculateDiscountNegativeTest() {
        assertThrows(ClientException.class, () -> {
            validClientType.calculateDiscount(-0.1);
        });
    }

    @Test
    void calculateDiscountPositiveTest() throws ClientException {
        assertDoesNotThrow(() -> {
            validClientType.calculateDiscount(0.0);
            validClientType.calculateDiscount(100.0);
        });

        Basic basic = new Basic();
        Premium premium = new Premium();
        Vip vip = new Vip();

        assertEquals(0.0, basic.calculateDiscount(0.0));
        assertEquals(0.0, premium.calculateDiscount(0.0));
        assertEquals(0.0, vip.calculateDiscount(0.0));

        assertEquals(0.0, basic.calculateDiscount(100.0), 0.001);
        assertEquals(10.0, premium.calculateDiscount(100.0), 0.001);
        assertEquals(20.0, vip.calculateDiscount(100.0), 0.001);
    }

    @Test
    void equalsPositiveTest() {
        ClientType clientType = Basic.builder()
                .id(UUID.fromString("5fc03087-d265-11e7-b8c6-83e29cd24f4c"))
                .build();
        ClientType clientTypeWithSameId = Basic.builder()
                .id(UUID.fromString("5fc03087-d265-11e7-b8c6-83e29cd24f4c"))
                .build();

        assertEquals(clientType, clientTypeWithSameId);
    }

    @Test
    void equalsNegativeTest() {
        ClientType clientType = Basic.builder()
                .id(UUID.fromString("5fc03087-d265-11e7-b8c6-83e29cd24f4c"))
                .build();
        ClientType clientTypeWithDifferentId = Basic.builder()
                .id(UUID.fromString("6fc03087-d265-11e7-b8c6-83e29cd24f4c"))
                .build();

        assertNotEquals(clientType, clientTypeWithDifferentId);
        assertNotEquals(clientType, null);
    }
}