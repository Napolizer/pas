package org.pl.adapter.data;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.adapter.data.model.*;
import org.pl.adapter.data.model.exceptions.HardwareEntException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class RepairEntTest {
    ValidatorFactory validatorFactory;
    Validator validator;
    ClientEnt validClient;
    HardwareEnt validHardware;
    AddressEnt validAddress;
    RepairEnt validRepair;
    DateRangeEnt validDateRange;

    @BeforeEach
    void setup() throws ParseException {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();

        validAddress = AddressEnt.builder()
                .city("Warsaw")
                .number("123456789")
                .street("Street")
                .build();

        validClient = ClientEnt.builder()
                .id(UUID.randomUUID())
                .username("TestUser")
                .password("TestPassword")
                .archive(false)
                .balance(100.0)
                .firstName("Władek")
                .lastName("Tester")
                .phoneNumber("123456789")
                .clientTypeEnt(new BasicEnt())
                .addressEnt(validAddress)
                .clientAccessTypeEnt(ClientAccessTypeEnt.ADMINISTRATORS)
                .build();

        validHardware = HardwareEnt.builder()
                .id(UUID.randomUUID())
                .archive(false)
                .price(100)
                .hardwareTypeEnt(new ConsoleEnt(ConditionEnt.VERY_BAD))
                .build();

        validDateRange = new DateRangeEnt(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse("13-02-2020 12:10:10"),
                new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse("15-02-2020 12:10:10"));

        validRepair = RepairEnt.builder()
                .id(UUID.randomUUID())
                .archive(false)
                .clientEnt(validClient)
                .hardwareEnt(validHardware)
                .dateRangeEnt(validDateRange)
                .build();
    }

    @Test
    void fieldArchivePositiveTest() {
        List<Boolean> validArchiveValues = List.of(
                true,
                false
        );
        for (var archive : validArchiveValues) {
            validRepair.setArchive(archive);
            assertEquals(archive, validRepair.getArchive());
            assertTrue(validator.validate(validRepair).isEmpty());
        }
    }

    @Test
    void fieldClientPositiveTest() {
        List<ClientEnt> validClients = List.of(
                validClient,
                new ClientEnt()
        );
        for (var client : validClients) {
            validRepair.setClientEnt(client);
            assertEquals(client, validRepair.getClientEnt());
            assertTrue(validator.validate(validRepair).isEmpty());
        }
        validRepair.setClientEnt(null);
        assertNull(validRepair.getClientEnt());
        assertTrue(validator.validate(validRepair).isEmpty());
    }

    @Test
    void fieldHardwarePositiveTest() {
        List<HardwareEnt> validHardwares = List.of(
                validHardware,
                new HardwareEnt()
        );
        for (var hardware : validHardwares) {
            validRepair.setHardwareEnt(hardware);
            assertEquals(hardware, validRepair.getHardwareEnt());
            assertTrue(validator.validate(validRepair).isEmpty());
        }
        validRepair.setHardwareEnt(null);
        assertNull(validRepair.getHardwareEnt());
        assertTrue(validator.validate(validRepair).isEmpty());
    }

    @Test
    void fieldDateRangePositiveTest() {
        List<DateRangeEnt> validDateRanges = List.of(
                validDateRange,
                new DateRangeEnt()
        );
        for (var dateRange : validDateRanges) {
            validRepair.setDateRangeEnt(dateRange);
            assertEquals(dateRange, validRepair.getDateRangeEnt());
            assertTrue(validator.validate(validRepair).isEmpty());
        }
        validRepair.setDateRangeEnt(null);
        assertNull(validRepair.getDateRangeEnt());
        assertDoesNotThrow(() -> validator.validate(validRepair));
    }

    @Test
    void calculateRepairCostPositiveTest() throws HardwareEntException {
        assertNotNull(validRepair.getHardwareEnt());
        assertEquals(90.0, validRepair.calculateRepairCost(), 0.001);
    }

    @Test
    void calculateRepairCostNegativeTest() {
        validRepair.setHardwareEnt(null);
        assertNull(validRepair.getHardwareEnt());
        assertThrows(Exception.class, () -> validRepair.calculateRepairCost());
        validRepair.setHardwareEnt(HardwareEnt.builder()
                .id(UUID.randomUUID())
                .hardwareTypeEnt(new ConsoleEnt(ConditionEnt.AVERAGE))
                .archive(false)
                .price(-2)
                .build());
        assertNotNull(validRepair.getHardwareEnt());
        assertThrows(HardwareEntException.class, () -> validRepair.calculateRepairCost());
    }

    @Test
    void equalsAndHashcodePositiveTest() {
        RepairEnt theSameRepair = RepairEnt.builder()
                .id(validRepair.getId())
                .archive(false)
                .clientEnt(validClient)
                .hardwareEnt(validHardware)
                .dateRangeEnt(validDateRange)
                .build();

        assertEquals(validRepair, theSameRepair);
        assertEquals(validRepair.hashCode(), theSameRepair.hashCode());
    }

    @Test
    void equalsAndHashcodeNegativeTest() {
        RepairEnt repairWithDifferentId = RepairEnt.builder()
                .id(UUID.randomUUID())
                .archive(false)
                .clientEnt(validClient)
                .hardwareEnt(validHardware)
                .dateRangeEnt(validDateRange)
                .build();

        assertNotEquals(validRepair, repairWithDifferentId);
        assertNotEquals(validRepair.hashCode(), repairWithDifferentId.hashCode());
        assertNotEquals(validRepair, null);
    }
}
