package org.pl.services;

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
import org.pl.model.Computer;
import org.pl.model.Condition;
import org.pl.model.Hardware;
import org.pl.model.HardwareType;
import org.pl.model.exceptions.HardwareException;
import org.pl.model.exceptions.RepositoryException;

import javax.transaction.UserTransaction;
import java.io.File;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ArquillianExtension.class)
class HardwareServiceIT {
    @Inject
    private HardwareService hardwareService;
    @Inject
    private UserTransaction userTransaction;
    @Inject
    private EntityManager entityManager;
    private HardwareType validHardwareType;
    private Hardware validHardware;

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
        validHardwareType = new Computer(Condition.FINE);
        validHardware = Hardware.builder()
                .id(UUID.randomUUID())
                .archive(false)
                .price(200)
                .hardwareType(validHardwareType)
                .build();
        try {
            userTransaction.begin();
            entityManager.clear();
            userTransaction.commit();
        } catch (Exception ignored) {

        }
    }

    @Test
    void addHardwarePositiveTest() throws HardwareException, RepositoryException {
        Hardware createdHardware = hardwareService.add(validHardware);
        assertEquals(validHardware.getHardwareType().getType(), createdHardware.getHardwareType().getType());
        assertEquals(validHardware.getHardwareType().getCondition(), createdHardware.getHardwareType().getCondition());
        assertEquals(validHardware.getPrice(), createdHardware.getPrice());
        assertEquals(validHardware.getArchive(), createdHardware.getArchive());
    }

    @Test
    void addHardwareNegativePriceIsNegativeTest() throws HardwareException, RepositoryException {
        validHardware.setPrice(-20);
        assertThrows(HardwareException.class, () -> hardwareService.add(validHardware));
    }

    @Test
    void addHardwareNegativeHardwareTypeIsNullTest() throws HardwareException, RepositoryException {
        validHardware.setHardwareType(null);
        assertThrows(HardwareException.class, () -> hardwareService.add(validHardware));
    }

    @Test
    void isHardwareArchiveTest() throws HardwareException, RepositoryException {
        assertTrue(hardwareService.isHardwareArchive(validHardware.getId()));
        Hardware createdHardware = hardwareService.add(validHardware);
        assertFalse(hardwareService.isHardwareArchive(createdHardware.getId()));
        validHardware.setId(UUID.randomUUID());
        validHardware.setArchive(true);
        Hardware createdHardware2 = hardwareService.add(validHardware);
        assertTrue(hardwareService.isHardwareArchive(createdHardware2.getId()));
    }

    @Test
    void getHardwarePositiveTest() throws HardwareException, RepositoryException {
        Hardware createdHardware = hardwareService.add(validHardware);
        assertEquals(validHardware.getHardwareType().getType(), hardwareService.get(createdHardware.getId()).getHardwareType().getType());
        assertEquals(validHardware.getHardwareType().getCondition(), hardwareService.get(createdHardware.getId()).getHardwareType().getCondition());
        assertEquals(validHardware.getArchive(), hardwareService.get(createdHardware.getId()).getArchive());
        assertEquals(validHardware.getPrice(), hardwareService.get(createdHardware.getId()).getPrice());
    }

    @Test
    void getHardwareNegativeTest() throws RepositoryException {
        assertNull(hardwareService.get(validHardware.getId()));
    }

    @Test
    void getHardwareInfoTest() throws HardwareException, RepositoryException {
        Hardware createdHardware = hardwareService.add(validHardware);
        String hardwareInfo = createdHardware.toString();
        assertEquals(hardwareInfo, hardwareService.getInfo(createdHardware.getId()));
    }

    @Test
    void archiveHardwarePositiveTest() throws HardwareException, RepositoryException {
        Hardware createdHardware = hardwareService.add(validHardware);
        assertFalse(hardwareService.get(createdHardware.getId()).getArchive());
        assertNotNull(hardwareService.get(createdHardware.getId()));
        hardwareService.archive(createdHardware.getId());
        assertNull(hardwareService.get(createdHardware.getId()));
    }

    @Test
    void archiveHardwareNegativeTest() {
        assertThrows(RepositoryException.class, () -> hardwareService.archive(validHardware.getId()));
    }

    @Test
    void getPresentSizeTest() throws HardwareException, RepositoryException {
        assertEquals(0, hardwareService.getPresentSize());
        hardwareService.add(validHardware);
        assertEquals(1, hardwareService.getPresentSize());
    }

    @Test
    void getArchiveSizeTest() throws HardwareException, RepositoryException {
        assertEquals(0, hardwareService.getArchiveSize());
        validHardware.setArchive(true);
        hardwareService.add(validHardware);
        assertEquals(1, hardwareService.getArchiveSize());
    }

    @Test
    void getAllHardwaresTest() throws HardwareException, RepositoryException {
        Hardware validHardware2 = Hardware.builder()
                .id(UUID.randomUUID())
                .archive(false)
                .price(200)
                .hardwareType(validHardwareType)
                .build();
        hardwareService.add(validHardware);
        assertEquals(1, hardwareService.getAllHardwares().size());
        hardwareService.add(validHardware2);
        assertEquals(2, hardwareService.getAllHardwares().size());
    }

    @Test
    void updateHardwarePositiveTest() throws HardwareException, RepositoryException {
        Hardware createdHardware = hardwareService.add(validHardware);
        createdHardware.setPrice(250);
        hardwareService.updateHardware(createdHardware.getId(), createdHardware);
        assertEquals(createdHardware.getPrice(), hardwareService.get(createdHardware.getId()).getPrice());
        assertEquals(createdHardware.getArchive(), hardwareService.get(createdHardware.getId()).getArchive());
    }

    @Test
    void updateHardwareNegativeTest() {
        assertThrows(RepositoryException.class, () -> hardwareService.updateHardware(validHardware.getId(), validHardware));
    }

    @Test
    void getHardwareTypeByIdPositiveTest() throws HardwareException, RepositoryException {
        Hardware createdHardware = hardwareService.add(validHardware);
        assertEquals(createdHardware.getHardwareType(), hardwareService.getHardwareTypeById(createdHardware.getHardwareType().getId()));
    }

    @Test
    void getHardwareTypeByIdNegativeTest() {
        assertThrows(RepositoryException.class, () -> hardwareService.getHardwareTypeById(validHardware.getId()));
    }

    @Test
    void getAllPresentHardwareTest() throws HardwareException, RepositoryException {
        Hardware validHardware2 = Hardware.builder()
                .id(UUID.randomUUID())
                .archive(false)
                .price(200)
                .hardwareType(validHardwareType)
                .build();
        Hardware validHardware3 = Hardware.builder()
                .id(UUID.randomUUID())
                .archive(true)
                .price(200)
                .hardwareType(validHardwareType)
                .build();
        hardwareService.add(validHardware);
        assertEquals(1, hardwareService.getAllPresentHardware().size());
        hardwareService.add(validHardware2);
        assertEquals(2, hardwareService.getAllPresentHardware().size());
        hardwareService.add(validHardware3);
        assertEquals(2, hardwareService.getAllPresentHardware().size());
    }

    @Test
    void getAllPresentHardwareFilterTest() throws HardwareException, RepositoryException {
        assertEquals(0, hardwareService.getAllPresentHardwareFilter("32131432").size());
        Hardware createdHardware = hardwareService.add(validHardware);
        assertEquals(1, hardwareService.getAllPresentHardwareFilter("").size());
        assertEquals(1, hardwareService.getAllPresentHardwareFilter(createdHardware.getId().toString()).size());
    }
}