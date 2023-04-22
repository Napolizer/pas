package org.pl.gateway.module.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.gateway.module.model.*;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class HardwareTest {
//    ValidatorFactory validatorFactory;
//    Validator validator;
//    HardwareRest validHardware;
//    List<HardwareTypeRest> validHardwareTypes;
//
//    @BeforeEach
//    void setup() {
//        validatorFactory = Validation.buildDefaultValidatorFactory();
//        validator = validatorFactory.getValidator();
//
//        validHardware = HardwareRest.builder()
//                .id(UUID.randomUUID())
//                .archive(false)
//                .price(100)
//                .hardwareType(new ConsoleRest(ConditionRest.VERY_BAD))
//                .build();
//
//        validHardwareTypes = List.of(
//                new ConsoleRest(),
//                new ComputerRest(),
//                new PhoneRest(),
//                new MonitorRest()
//        );
//    }
//
//    @Test
//    void fieldArchivePositiveTest() {
//        List<Boolean> validArchiveValues = List.of(
//                true,
//                false
//        );
//        for (var archive : validArchiveValues) {
//            validHardware.setArchive(archive);
//            assertEquals(archive, validHardware.getArchive());
//            assertTrue(validator.validate(validHardware).isEmpty());
//        }
//    }
//
//    @Test
//    void fieldPricePositiveTest() {
//        List<Integer> validPriceValues = List.of(
//                100,
//                20,
//                1
//        );
//        for (var price : validPriceValues) {
//            validHardware.setPrice(price);
//            assertEquals(price, validHardware.getPrice());
//            assertTrue(validator.validate(validHardware).isEmpty());
//        }
//    }
//
//    @Test
//    void fieldPriceNegativeTest() {
//        validHardware.setPrice(null);
//        assertNull(validHardware.getPrice());
//        assertFalse(validator.validate(validHardware).isEmpty());
//    }
//
//    @Test
//    void fieldHardwareTypePositiveTest() {
//        for (var hardwareType : validHardwareTypes) {
//            validHardware.setHardwareType(hardwareType);
//            assertEquals(hardwareType, validHardware.getHardwareType());
//            assertTrue(validator.validate(validHardware).isEmpty());
//        }
//    }
//
//    @Test
//    void fieldClientTypeNegativeTest() {
//        validHardware.setHardwareType(null);
//        assertNull(validHardware.getHardwareType());
//        assertFalse(validator.validate(validHardware).isEmpty());
//    }
//
//    @Test
//    void repairPositiveTest() {
//        assertEquals(ConditionRest.VERY_BAD, validHardware.getHardwareType().getCondition());
//        validHardware.repair();
//        assertEquals(ConditionRest.FINE, validHardware.getHardwareType().getCondition());
//    }
//
//    @Test
//    void equalsAndHashcodePositiveTest() {
//        HardwareRest theSameHardware = HardwareRest.builder()
//                .id(validHardware.getId())
//                .archive(false)
//                .price(100)
//                .hardwareType(new ConsoleRest(ConditionRest.VERY_BAD))
//                .build();
//
//        assertEquals(validHardware, theSameHardware);
//        assertEquals(validHardware.hashCode(), theSameHardware.hashCode());
//    }
//
//    @Test
//    void equalsAndHashcodeNegativeTest() {
//        HardwareRest hardwareWithDifferentId = HardwareRest.builder()
//                .id(UUID.randomUUID())
//                .archive(false)
//                .price(100)
//                .hardwareType(new ConsoleRest(ConditionRest.VERY_BAD))
//                .build();
//
//        assertNotEquals(validHardware, hardwareWithDifferentId);
//        assertNotEquals(validHardware.hashCode(), hardwareWithDifferentId.hashCode());
//        assertNotEquals(validHardware, null);
//    }
}