package org.pl.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.model.exceptions.HardwareException;
import org.pl.model.exceptions.HardwareSoapException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class RepairSoapTest {
    ValidatorFactory validatorFactory;
    Validator validator;
    ClientSoap validClient;
    HardwareSoap validHardware;
    AddressSoap validAddress;
    RepairSoap validRepair;
    DateRangeSoap validDateRange;

    @BeforeEach
    void setup() throws ParseException {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();

        validAddress = new AddressSoap("Warsaw", "123456789", "Street");

        validClient = new ClientSoap(UUID.randomUUID(), "TestUser", "TestPassword", false, 100.0, "Władek", "Tester", "123456789",
                new BasicSoap(), validAddress, ClientAccessTypeSoap.ADMINISTRATORS);

        validHardware = new HardwareSoap(UUID.randomUUID(), false, 100, new ConsoleSoap(ConditionSoap.VERY_BAD));

        validDateRange = new DateRangeSoap(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse("13-02-2020 12:10:10"),
                new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse("15-02-2020 12:10:10"));

        validRepair = new RepairSoap(UUID.randomUUID(), false, validClient, validHardware, validDateRange);
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
        List<ClientSoap> validClients = List.of(
                validClient,
                new ClientSoap()
        );
        for (var client : validClients) {
            validRepair.setClient(client);
            assertEquals(client, validRepair.getClient());
            assertTrue(validator.validate(validRepair).isEmpty());
        }
    }

    @Test
    void fieldHardwarePositiveTest() {
        List<HardwareSoap> validHardwares = List.of(
                validHardware,
                new HardwareSoap()
        );
        for (var hardware : validHardwares) {
            validRepair.setHardware(hardware);
            assertEquals(hardware, validRepair.getHardware());
            assertTrue(validator.validate(validRepair).isEmpty());
        }
    }

    @Test
    void fieldDateRangePositiveTest() {
        List<DateRangeSoap> validDateRanges = List.of(
                validDateRange,
                new DateRangeSoap()
        );
        for (var dateRange : validDateRanges) {
            validRepair.setDateRange(dateRange);
            assertEquals(dateRange, validRepair.getDateRange());
            assertTrue(validator.validate(validRepair).isEmpty());
        }
    }

    @Test
    void calculateRepairCostPositiveTest() throws HardwareSoapException {
        assertNotNull(validRepair.getHardware());
        assertEquals(90.0, validRepair.calculateRepairCost(), 0.001);
    }

    @Test
    void calculateRepairCostNegativeTest() {
        validRepair.setHardware(null);
        assertNull(validRepair.getHardware());
        assertThrows(Exception.class, () -> validRepair.calculateRepairCost());
        validRepair.setHardware(new HardwareSoap(UUID.randomUUID(), false, -2, new ConsoleSoap(ConditionSoap.AVERAGE)));
        assertNotNull(validRepair.getHardware());
        assertThrows(HardwareSoapException.class, () -> validRepair.calculateRepairCost());
    }

    @Test
    void equalsAndHashcodePositiveTest() {
        RepairSoap theSameRepair = new RepairSoap(validRepair.getId(), false, validClient, validHardware, validDateRange);
        assertEquals(validRepair, theSameRepair);
        assertEquals(validRepair.hashCode(), theSameRepair.hashCode());
    }

    @Test
    void equalsAndHashcodeNegativeTest() {
        RepairSoap repairWithDifferentId = new RepairSoap(UUID.randomUUID(), false, validClient, validHardware, validDateRange);
        assertNotEquals(validRepair, repairWithDifferentId);
        assertNotEquals(validRepair.hashCode(), repairWithDifferentId.hashCode());
        assertNotEquals(validRepair, null);
    }
}
