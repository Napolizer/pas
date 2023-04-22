package org.pl.repair.module.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.repair.module.model.*;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class HardwareTest {
    ValidatorFactory validatorFactory;
    Validator validator;
    Hardware validHardware;
    List<HardwareType> validHardwareTypes;

    @BeforeEach
    void setup() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();

        validHardware = Hardware.builder()
                .id(UUID.randomUUID())
                .archive(false)
                .price(100)
                .hardwareType(new Console(Condition.VERY_BAD))
                .build();

        validHardwareTypes = List.of(
                new Console(),
                new Computer(),
                new Phone(),
                new Monitor()
        );
    }

    @Test
    void fieldArchivePositiveTest() {
        List<Boolean> validArchiveValues = List.of(
                true,
                false
        );
        for (var archive : validArchiveValues) {
            validHardware.setArchive(archive);
            assertEquals(archive, validHardware.getArchive());
            assertTrue(validator.validate(validHardware).isEmpty());
        }
    }

    @Test
    void fieldPricePositiveTest() {
        List<Integer> validPriceValues = List.of(
                100,
                20,
                1
        );
        for (var price : validPriceValues) {
            validHardware.setPrice(price);
            assertEquals(price, validHardware.getPrice());
            assertTrue(validator.validate(validHardware).isEmpty());
        }
        validHardware.setPrice(null);
        assertNull(validHardware.getPrice());
        assertTrue(validator.validate(validHardware).isEmpty());
    }

    @Test
    void fieldHardwareTypePositiveTest() {
        for (var hardwareType : validHardwareTypes) {
            validHardware.setHardwareType(hardwareType);
            assertEquals(hardwareType, validHardware.getHardwareType());
            assertTrue(validator.validate(validHardware).isEmpty());
        }

        validHardware.setHardwareType(null);
        assertNull(validHardware.getHardwareType());
        assertTrue(validator.validate(validHardware).isEmpty());
    }

    @Test
    void repairPositiveTest() {
        assertEquals(Condition.VERY_BAD, validHardware.getHardwareType().getCondition());
        validHardware.repair();
        assertEquals(Condition.FINE, validHardware.getHardwareType().getCondition());
    }

    @Test
    void equalsAndHashcodePositiveTest() {
        Hardware theSameHardware = Hardware.builder()
                .id(validHardware.getId())
                .archive(false)
                .price(100)
                .hardwareType(new Console(Condition.VERY_BAD))
                .build();

        assertEquals(validHardware, theSameHardware);
        assertEquals(validHardware.hashCode(), theSameHardware.hashCode());
    }

    @Test
    void equalsAndHashcodeNegativeTest() {
        Hardware hardwareWithDifferentId = Hardware.builder()
                .id(UUID.randomUUID())
                .archive(false)
                .price(100)
                .hardwareType(new Console(Condition.VERY_BAD))
                .build();

        assertNotEquals(validHardware, hardwareWithDifferentId);
        assertNotEquals(validHardware.hashCode(), hardwareWithDifferentId.hashCode());
        assertNotEquals(validHardware, null);
    }
}