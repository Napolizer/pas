package org.pl.repair.module.adapter.data.aggregates;

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
import org.pl.repair.module.model.exceptions.RepairException;
import org.pl.repair.module.model.exceptions.RepositoryException;

import javax.transaction.UserTransaction;
import java.io.File;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ArquillianExtension.class)
public class RepairRepositoryAdapterIT {
    @Inject
    private RepairRepositoryAdapter repairRepositoryAdapter;
    @Inject
    private UserTransaction userTransaction;
    @Inject
    private EntityManager entityManager;
    private Client validClient;
    private Client validClient2;
    private Address validAddress;
    private Hardware validHardware;
    private Hardware validHardware2;
    private Computer validComputer;
    private Monitor validMonitor;
    private DateRange dateRange;
    private DateRange dateRange2;
    private Repair validRepair;
    private Repair validRepair2;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "org.pl")
                .addPackages(true, "org.hamcrest")
                .addAsResource(new File("src/main/resources/"),"")
                .addAsResource(new File("target/classes/META-INF/"), "META-INF/")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @BeforeEach
    void setup() {
        validAddress = Address.builder()
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
        validClient2 = Client.builder()
                .id(UUID.randomUUID())
                .username("Username2")
                .archive(true)
                .balance(100.0)
                .firstName("Tester")
                .lastName("Testowy")
                .phoneNumber("123456789")
                .clientType(new Basic())
                .address(validAddress)
                .clientAccessType(ClientAccessType.EMPLOYEES)
                .build();
        validComputer = Computer.builder()
                .condition(Condition.AVERAGE)
                .build();
        validMonitor = Monitor.builder()
                .condition(Condition.FINE)
                .build();
        validHardware = Hardware.builder()
                .id(UUID.randomUUID())
                .archive(false)
                .price(200)
                .hardwareType(validComputer)
                .build();
        validHardware2 = Hardware.builder()
                .id(UUID.randomUUID())
                .archive(true)
                .price(222)
                .hardwareType(validMonitor)
                .build();
        dateRange = new DateRange(new Date(), new Date());
        dateRange2 = new DateRange(new Date(), new Date());
        validRepair = Repair.builder()
                .id(UUID.randomUUID())
                .archive(false)
                .client(validClient)
                .hardware(validHardware)
                .dateRange(dateRange)
                .build();
        validRepair2 = Repair.builder()
                .id(UUID.randomUUID())
                .archive(true)
                .client(validClient2)
                .hardware(validHardware2)
                .dateRange(dateRange2)
                .build();
        try {
            userTransaction.begin();
            entityManager.clear();
            userTransaction.commit();
        } catch (Exception ignored) {

        }
    }

    @Test
    void createRepairPositiveTest() throws RepositoryException {
        Repair createdRepair = repairRepositoryAdapter.createRepair(validRepair);
        assertNotNull(createdRepair.getId());
        assertEquals(validRepair.getArchive(), createdRepair.getArchive());
        assertEquals(validRepair.getClient(), createdRepair.getClient());
        assertEquals(validRepair.getHardware(), createdRepair.getHardware());
        assertEquals(validRepair.getDateRange(), createdRepair.getDateRange());
    }

    @Test
    void createRepairNegativeTest() throws RepositoryException {
        repairRepositoryAdapter.createRepair(validRepair);
        assertThrows(RepairException.class, () -> repairRepositoryAdapter.createRepair(validRepair));
    }

    @Test
    void getRepairByUuidPositiveTest() throws RepositoryException {
        Repair createdRepair = repairRepositoryAdapter.createRepair(validRepair);
        assertEquals(createdRepair.getId(), repairRepositoryAdapter.getRepair(createdRepair.getId()).getId());
    }

    @Test
    void getRepairByUuidNegativeTest() {
        assertThrows(RepositoryException.class, () -> repairRepositoryAdapter.getRepair(validRepair.getId()));
    }

    @Test
    void deleteRepairPositiveTest() throws RepositoryException {
        Repair createdRepair = repairRepositoryAdapter.createRepair(validRepair);
        assertFalse(repairRepositoryAdapter.getRepair(createdRepair.getId()).getArchive());
        repairRepositoryAdapter.deleteRepair(createdRepair.getId());
        assertTrue(repairRepositoryAdapter.getRepair(createdRepair.getId()).getArchive());
    }

    @Test
    void deleteRepairNegativeTest() {
        assertThrows(RepositoryException.class, () -> repairRepositoryAdapter.deleteRepair(validRepair.getId()));
    }

    @Test
    void getAllRepairsTest() throws RepositoryException {
        assertEquals(0, repairRepositoryAdapter.getAllRepairs().size());
        repairRepositoryAdapter.createRepair(validRepair);
        assertEquals(1, repairRepositoryAdapter.getAllRepairs().size());
        repairRepositoryAdapter.createRepair(validRepair2);
        assertEquals(2, repairRepositoryAdapter.getAllRepairs().size());
    }

    @Test
    void getRepairListTest() throws RepositoryException {
        assertEquals(0, repairRepositoryAdapter.getRepairList(false).size());
        assertEquals(0, repairRepositoryAdapter.getRepairList(true).size());
        repairRepositoryAdapter.createRepair(validRepair);
        assertEquals(1, repairRepositoryAdapter.getRepairList(false).size());
        assertEquals(0, repairRepositoryAdapter.getRepairList(true).size());
        repairRepositoryAdapter.createRepair(validRepair2);
        assertEquals(1, repairRepositoryAdapter.getRepairList(false).size());
        assertEquals(1, repairRepositoryAdapter.getRepairList(true).size());
    }

    @Test
    void updateRepairPositiveTest() throws RepositoryException {
        Repair createdRepair = repairRepositoryAdapter.createRepair(validRepair);
        Repair updatedRepair = repairRepositoryAdapter.updateRepair(createdRepair.getId(), validRepair2);
        assertEquals(createdRepair.getId(), updatedRepair.getId());
        assertEquals(validRepair2.getArchive(), updatedRepair.getArchive());
        assertEquals(validRepair2.getClient(), updatedRepair.getClient());
        assertEquals(validRepair2.getHardware(), updatedRepair.getHardware());
        assertEquals(validRepair2.getDateRange(), updatedRepair.getDateRange());
    }
}
