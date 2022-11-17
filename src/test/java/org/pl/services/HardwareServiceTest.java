package org.pl.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import org.junit.jupiter.api.*;
import org.pl.exceptions.HardwareException;
import org.pl.exceptions.RepositoryException;
import org.pl.model.*;
import org.pl.repositories.HardwareRepository;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class HardwareServiceTest {
    private HardwareType computer, console, monitor, phone;
    private Hardware hardware, hardware1, hardware2, hardware3;
    private HardwareRepository hardwareRepository;
    private HardwareService hardwareService;
    private static EntityManagerFactory emf;
    private static EntityManager em;

    @BeforeAll
    static void beforeAll() {
        emf = Persistence.createEntityManagerFactory("POSTGRES_REPAIR_PU");
        em = emf.createEntityManager();
    }
    @BeforeEach
    void setUp() {
        computer = Computer.builder().condition(Condition.FINE).build();
        console = Console.builder().condition(Condition.VERY_BAD).build();
        monitor = Monitor.builder().condition(Condition.DUSTY).build();
        phone = Phone.builder().condition(Condition.UNREPAIRABLE).build();
        hardware = Hardware.builder()
                .price(1000)
                .hardwareType(computer)
                .archive(false)
                .build();
        hardware1 = Hardware.builder()
                .price(100)
                .hardwareType(console)
                .archive(true)
                .build();
        hardware2 = Hardware.builder()
                .price(10)
                .hardwareType(phone)
                .archive(false)
                .build();
        hardware3 = Hardware.builder()
                .price(1)
                .hardwareType(monitor)
                .archive(false)
                .build();
        hardwareRepository = new HardwareRepository(em);
        hardwareService = new HardwareService(hardwareRepository);
    }

    @Test
    void hardwareServiceAddPositiveTest() throws RepositoryException, HardwareException {
        hardwareService.add(hardware);
        assertNotNull(hardwareService.get(hardware.getId()));
        hardwareService.add(hardware1);
        assertNotNull(hardwareService.get(hardware1.getId()));
        hardwareService.add(hardware2);
        assertNotNull(hardwareService.get(hardware2.getId()));
        hardwareService.add(hardware3);
        assertNotNull(hardwareService.get(hardware3.getId()));
    }

    @Test
    void hardwareServiceAddNegativeTest() {
        assertThrows(HardwareException.class,
                ()-> hardwareService.add(Hardware.builder().price(200).hardwareType(null).build()));
        assertThrows(HardwareException.class,
                ()-> hardwareService.add(Hardware.builder().price(-100).build()));
    }

    @Test
    void hardwareServiceGetInfoTest() throws HardwareException, RepositoryException {
        hardwareService.add(hardware);
        String idString = hardware.getId().toString();
        String expectedInfo = "Hardware(id=" + idString + ", archive=false, price=1000, hardwareType=Computer(condition=FINE))";
        assertEquals(expectedInfo, hardwareService.getInfo(hardware.getId()));
    }

    @Test
    void hardwareServiceRemovePositiveTest() throws HardwareException, RepositoryException {
        hardwareService.add(hardware);
        assertEquals(1, hardwareService.getPresentSize());
        hardwareService.add(hardware2);
        assertEquals(2, hardwareService.getPresentSize());
        hardwareService.archive(hardware.getId());
        assertEquals(1, hardwareService.getPresentSize());
        assertTrue(hardwareService.get(hardware.getId()).isArchive());
        hardwareService.archive(hardware2.getId());
        assertEquals(0, hardwareService.getPresentSize());
        assertTrue(hardwareService.get(hardware2.getId()).isArchive());
    }

    @Test
    void hardwareServiceRemoveNegativeTest() throws HardwareException, RepositoryException {
        hardwareService.add(hardware);
        hardwareService.archive(hardware.getId());
        assertThrows(RepositoryException.class,
                ()-> hardwareService.archive(hardware.getId()));
    }

    @Test
    void hardwareServiceGetSizeTest() throws RepositoryException, HardwareException {
        assertEquals(0, hardwareService.getPresentSize());
        assertEquals(0, hardwareService.getArchiveSize());
        hardwareService.add(hardware);
        assertEquals(1, hardwareService.getPresentSize());
        assertEquals(0, hardwareService.getArchiveSize());
        hardwareService.add(hardware2);
        assertEquals(2, hardwareService.getPresentSize());
        assertEquals(0, hardwareService.getArchiveSize());
        hardwareService.archive(hardware.getId());
        assertEquals(1, hardwareService.getPresentSize());
        assertEquals(1, hardwareService.getArchiveSize());
        hardwareService.archive(hardware2.getId());
        assertEquals(0, hardwareService.getPresentSize());
        assertEquals(2, hardwareService.getArchiveSize());
    }

    @AfterEach
    void afterEach() {
        Query query1 = em.createQuery("DELETE FROM Hardware ");
        Query query2 = em.createQuery("DELETE FROM HardwareType ");
        em.getTransaction().begin();
        query1.executeUpdate();
        query2.executeUpdate();
        em.getTransaction().commit();
    }

    @AfterAll
    static void afterAll() {
        if (emf != null)
            emf.close();
    }
}
