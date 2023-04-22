package org.pl.gateway.module.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.gateway.module.model.*;
import org.pl.gateway.module.model.exceptions.HardwareRestException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class RepairTest {
//    ValidatorFactory validatorFactory;
//    Validator validator;
//    ClientRest validClient;
//    HardwareRest validHardware;
//    AddressRest validAddress;
//    RepairRest validRepair;
//    DateRangeRest validDateRange;
//
//    @BeforeEach
//    void setup() throws ParseException {
//        validatorFactory = Validation.buildDefaultValidatorFactory();
//        validator = validatorFactory.getValidator();
//
//        validAddress = AddressRest.builder()
//                .city("Warsaw")
//                .number("123456789")
//                .street("Street")
//                .build();
//
//        validClient = ClientRest.builder()
//                .id(UUID.randomUUID())
//                .username("TestUser")
//                .password("TestPassword")
//                .archive(false)
//                .balance(100.0)
//                .firstName("Władek")
//                .lastName("Tester")
//                .phoneNumber("123456789")
//                .clientType(new BasicRest())
//                .address(validAddress)
//                .clientAccessType(ClientAccessTypeRest.ADMINISTRATORS)
//                .build();
//
//        validHardware = HardwareRest.builder()
//                .id(UUID.randomUUID())
//                .archive(false)
//                .price(100)
//                .hardwareType(new ConsoleRest(ConditionRest.VERY_BAD))
//                .build();
//
//        validDateRange = new DateRangeRest(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse("13-02-2020 12:10:10"),
//                new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse("15-02-2020 12:10:10"));
//
//        validRepair = RepairRest.builder()
//                .id(UUID.randomUUID())
//                .archive(false)
//                .client(validClient)
//                .hardware(validHardware)
//                .dateRange(validDateRange)
//                .build();
//    }
//
//    @Test
//    void fieldArchivePositiveTest() {
//        List<Boolean> validArchiveValues = List.of(
//                true,
//                false
//        );
//        for (var archive : validArchiveValues) {
//            validRepair.setArchive(archive);
//            assertEquals(archive, validRepair.getArchive());
//            assertTrue(validator.validate(validRepair).isEmpty());
//        }
//    }
//
//    @Test
//    void fieldClientPositiveTest() {
//        List<ClientRest> validClients = List.of(
//                validClient,
//                new ClientRest()
//        );
//        for (var client : validClients) {
//            validRepair.setClient(client);
//            assertEquals(client, validRepair.getClient());
//            assertTrue(validator.validate(validRepair).isEmpty());
//        }
//    }
//
//    @Test
//    void fieldClientNegativeTest() {
//        validRepair.setClient(null);
//        assertNull(validRepair.getClient());
//        assertFalse(validator.validate(validRepair).isEmpty());
//    }
//
//    @Test
//    void fieldHardwarePositiveTest() {
//        List<HardwareRest> validHardwares = List.of(
//                validHardware,
//                new HardwareRest()
//        );
//        for (var hardware : validHardwares) {
//            validRepair.setHardware(hardware);
//            assertEquals(hardware, validRepair.getHardware());
//            assertTrue(validator.validate(validRepair).isEmpty());
//        }
//    }
//
//    @Test
//    void fieldHardwareNegativeTest() {
//        validRepair.setHardware(null);
//        assertNull(validRepair.getHardware());
//        assertFalse(validator.validate(validRepair).isEmpty());
//    }
//
//    @Test
//    void fieldDateRangePositiveTest() {
//        List<DateRangeRest> validDateRanges = List.of(
//                validDateRange,
//                new DateRangeRest()
//        );
//        for (var dateRange : validDateRanges) {
//            validRepair.setDateRange(dateRange);
//            assertEquals(dateRange, validRepair.getDateRange());
//            assertTrue(validator.validate(validRepair).isEmpty());
//        }
//    }
//
//    @Test
//    void fieldDateRangeNegativeTest() {
//        validRepair.setDateRange(null);
//        assertNull(validRepair.getDateRange());
//        assertThrows(Exception.class, () -> validator.validate(validRepair).isEmpty());
//    }
//
//    @Test
//    void calculateRepairCostPositiveTest() throws HardwareRestException {
//        assertNotNull(validRepair.getHardware());
//        assertEquals(90.0, validRepair.calculateRepairCost(), 0.001);
//    }
//
//    @Test
//    void calculateRepairCostNegativeTest() {
//        validRepair.setHardware(null);
//        assertNull(validRepair.getHardware());
//        assertThrows(Exception.class, () -> validRepair.calculateRepairCost());
//        validRepair.setHardware(HardwareRest.builder()
//                .id(UUID.randomUUID())
//                .hardwareType(new ConsoleRest(ConditionRest.AVERAGE))
//                .archive(false)
//                .price(-2)
//                .build());
//        assertNotNull(validRepair.getHardware());
//        assertThrows(HardwareRestException.class, () -> validRepair.calculateRepairCost());
//    }
//
//    @Test
//    void equalsAndHashcodePositiveTest() {
//        RepairRest theSameRepair = RepairRest.builder()
//                .id(validRepair.getId())
//                .archive(false)
//                .client(validClient)
//                .hardware(validHardware)
//                .dateRange(validDateRange)
//                .build();
//
//        assertEquals(validRepair, theSameRepair);
//        assertEquals(validRepair.hashCode(), theSameRepair.hashCode());
//    }
//
//    @Test
//    void equalsAndHashcodeNegativeTest() {
//        RepairRest repairWithDifferentId = RepairRest.builder()
//                .id(UUID.randomUUID())
//                .archive(false)
//                .client(validClient)
//                .hardware(validHardware)
//                .dateRange(validDateRange)
//                .build();
//
//        assertNotEquals(validRepair, repairWithDifferentId);
//        assertNotEquals(validRepair.hashCode(), repairWithDifferentId.hashCode());
//        assertNotEquals(validRepair, null);
//    }
}
