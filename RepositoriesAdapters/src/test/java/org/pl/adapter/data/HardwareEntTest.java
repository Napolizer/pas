package org.pl.adapter.data;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.adapter.data.model.*;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class HardwareEntTest {
    ValidatorFactory validatorFactory;
    Validator validator;
    HardwareEnt validHardware;
    List<HardwareTypeEnt> validHardwareTypes;

    @BeforeEach
    void setup() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();

        validHardware = HardwareEnt.builder()
                .id(UUID.randomUUID())
                .archive(false)
                .price(100)
                .hardwareTypeEnt(new ConsoleEnt(ConditionEnt.VERY_BAD))
                .build();

        validHardwareTypes = List.of(
                new ConsoleEnt(),
                new ComputerEnt(),
                new PhoneEnt(),
                new MonitorEnt()
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
            validHardware.setHardwareTypeEnt(hardwareType);
            assertEquals(hardwareType, validHardware.getHardwareTypeEnt());
            assertTrue(validator.validate(validHardware).isEmpty());
        }
        validHardware.setHardwareTypeEnt(null);
        assertNull(validHardware.getHardwareTypeEnt());
        assertTrue(validator.validate(validHardware).isEmpty());
    }

    @Test
    void repairPositiveTest() {
        assertEquals(ConditionEnt.VERY_BAD, validHardware.getHardwareTypeEnt().getCondition());
        validHardware.repair();
        assertEquals(ConditionEnt.FINE, validHardware.getHardwareTypeEnt().getCondition());
    }

    @Test
    void equalsAndHashcodePositiveTest() {
        HardwareEnt theSameHardware = HardwareEnt.builder()
                .id(validHardware.getId())
                .archive(false)
                .price(100)
                .hardwareTypeEnt(new ConsoleEnt(ConditionEnt.VERY_BAD))
                .build();

        assertEquals(validHardware, theSameHardware);
        assertEquals(validHardware.hashCode(), theSameHardware.hashCode());
    }

    @Test
    void equalsAndHashcodeNegativeTest() {
        HardwareEnt hardwareWithDifferentId = HardwareEnt.builder()
                .id(UUID.randomUUID())
                .archive(false)
                .price(100)
                .hardwareTypeEnt(new ConsoleEnt(ConditionEnt.VERY_BAD))
                .build();

        assertNotEquals(validHardware, hardwareWithDifferentId);
        assertNotEquals(validHardware.hashCode(), hardwareWithDifferentId.hashCode());
        assertNotEquals(validHardware, null);
    }
}