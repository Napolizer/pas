package org.pl.gateway.module.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.gateway.module.model.BasicRest;
import org.pl.gateway.module.model.ClientTypeRest;
import org.pl.gateway.module.model.PremiumRest;
import org.pl.gateway.module.model.VipRest;
import org.pl.gateway.module.model.exceptions.ClientRestException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ClientTypeTest {
//    ValidatorFactory validatorFactory;
//    Validator validator;
//
//    UUID uuid;
//    ClientTypeRest validClientType;
//    List<ClientTypeRest> validClientTypes;
//
//    @BeforeEach
//    void setup() {
//        validatorFactory = Validation.buildDefaultValidatorFactory();
//        validator = validatorFactory.getValidator();
//
//        uuid = UUID.fromString("5fc03087-d265-11e7-b8c6-83e29cd24f4c");
//        validClientType = new BasicRest();
//
//        validClientTypes = List.of(
//                new BasicRest(),
//                new PremiumRest(),
//                new VipRest()
//        );
//    }
//
//    @Test
//    void fieldFactorPositiveTest() {
//        List<Double> validFactors = List.of(
//                0.0, 0.01, 0.1, 0.5, 0.9, 0.99, 1.0
//        );
//        for (var factor : validFactors) {
//            validClientType.setFactor(factor);
//            assertEquals(factor, validClientType.getFactor());
//            assertTrue(validator.validate(validClientType).isEmpty());
//        }
//    }
//
//    @Test
//    void fieldFactorNegativeTest() {
//        List<Double> invalidFactors = new ArrayList<>(List.of(
//                -100.0, -10.0, -1.0, -0.1, -0.01, 1.01, 1.1, 10.0, 100.0
//        ));
//        invalidFactors.add(null);
//        for (var factor : invalidFactors) {
//            validClientType.setFactor(factor);
//            assertEquals(factor, validClientType.getFactor());
//            assertFalse(validator.validate(validClientType).isEmpty());
//        }
//    }
//
//    @Test
//    void fieldMaxRepairsPositiveTest() {
//        List<Integer> validMaxRepairs = List.of(
//                1, 2, 3, 4, 5, 6,
//                10, 100, 1000
//        );
//        for (var maxRepairs : validMaxRepairs) {
//            validClientType.setMaxRepairs(maxRepairs);
//            assertEquals(maxRepairs, validClientType.getMaxRepairs());
//            assertTrue(validator.validate(validClientType).isEmpty());
//        }
//    }
//
//    @Test
//    void fieldMaxRepairsNegativeTest() {
//        List<Integer> invalidMaxRepairs = new ArrayList<>(List.of(
//                -1000,
//                -100,
//                -10,
//                -1,
//                0
//        ));
//        invalidMaxRepairs.add(null);
//        for (var maxRepairs : invalidMaxRepairs) {
//            validClientType.setMaxRepairs(maxRepairs);
//            assertEquals(maxRepairs, validClientType.getMaxRepairs());
//            assertFalse(validator.validate(validClientType).isEmpty());
//        }
//    }
//
//    @Test
//    void fieldTypeNamePositiveTest() {
//        List<String> validTypeNames = List.of(
//                "some name",
//                "a",
//                " other name 123 "
//        );
//        for (var typeName : validTypeNames) {
//            validClientType.setType(typeName);
//            assertEquals(typeName, validClientType.getType());
//            assertTrue(validator.validate(validClientType).isEmpty());
//        }
//    }
//
//    @Test
//    void fieldTypeNameNegativeTest() {
//        List<String> invalidTypeNames = new ArrayList<>(List.of(
//                "",
//                " ",
//                "         "
//        ));
//        invalidTypeNames.add(null);
//        for (var typeName : invalidTypeNames) {
//            validClientType.setType(typeName);
//            assertEquals(typeName, validClientType.getType());
//            assertFalse(validator.validate(validClientType).isEmpty());
//        }
//    }
//
//    @Test
//    void calculateDiscountNegativeTest() {
//        assertThrows(ClientRestException.class, () -> {
//            validClientType.calculateDiscount(-0.1);
//        });
//    }
//
//    @Test
//    void calculateDiscountPositiveTest() throws ClientRestException {
//        assertDoesNotThrow(() -> {
//            validClientType.calculateDiscount(0.0);
//            validClientType.calculateDiscount(100.0);
//        });
//
//        BasicRest basic = new BasicRest();
//        PremiumRest premium = new PremiumRest();
//        VipRest vip = new VipRest();
//
//        assertEquals(0.0, basic.calculateDiscount(0.0));
//        assertEquals(0.0, premium.calculateDiscount(0.0));
//        assertEquals(0.0, vip.calculateDiscount(0.0));
//
//        assertEquals(0.0, basic.calculateDiscount(100.0), 0.001);
//        assertEquals(10.0, premium.calculateDiscount(100.0), 0.001);
//        assertEquals(20.0, vip.calculateDiscount(100.0), 0.001);
//    }
//
//    @Test
//    void equalsPositiveTest() {
//        ClientTypeRest clientType = BasicRest.builder()
//                .id(UUID.fromString("5fc03087-d265-11e7-b8c6-83e29cd24f4c"))
//                .build();
//        ClientTypeRest clientTypeWithSameId = BasicRest.builder()
//                .id(UUID.fromString("5fc03087-d265-11e7-b8c6-83e29cd24f4c"))
//                .build();
//
//        assertEquals(clientType, clientTypeWithSameId);
//    }
//
//    @Test
//    void equalsNegativeTest() {
//        ClientTypeRest clientType = BasicRest.builder()
//                .id(UUID.fromString("5fc03087-d265-11e7-b8c6-83e29cd24f4c"))
//                .build();
//        ClientTypeRest clientTypeWithDifferentId = BasicRest.builder()
//                .id(UUID.fromString("6fc03087-d265-11e7-b8c6-83e29cd24f4c"))
//                .build();
//
//        assertNotEquals(clientType, clientTypeWithDifferentId);
//        assertNotEquals(clientType, null);
//    }
}