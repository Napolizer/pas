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
import org.pl.repair.module.model.Computer;
import org.pl.repair.module.model.Condition;
import org.pl.repair.module.model.Hardware;
import org.pl.repair.module.model.Monitor;
import org.pl.repair.module.model.exceptions.RepositoryException;

import javax.transaction.UserTransaction;
import java.io.File;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ArquillianExtension.class)
public class HardwareRepositoryAdapterIT {
    @Inject
    private HardwareRepositoryAdapter hardwareRepositoryAdapter;
    @Inject
    private UserTransaction userTransaction;
    @Inject
    private EntityManager entityManager;
    private Hardware validHardware;
    private Hardware validHardware2;
    private Computer validComputer;
    private Monitor validMonitor;

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
        try {
            userTransaction.begin();
            entityManager.clear();
            userTransaction.commit();
        } catch (Exception ignored) {

        }
    }

    @Test
    void createHardwarePositiveTest() throws RepositoryException {
        Hardware createdHardware = hardwareRepositoryAdapter.createHardware(validHardware);
        assertNotNull(createdHardware.getId());
        assertEquals(validHardware.getArchive(), createdHardware.getArchive());
        assertEquals(validHardware.getPrice(), createdHardware.getPrice());
        assertEquals(validHardware.getHardwareType(), createdHardware.getHardwareType());
    }

    @Test
    void createHardwareNegativeTest() throws RepositoryException {
        Hardware createdHardware = hardwareRepositoryAdapter.createHardware(validHardware);
        assertThrows(RepositoryException.class, () -> hardwareRepositoryAdapter.createHardware(validHardware));
    }

    @Test
    void getHardwareByUuidPositiveTest() throws RepositoryException {
        Hardware createdHardware = hardwareRepositoryAdapter.createHardware(validHardware);
        assertEquals(createdHardware.getId(), hardwareRepositoryAdapter.getHardware(createdHardware.getId()).getId());
    }

    @Test
    void getHardwareByUuidNegativeTest() {
        assertThrows(RepositoryException.class, () -> hardwareRepositoryAdapter.getHardware(validHardware.getId()));
    }

    @Test
    void deleteHardwarePositiveTest() throws RepositoryException {
        Hardware createdHardware = hardwareRepositoryAdapter.createHardware(validHardware);
        assertFalse(hardwareRepositoryAdapter.getHardware(createdHardware.getId()).getArchive());
        hardwareRepositoryAdapter.deleteHardware(createdHardware.getId());
        assertTrue(hardwareRepositoryAdapter.getHardware(createdHardware.getId()).getArchive());
    }

    @Test
    void deleteHardwareNegativeTest() {
        assertThrows(RepositoryException.class, () -> hardwareRepositoryAdapter.deleteHardware(validHardware.getId()));
    }

    @Test
    void getAllHardwaresTest() throws RepositoryException {
        assertEquals(0, hardwareRepositoryAdapter.getAllHardwares().size());
        hardwareRepositoryAdapter.createHardware(validHardware);
        assertEquals(1, hardwareRepositoryAdapter.getAllHardwares().size());
        hardwareRepositoryAdapter.createHardware(validHardware2);
        assertEquals(2, hardwareRepositoryAdapter.getAllHardwares().size());
    }

    @Test
    void getAllPresentHardwareFilterTest() throws RepositoryException {
        assertEquals(0, hardwareRepositoryAdapter.getAllPresentHardwareFilter(validHardware.getId().toString()).size());
        Hardware createdHardware = hardwareRepositoryAdapter.createHardware(validHardware);
        assertEquals(1, hardwareRepositoryAdapter.getAllPresentHardwareFilter(createdHardware.getId().toString()).size());
        Hardware createdHardware2 = hardwareRepositoryAdapter.createHardware(validHardware2);
        assertEquals(1, hardwareRepositoryAdapter.getAllPresentHardwareFilter(createdHardware.getId().toString()).size());
    }

    @Test
    void getAllPresentHardwaresTest() throws RepositoryException {
        assertEquals(0, hardwareRepositoryAdapter.getAllPresentHardwares().size());
        hardwareRepositoryAdapter.createHardware(validHardware);
        assertEquals(1, hardwareRepositoryAdapter.getAllPresentHardwares().size());
        hardwareRepositoryAdapter.createHardware(validHardware2);
        assertEquals(1, hardwareRepositoryAdapter.getAllPresentHardwares().size());
    }

    @Test
    void getHardwaresListTest() throws RepositoryException {
        assertEquals(0, hardwareRepositoryAdapter.getHardwareList(false).size());
        assertEquals(0, hardwareRepositoryAdapter.getHardwareList(true).size());
        hardwareRepositoryAdapter.createHardware(validHardware);
        assertEquals(1, hardwareRepositoryAdapter.getHardwareList(false).size());
        assertEquals(0, hardwareRepositoryAdapter.getHardwareList(true).size());
        hardwareRepositoryAdapter.createHardware(validHardware2);
        assertEquals(1, hardwareRepositoryAdapter.getHardwareList(false).size());
        assertEquals(1, hardwareRepositoryAdapter.getHardwareList(true).size());
    }

    @Test
    void updateHardwarePositiveTest() throws RepositoryException {
        Hardware createdHardware = hardwareRepositoryAdapter.createHardware(validHardware);
        Hardware updatedHardware = hardwareRepositoryAdapter.updateHardware(createdHardware.getId(), validHardware2);
        assertEquals(createdHardware.getId(), updatedHardware.getId());
        assertEquals(validHardware2.getArchive(), updatedHardware.getArchive());
        assertEquals(validHardware2.getPrice(), updatedHardware.getPrice());
        assertEquals(validHardware2.getHardwareType().getType(), updatedHardware.getHardwareType().getType());
        assertEquals(validHardware2.getHardwareType().getCondition(), updatedHardware.getHardwareType().getCondition());
    }

    @Test
    void updateHardwareNegativeTest() {
        assertThrows(RepositoryException.class, () -> hardwareRepositoryAdapter.updateHardware(validHardware.getId(), validHardware2));
    }
}
