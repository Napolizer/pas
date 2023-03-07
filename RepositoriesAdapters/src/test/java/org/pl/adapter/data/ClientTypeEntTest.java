package org.pl.adapter.data;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.adapter.data.model.BasicEnt;
import org.pl.adapter.data.model.ClientTypeEnt;
import org.pl.adapter.data.model.PremiumEnt;
import org.pl.adapter.data.model.VipEnt;
import org.pl.adapter.data.model.exceptions.ClientEntException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ClientTypeEntTest {
    ValidatorFactory validatorFactory;
    Validator validator;

    UUID uuid;
    ClientTypeEnt validClientType;
    List<ClientTypeEnt> validClientTypes;

    @BeforeEach
    void setup() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();

        uuid = UUID.fromString("5fc03087-d265-11e7-b8c6-83e29cd24f4c");
        validClientType = new BasicEnt();

        validClientTypes = List.of(
                new BasicEnt(),
                new PremiumEnt(),
                new VipEnt()
        );
    }

    @Test
    void fieldFactorPositiveTest() {
        List<Double> validFactors = new ArrayList<>(List.of(
                0.0, 0.01, 0.1, 0.5, 0.9, 0.99, 1.0,
                -100.0, -10.0, -1.0, -0.1, -0.01, 1.01, 1.1, 10.0, 100.0
        ));
        validFactors.add(null);
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
                10, 100, 1000,
                -1000, -100, -10, -1, 0
        ));
        validMaxRepairs.add(null);
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
                " other name 123 ",
                "",
                " ",
                "         "
        ));
        validTypeNames.add(null);
        for (var typeName : validTypeNames) {
            validClientType.setType(typeName);
            assertEquals(typeName, validClientType.getType());
            assertTrue(validator.validate(validClientType).isEmpty());
        }
    }

    @Test
    void calculateDiscountNegativeTest() {
        assertThrows(ClientEntException.class, () -> validClientType.calculateDiscount(-0.1));
    }

    @Test
    void calculateDiscountPositiveTest() throws ClientEntException {
        assertDoesNotThrow(() -> {
            validClientType.calculateDiscount(0.0);
            validClientType.calculateDiscount(100.0);
        });

        BasicEnt basic = new BasicEnt();
        PremiumEnt premium = new PremiumEnt();
        VipEnt vip = new VipEnt();

        assertEquals(0.0, basic.calculateDiscount(0.0));
        assertEquals(0.0, premium.calculateDiscount(0.0));
        assertEquals(0.0, vip.calculateDiscount(0.0));

        assertEquals(0.0, basic.calculateDiscount(100.0), 0.001);
        assertEquals(10.0, premium.calculateDiscount(100.0), 0.001);
        assertEquals(20.0, vip.calculateDiscount(100.0), 0.001);
    }

    @Test
    void equalsPositiveTest() {
        ClientTypeEnt clientTypeEnt = BasicEnt.builder()
                .id(UUID.fromString("5fc03087-d265-11e7-b8c6-83e29cd24f4c"))
                .build();
        ClientTypeEnt clientTypeWithSameId = BasicEnt.builder()
                .id(UUID.fromString("5fc03087-d265-11e7-b8c6-83e29cd24f4c"))
                .build();

        assertEquals(clientTypeEnt, clientTypeWithSameId);
    }

    @Test
    void equalsNegativeTest() {
        ClientTypeEnt clientType = BasicEnt.builder()
                .id(UUID.fromString("5fc03087-d265-11e7-b8c6-83e29cd24f4c"))
                .build();
        ClientTypeEnt clientTypeWithDifferentId = BasicEnt.builder()
                .id(UUID.fromString("6fc03087-d265-11e7-b8c6-83e29cd24f4c"))
                .build();

        assertNotEquals(clientType, clientTypeWithDifferentId);
        assertNotEquals(clientType, null);
    }
}