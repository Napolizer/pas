package org.pl.repair.module.services;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.pl.model.*;
import org.pl.repair.module.model.*;
import org.pl.repair.module.model.exceptions.ClientException;
import org.pl.repair.module.model.exceptions.HardwareException;
import org.pl.repair.module.model.exceptions.RepositoryException;
import org.pl.repair.module.services.RepairService;

import javax.transaction.UserTransaction;
import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ArquillianExtension.class)
class RepairServiceIT {
    @Inject
    private RepairService repairService;
    @Inject
    private UserTransaction userTransaction;
    @Inject
    private EntityManager entityManager;
    private Hardware validHardware;
    private Client validClient;
    private Repair validRepair;


    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "org.pl")
                .addPackages(true, "org.hamcrest")
                .addAsResource(new File("src/test/resources/META-INF"),"")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @BeforeEach
    void setUp() {
        HardwareType validHardwareType = new Computer();
        validHardware = Hardware.builder()
                .id(UUID.randomUUID())
                .archive(false)
                .price(200)
                .hardwareType(validHardwareType)
                .build();
        Address validAddress = Address.builder()
                .city("Lodz")
                .street("Przybyszewskiego")
                .number("21")
                .build();
        validClient = Client.builder()
                .id(UUID.randomUUID())
                .username("Username")
                .archive(false)
                .balance(100.0)
                .firstName("Tester")
                .lastName("Testowy")
                .phoneNumber("123456789")
                .clientType(new Basic())
                .address(validAddress)
                .clientAccessType(ClientAccessType.EMPLOYEES)
                .build();
        validRepair = Repair.builder()
                .id(UUID.randomUUID())
                .archive(false)
                .client(validClient)
                .hardware(validHardware)
                .dateRange(new DateRange(
                        Date.from(LocalDateTime.now().toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                        Date.from(LocalDateTime.now().plusDays(1).toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant())))
                .build();
        try {
            userTransaction.begin();
            entityManager.clear();
            userTransaction.commit();
        } catch (Exception ignored) {

        }
    }

    @Test
    void addRepairPositiveTest() throws RepositoryException {
        Repair createdRepair = repairService.add(validRepair);
        assertEquals(validRepair.getArchive(), createdRepair.getArchive());
        assertEquals(validRepair.getClient().getUsername(), createdRepair.getClient().getUsername());
        assertEquals(validRepair.getHardware().getPrice(), createdRepair.getHardware().getPrice());
        assertEquals(validRepair.getDateRange(), createdRepair.getDateRange());
    }

    @Test
    void addRepairNegativeClientIsNullTest() {
        validRepair.setClient(null);
        assertThrows(RepositoryException.class, () -> repairService.add(validRepair));
    }

    @Test
    void addRepairNegativeHardwareIsNullTest() {
        validRepair.setHardware(null);
        assertThrows(RepositoryException.class, () -> repairService.add(validRepair));
    }

    @Test
    void addRepairNegativeDateRangeIsNullTest() {
        validRepair.setDateRange(null);
        assertThrows(RepositoryException.class, () -> repairService.add(validRepair));
    }

    @Test
    void getRepairPositiveTest() throws RepositoryException {
        Repair createdRepair = repairService.add(validRepair);
        assertEquals(createdRepair.getDateRange(), repairService.get(createdRepair.getId()).getDateRange());
        assertEquals(createdRepair.getClient().getFirstName(), repairService.get(createdRepair.getId()).getClient().getFirstName());
        assertEquals(createdRepair.getHardware().getPrice(), repairService.get(createdRepair.getId()).getHardware().getPrice());
        assertEquals(createdRepair.getArchive(), repairService.get(createdRepair.getId()).getArchive());
    }

    @Test
    void getRepairNegativeTest() {
        assertThrows(RepositoryException.class, () -> repairService.get(validRepair.getId()));
    }

    @Test
    void getInfoTest() throws RepositoryException {
        Repair createdRepair = repairService.add(validRepair);
        String repairInfo = createdRepair.toString();
        assertEquals(repairInfo, repairService.getInfo(createdRepair.getId()));
    }

    @Test
    void getAllClientRepairsTest() throws RepositoryException {
        Repair validRepair2 = Repair.builder()
                .id(UUID.randomUUID())
                .archive(false)
                .client(validClient)
                .hardware(validHardware)
                .dateRange(new DateRange(new Date(), new Date()))
                .build();
        repairService.add(validRepair);
        assertTrue(repairService.getAllClientRepairs(validClient.getId()).size() > 0);
        repairService.add(validRepair2);
        assertTrue(repairService.getAllClientRepairs(validClient.getId()).size() > 1);
    }

    @Test
    void isRepairArchivePositiveTest() throws RepositoryException {
        Repair createdRepair = repairService.add(validRepair);
        assertFalse(repairService.isRepairArchive(createdRepair.getId()));
        repairService.archivize(createdRepair.getId());
        assertTrue(repairService.isRepairArchive(createdRepair.getId()));
    }

    @Test
    void isRepairArchiveNegativeTest() {
        assertThrows(RepositoryException.class, () -> repairService.isRepairArchive(validRepair.getId()));
    }

    @Test
    void archivizePositiveTest() throws RepositoryException {
        Repair createdRepair = repairService.add(validRepair);
        assertFalse(repairService.isRepairArchive(createdRepair.getId()));
        repairService.archivize(createdRepair.getId());
        assertTrue(repairService.isRepairArchive(createdRepair.getId()));
    }

    @Test
    void archivizeNegativeTest() throws RepositoryException {
        assertThrows(RepositoryException.class, () -> repairService.archivize(validRepair.getId()));
        validRepair.setArchive(true);
        Repair createdRepair = repairService.add(validRepair);
        assertThrows(RepositoryException.class, () -> repairService.archivize(createdRepair.getId()));
    }

    @Test
    void repairPositiveTest() throws RepositoryException, HardwareException, ClientException {
        Repair createdRepair = repairService.add(validRepair);
        repairService.repair(createdRepair.getId());
        assertTrue(repairService.get(createdRepair.getId()).getArchive());
        assertTrue(repairService.get(createdRepair.getId()).getHardware().getArchive());
    }

    @Test
    void getPresentSizeTest() throws RepositoryException {
        repairService.add(validRepair);
        assertEquals(1, repairService.getPresentSize());
    }

    @Test
    void getArchiveSizeTest() throws RepositoryException {
        validRepair.setArchive(true);
        repairService.add(validRepair);
        assertEquals(1, repairService.getArchiveSize());
    }

    @Test
    void getClientsPastRepairsTest() throws RepositoryException {
        validRepair.setArchive(true);
        Repair createdRepair = repairService.add(validRepair);
        assertEquals(1, repairService.getClientsPastRepairs(createdRepair.getClient().getId()).size());
    }

    @Test
    void getClientsPresentRepairsTest() throws RepositoryException {
        Repair createdRepair = repairService.add(validRepair);
        assertEquals(1, repairService.getClientsPresentRepairs(createdRepair.getClient().getId()).size());
    }

    @Test
    void getAllRepairsTest() throws RepositoryException {
        assertEquals(0, repairService.getAllRepairs().size());
        repairService.add(validRepair);
        assertEquals(1, repairService.getAllRepairs().size());
        Repair validRepair2 = Repair.builder()
                .id(UUID.randomUUID())
                .archive(false)
                .client(validClient)
                .hardware(validHardware)
                .dateRange(new DateRange(new Date(), new Date()))
                .build();
        repairService.add(validRepair2);
        assertEquals(2, repairService.getAllRepairs().size());
    }

    @Test
    void updateRepairPositiveTest() throws RepositoryException {
        Repair createdRepair = repairService.add(validRepair);
        createdRepair.setArchive(true);
        repairService.updateRepair(createdRepair.getId(), createdRepair);
        assertEquals(createdRepair.getArchive(), repairService.get(createdRepair.getId()).getArchive());
        assertEquals(createdRepair.getClient(), repairService.get(createdRepair.getId()).getClient());
    }

    @Test
    void updateRepairNegativeTest() {
        assertThrows(RepositoryException.class, () -> repairService.updateRepair(validRepair.getId(), validRepair));
    }
}