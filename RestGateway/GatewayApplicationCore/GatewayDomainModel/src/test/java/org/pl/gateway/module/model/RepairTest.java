package org.pl.gateway.module.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.gateway.module.model.exceptions.HardwareException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class RepairTest {
    ValidatorFactory validatorFactory;
    Validator validator;
    Client validClient;
    Hardware validHardware;
    Address validAddress;
    Repair validRepair;
    DateRange validDateRange;

    @BeforeEach
    void setup() throws ParseException {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();

        validAddress = Address.builder()
                .city("Warsaw")
                .number("123456789")
                .street("Street")
                .build();

        validClient = Client.builder()
                .id(UUID.randomUUID())
                .username("TestUser")
                .password("TestPassword")
                .archive(false)
                .balance(100.0)
                .firstName("Władek")
                .lastName("Tester")
                .phoneNumber("123456789")
                .clientType(new Basic())
                .address(validAddress)
                .clientAccessType(ClientAccessType.ADMINISTRATORS)
                .build();

        validHardware = Hardware.builder()
                .id(UUID.randomUUID())
                .archive(false)
                .price(100)
                .hardwareType(new Console(Condition.VERY_BAD))
                .build();

        validDateRange = new DateRange(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse("13-02-2020 12:10:10"),
                new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse("15-02-2020 12:10:10"));

        validRepair = Repair.builder()
                .id(UUID.randomUUID())
                .archive(false)
                .client(validClient)
                .hardware(validHardware)
                .dateRange(validDateRange)
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
        List<Client> validClients = List.of(
                validClient,
                new Client()
        );
        for (var client : validClients) {
            validRepair.setClient(client);
            assertEquals(client, validRepair.getClient());
            assertTrue(validator.validate(validRepair).isEmpty());
        }

        validRepair.setClient(null);
        assertNull(validRepair.getClient());
        assertTrue(validator.validate(validRepair).isEmpty());
    }

    @Test
    void fieldHardwarePositiveTest() {
        List<Hardware> validHardwares = List.of(
                validHardware,
                new Hardware()
        );
        for (var hardware : validHardwares) {
            validRepair.setHardware(hardware);
            assertEquals(hardware, validRepair.getHardware());
            assertTrue(validator.validate(validRepair).isEmpty());
        }
        validRepair.setHardware(null);
        assertNull(validRepair.getHardware());
        assertTrue(validator.validate(validRepair).isEmpty());
    }

    @Test
    void fieldDateRangePositiveTest() {
        List<DateRange> validDateRanges = List.of(
                validDateRange,
                new DateRange()
        );
        for (var dateRange : validDateRanges) {
            validRepair.setDateRange(dateRange);
            assertEquals(dateRange, validRepair.getDateRange());
            assertTrue(validator.validate(validRepair).isEmpty());
        }

        validRepair.setDateRange(null);
        assertNull(validRepair.getDateRange());
        assertDoesNotThrow(() -> validator.validate(validRepair).isEmpty());
    }

    @Test
    void calculateRepairCostPositiveTest() throws HardwareException {
        assertNotNull(validRepair.getHardware());
        assertEquals(90.0, validRepair.calculateRepairCost(), 0.001);
    }

    @Test
    void calculateRepairCostNegativeTest() {
        validRepair.setHardware(null);
        assertNull(validRepair.getHardware());
        assertThrows(Exception.class, () -> validRepair.calculateRepairCost());
        validRepair.setHardware(Hardware.builder()
                .id(UUID.randomUUID())
                .hardwareType(new Console(Condition.AVERAGE))
                .archive(false)
                .price(-2)
                .build());
        assertNotNull(validRepair.getHardware());
        assertThrows(HardwareException.class, () -> validRepair.calculateRepairCost());
    }

    @Test
    void equalsAndHashcodePositiveTest() {
        Repair theSameRepair = Repair.builder()
                .id(validRepair.getId())
                .archive(false)
                .client(validClient)
                .hardware(validHardware)
                .dateRange(validDateRange)
                .build();

        assertEquals(validRepair, theSameRepair);
        assertEquals(validRepair.hashCode(), theSameRepair.hashCode());
    }

    @Test
    void equalsAndHashcodeNegativeTest() {
        Repair repairWithDifferentId = Repair.builder()
                .id(UUID.randomUUID())
                .archive(false)
                .client(validClient)
                .hardware(validHardware)
                .dateRange(validDateRange)
                .build();

        assertNotEquals(validRepair, repairWithDifferentId);
        assertNotEquals(validRepair.hashCode(), repairWithDifferentId.hashCode());
        assertNotEquals(validRepair, null);
    }
}
