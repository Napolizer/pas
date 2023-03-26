package org.pl.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class HardwareSoapTest {
    ValidatorFactory validatorFactory;
    Validator validator;
    HardwareSoap validHardware;
    List<HardwareTypeSoap> validHardwareTypes;

    @BeforeEach
    void setup() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();

        validHardware = new HardwareSoap(UUID.randomUUID(), false, 100, new ConsoleSoap(ConditionSoap.VERY_BAD));
        validHardwareTypes = List.of(
                new ConsoleSoap(ConditionSoap.BAD),
                new ComputerSoap(ConditionSoap.VERY_BAD),
                new PhoneSoap(ConditionSoap.AVERAGE),
                new MonitorSoap(ConditionSoap.FINE)
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
    }

    @Test
    void fieldHardwareTypePositiveTest() {
        for (var hardwareType : validHardwareTypes) {
            validHardware.setHardwareType(hardwareType);
            assertEquals(hardwareType, validHardware.getHardwareType());
            assertTrue(validator.validate(validHardware).isEmpty());
        }
    }

    @Test
    void repairPositiveTest() {
        assertEquals(ConditionSoap.VERY_BAD, validHardware.getHardwareType().getCondition());
        validHardware.repair();
        assertEquals(ConditionSoap.FINE, validHardware.getHardwareType().getCondition());
    }

    @Test
    void equalsAndHashcodePositiveTest() {
        HardwareSoap theSameHardware = new HardwareSoap(validHardware.getId(), false, 100, new ConsoleSoap(ConditionSoap.VERY_BAD));
        assertEquals(validHardware, theSameHardware);
        assertEquals(validHardware.hashCode(), theSameHardware.hashCode());
    }

    @Test
    void equalsAndHashcodeNegativeTest() {
        HardwareSoap hardwareWithDifferentId = new HardwareSoap(UUID.randomUUID(), false, 100, new ConsoleSoap(ConditionSoap.VERY_BAD));

        assertNotEquals(validHardware, hardwareWithDifferentId);
        assertNotEquals(validHardware.hashCode(), hardwareWithDifferentId.hashCode());
        assertNotEquals(validHardware, null);
    }
}