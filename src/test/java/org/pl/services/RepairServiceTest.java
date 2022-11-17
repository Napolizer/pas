package org.pl.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import org.junit.jupiter.api.*;
import org.pl.exceptions.ClientException;
import org.pl.exceptions.HardwareException;
import org.pl.exceptions.RepairException;
import org.pl.exceptions.RepositoryException;
import org.pl.model.*;
import org.pl.repositories.RepairRepository;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class RepairServiceTest {
    private Address address1, address2;
    private Client client1, client2;
    private Hardware hardware1, hardware2;
    private HardwareType computer, monitor;
    private RepairRepository repairRepository;
    private RepairService repairService;
    private static EntityManagerFactory emf;
    private static EntityManager em;

    @BeforeAll
    static void beforeAll() {
        emf = Persistence.createEntityManagerFactory("POSTGRES_REPAIR_PU");
        em = emf.createEntityManager();
    }

    @BeforeEach
    void setUp() {
        address1 = Address.builder()
                .city("Warszawa")
                .street("Uliczna")
                .number("34")
                .build();
        address2 = Address.builder()
                .city("Warszawa")
                .street("Kolorowa")
                .number("23")
                .build();
        client1 = Client.builder()
                .firstName("Szymon")
                .lastName("Kowalski")
                .phoneNumber("123456789")
                .address(address1)
                .balance(100.0)
                .archive(false)
                .clientType(new Premium())
                .build();
        client2 = Client.builder()
                .firstName("Kacper")
                .lastName("Jackowski")
                .phoneNumber("987654321")
                .address(address2)
                .archive(false)
                .balance(200.0)
                .clientType(new Vip())
                .build();
        computer = Computer.builder().condition(Condition.DUSTY).build();
        monitor = Monitor.builder().condition(Condition.AVERAGE).build();
        hardware1 = Hardware.builder()
                .price(2000)
                .hardwareType(computer)
                .archive(false)
                .build();
        hardware2 = Hardware.builder()
                .price(3000)
                .hardwareType(monitor)
                .archive(false)
                .build();
        repairRepository = new RepairRepository(em);
        repairService = new RepairService(repairRepository);
    }

    @Test
    void repairServiceAddPositiveTest() throws RepositoryException, RepairException {
        Repair createdRepair = repairService.add(client1, hardware1);
        assertNotNull(repairService.get(createdRepair.getId()));
        Repair createdRepair1 = repairService.add(client2, hardware2);
        assertNotNull(repairService.get(createdRepair1.getId()));
    }

    @Test
    void repairServiceAddNegativeTest() {
        assertThrows(RepairException.class,
                ()-> repairService.add(null, hardware1));
        assertThrows(RepairException.class,
                ()-> repairService.add(client1, null));
    }

    @Test
    void repairServiceGetInfoTest() throws RepositoryException, RepairException {
        Repair createdRepair = repairService.add(client1, hardware1);
        String repairIdString = createdRepair.getId().toString();
        String clientIdString = createdRepair.getClient().getId().toString();
        String hardwareIdString = createdRepair.getHardware().getId().toString();
        String expectedInfo = "Repair(id=" + repairIdString + ", archive=false, client=Client(id=" + clientIdString + ", username=null, archive=false, balance=null, firstName=Szymon, lastName=Kowalski, phoneNumber=123456789, clientType=Premium(), address=Address(city=Warszawa, number=34, street=Uliczna), clientAccessType=null), hardware=Hardware(id=" + hardwareIdString + ", archive=false, price=2000, hardwareType=Computer(condition=DUSTY)), startDate=null, endDate=null)";
        assertEquals(expectedInfo, repairService.getInfo(createdRepair.getId()));
    }

    @Test
    void repairServiceRemovePositiveTest() throws RepositoryException, RepairException, HardwareException, ClientException {
        Repair createdRepair = repairService.add(client1, hardware1);
        assertEquals(1, repairService.getPresentSize());
        Repair createdRepair1 = repairService.add(client2, hardware2);
        assertEquals(2, repairService.getPresentSize());
        repairService.repair(createdRepair1.getId());
        assertEquals(1, repairService.getPresentSize());
        assertTrue(repairService.get(createdRepair1.getId()).isArchive());
        repairService.repair(createdRepair.getId());
        assertTrue(repairService.get(createdRepair.getId()).isArchive());
    }

    @Test
    void repairServiceRemoveNegativeTest() throws HardwareException, RepositoryException, ClientException, RepairException {
        Repair createdRepair = repairService.add(client1, hardware1);
        repairService.repair(createdRepair.getId());
        assertThrows(RepositoryException.class,
                ()-> repairService.repair(createdRepair.getId()));
    }

    @Test
    void repairServiceGetSizeTest() throws RepositoryException, RepairException, HardwareException, ClientException {
        assertEquals(0, repairService.getPresentSize());
        assertEquals(0, repairService.getArchiveSize());
        Repair createdRepair = repairService.add(client1, hardware1);
        assertEquals(1, repairService.getPresentSize());
        assertEquals(0, repairService.getArchiveSize());
        Repair createdRepair1 = repairService.add(client2, hardware2);
        assertEquals(2, repairService.getPresentSize());
        assertEquals(0, repairService.getArchiveSize());
        repairService.repair(createdRepair.getId());
        assertEquals(1, repairService.getPresentSize());
        assertEquals(1, repairService.getArchiveSize());
        repairService.repair(createdRepair1.getId());
        assertEquals(0, repairService.getPresentSize());
        assertEquals(2, repairService.getArchiveSize());
    }

    @Test
    void repairServiceRepairTest() throws RepositoryException, RepairException, HardwareException, ClientException {
        Repair createdRepair = repairService.add(client1, hardware1);
        assertFalse(repairService.get(createdRepair.getId()).isArchive());
        repairService.repair(createdRepair.getId());
        assertTrue(repairService.get(createdRepair.getId()).isArchive());
    }

    @Test
    void repairServiceRepairArchiveTest() throws RepositoryException, RepairException, HardwareException, ClientException {
        Repair createdRepair = repairService.add(client1, hardware1);
        Repair createdRepair1 = repairService.add(client1, hardware2);
        assertFalse(client1.isArchive());
        assertFalse(hardware1.isArchive());
        assertFalse(hardware2.isArchive());
        repairService.repair(createdRepair.getId());
        assertFalse(client1.isArchive());
        assertTrue(hardware1.isArchive());
        assertFalse(hardware2.isArchive());
        repairService.repair(createdRepair1.getId());
        assertTrue(client1.isArchive());
        assertTrue(hardware1.isArchive());
        assertTrue(hardware2.isArchive());
    }

    @Test
    void repairServiceChangeBalanceTest() throws RepositoryException, RepairException, HardwareException, ClientException {
        Repair createdRepair = repairService.add(client1, hardware1);
        Repair createdRepair1 = repairService.add(client1, hardware2);
        assertEquals(0, client1.getBalance());
        repairService.repair(createdRepair1.getId());
        assertEquals(-2160, client1.getBalance());
        repairService.repair(createdRepair.getId());
        assertEquals(-2164.5, client1.getBalance());
    }

    @AfterEach
    void afterEach() {
        Query query = em.createQuery("DELETE FROM Repair ");
        em.getTransaction().begin();
        query.executeUpdate();
        em.getTransaction().commit();
    }

    @AfterAll
    static void afterAll() {
        if (emf != null)
            emf.close();
    }
}
