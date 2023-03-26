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

class ClientTypeSoapTest {
    ValidatorFactory validatorFactory;
    Validator validator;

    UUID uuid;
    ClientTypeSoap validClientType;
    List<ClientTypeSoap> validClientTypes;

    @BeforeEach
    void setup() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();

        uuid = UUID.fromString("5fc03087-d265-11e7-b8c6-83e29cd24f4c");
        validClientType = new BasicSoap();

        validClientTypes = List.of(
                new BasicSoap(),
                new PremiumSoap(),
                new VipSoap()
        );
    }

    @Test
    void fieldFactorPositiveTest() {
        List<Double> validFactors = new ArrayList<>(List.of(
                0.0, 0.01, 0.1, 0.5, 0.9, 0.99, 1.0
        ));
        for (var factor : validFactors) {
            validClientType.setFactor(factor);
            assertEquals(factor, validClientType.getFactor());
            assertTrue(validator.validate(validClientType).isEmpty());
        }
    }

    @Test
    void fieldMaxRepairsPositiveTest() {
        List<Integer> validMaxRepairs = new ArrayList<>(List.of(
                1, 2, 3, 4, 5, 6,
                10, 100, 1000
        ));
        for (var maxRepairs : validMaxRepairs) {
            validClientType.setMaxRepairs(maxRepairs);
            assertEquals(maxRepairs, validClientType.getMaxRepairs());
            assertTrue(validator.validate(validClientType).isEmpty());
        }
    }

    @Test
    void fieldTypeNamePositiveTest() {
        List<String> validTypeNames = new ArrayList<>(List.of(
                "some name",
                "a",
                " other name 123 "
        ));
        for (var typeName : validTypeNames) {
            validClientType.setType(typeName);
            assertEquals(typeName, validClientType.getType());
            assertTrue(validator.validate(validClientType).isEmpty());
        }
    }

    @Test
    void calculateDiscountNegativeTest() {
        assertThrows(ClientSoapException.class, () -> {
            validClientType.calculateDiscount(-0.1);
        });
    }

    @Test
    void calculateDiscountPositiveTest() throws ClientSoapException {
        assertDoesNotThrow(() -> {
            validClientType.calculateDiscount(0.0);
            validClientType.calculateDiscount(100.0);
        });

        BasicSoap basic = new BasicSoap();
        PremiumSoap premium = new PremiumSoap();
        VipSoap vip = new VipSoap();

        assertEquals(0.0, basic.calculateDiscount(0.0));
        assertEquals(0.0, premium.calculateDiscount(0.0));
        assertEquals(0.0, vip.calculateDiscount(0.0));

        assertEquals(0.0, basic.calculateDiscount(100.0), 0.001);
        assertEquals(10.0, premium.calculateDiscount(100.0), 0.001);
        assertEquals(20.0, vip.calculateDiscount(100.0), 0.001);
    }
}