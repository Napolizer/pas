package org.pl.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.exceptions.HardwareException;
import org.pl.exceptions.RepairException;
import org.pl.exceptions.RepositoryException;
import org.pl.model.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.pl.model.Condition.DUSTY;

class RepairReposioryTest {
    private RepairRepository repairRepository;
    private ClientRepository clientRepository;
    private Address address;
    private Client client;
    private Client client1;
    private Hardware hardware;
    private Repair repair;
    private Repair repair1;
    private Repair repair2;
    private static EntityManager em;
    private static EntityManagerFactory emf;

    @BeforeAll
    static void beforeAll() {
        emf = Persistence.createEntityManagerFactory("POSTGRES_REPAIR_PU");
        em = emf.createEntityManager();
    }

    @BeforeEach
    void setUp() {
        address = Address.builder()
                .street("White")
                .number("123")
                .city("Lodz")
                .build();

        client = Client.builder()
                .clientType(new Premium())
                .address(address)
                .balance(300.0)
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("123-123-123")
                .archive(false)
                .build();
        client1 = Client.builder()
                .clientType(new Premium())
                .address(address)
                .balance(300.0)
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("123-123-123")
                .archive(false)
                .build();
        hardware = Hardware.builder()
                .archive(false)
                .hardwareType(new Computer(DUSTY))
                .price(100)
                .build();
        repair = Repair.builder()
                .client(client)
                .hardware(hardware)
                .archive(false)
                .build();
        repair1 = Repair.builder()
                .client(client)
                .hardware(hardware)
                .archive(true)
                .build();
        repair2 = Repair.builder()
                .client(client1)
                .hardware(hardware)
                .archive(true)
                .build();
        repairRepository = new RepairRepository(em);
        clientRepository = new ClientRepository(em);
    }

    @Test
    void saveRepairPositiveTest() throws RepositoryException {
        assertEquals(repairRepository.saveRepair(repair), repair);
        assertEquals(repairRepository.getRepairById(repair.getId()), repair);
    }
    @Test
    void saveRepairNegativeTest() throws RepositoryException {
        assertNotNull(repairRepository.saveRepair(repair));
        assertThrows(RepositoryException.class, () -> repairRepository.saveRepair(repair));
    }
    @Test
    void checkRepairsIdCreatedByDatabaseTest() throws RepositoryException {
        assertNotNull(repairRepository.saveRepair(repair));
        assertNotNull(repairRepository.saveRepair(repair1));
        assertNotEquals(repair.getId(), repair1.getId());
        assertEquals(repair.getId().getClass(), UUID.class);
        assertEquals(repair1.getId().getClass(), UUID.class);
    }

    @Test
    void getRepairByIdPositiveTest() throws RepositoryException {
        assertNotNull(repairRepository.saveRepair(repair));
        assertNotNull(repairRepository.getRepairById(repair.getId()));
    }

    @Test
    void getClientByIdNegativeTest() {
        assertThrows(RepositoryException.class, () -> repairRepository.getRepairById(repair.getId()));
    }

    @Test
    void deleteRepairPositiveTest() throws RepositoryException {
        assertNotNull(repairRepository.saveRepair(repair));
        repairRepository.deleteRepair(repair.getId());
        assertTrue(repairRepository.getRepairById(repair.getId()).isArchive());
    }

    @Test
    void deleteRepairNegativeTest() throws RepositoryException {
        assertThrows(RepositoryException.class, () -> repairRepository.deleteRepair(repair.getId()));
        assertNotNull(repairRepository.saveRepair(repair1));
        assertThrows(RepositoryException.class, () -> repairRepository.deleteRepair(repair.getId()));
        assertTrue(repairRepository.getRepairById(repair1.getId()).isArchive());
    }

    @Test
    void getClientRepairsTest() throws RepositoryException {
        assertEquals(0, repairRepository.getClientRepairs(client.getId()).size());
        assertNotNull(repairRepository.saveRepair(repair));
        assertNotNull(repairRepository.saveRepair(repair1));
        assertNotNull(repairRepository.saveRepair(repair2));
        assertEquals(2, repairRepository.getClientRepairs(client.getId()).size());
    }

    @Test
    void updateRepairPositiveTest() throws RepositoryException {
        assertNotNull(repairRepository.saveRepair(repair));
        assertNotNull(repairRepository.updateRepair(repair.getId(), repair1));
        assertEquals(repairRepository.getRepairById(repair.getId()).getId(), repair.getId());
        assertEquals(repairRepository.getRepairById(repair.getId()).getClient(), repair1.getClient());
        assertEquals(repairRepository.getRepairById(repair.getId()).getHardware(), repair1.getHardware());
        assertEquals(repairRepository.getRepairById(repair.getId()).getArchive(), repair1.isArchive());
    }

    @Test
    void updateRepairNegativeTest() {
        assertThrows(RepositoryException.class, () -> repairRepository.updateRepair(repair.getId(), repair1));
    }

    @Test
    void repairPositiveTest() throws RepositoryException, HardwareException {
        assertNotNull(repairRepository.saveRepair(repair));
        repairRepository.repair(repair.getId());
        assertTrue(repair.isArchive());
        assertTrue(repair.getHardware().isArchive());
        assertEquals(295.0, repair.getClient().getBalance());
    }

    @AfterAll
    static void afterAll() {
        if (emf != null)
            emf.close();
    }
}
