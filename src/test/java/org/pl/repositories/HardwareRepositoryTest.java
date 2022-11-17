package org.pl.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import org.junit.jupiter.api.*;
import org.pl.exceptions.RepositoryException;
import org.pl.model.*;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.pl.model.Condition.DUSTY;

public class HardwareRepositoryTest {
    private HardwareRepository repository;
    private Hardware hardware;
    private Hardware hardware1;
    private static EntityManagerFactory emf;
    private static EntityManager em;

    @BeforeAll
    static void beforeAll() {
        emf = Persistence.createEntityManagerFactory("POSTGRES_REPAIR_PU");
        em = emf.createEntityManager();
    }

    @BeforeEach
    void setUp() {
        hardware = Hardware.builder()
                .archive(false)
                .hardwareType(new Computer(DUSTY))
                .price(100)
                .build();
        hardware1 = Hardware.builder()
                .archive(true)
                .hardwareType(new Console(DUSTY))
                .price(200)
                .build();
        repository = new HardwareRepository(em);
    }

    @Test
    void saveHardwarePositiveTest() throws RepositoryException {
        assertEquals(repository.saveHardware(hardware), hardware);
        assertEquals(repository.getHardwareById(hardware.getId()), hardware);
    }

    @Test
    void saveHardwareNegativeTest() throws RepositoryException {
        assertNotNull(repository.saveHardware(hardware));
        assertThrows(RepositoryException.class, () -> repository.saveHardware(hardware));
    }

    @Test
    void checkHardwareIdCreatedByDatabaseTest() throws RepositoryException {
        assertNotNull(repository.saveHardware(hardware));
        assertNotNull(repository.saveHardware(hardware1));
        assertNotEquals(hardware.getId(), hardware1.getId());
        assertEquals(hardware.getId().getClass(), UUID.class);
        assertEquals(hardware1.getId().getClass(), UUID.class);
    }

    @Test
    void getHardwareByIdPositiveTest() throws RepositoryException {
        assertNotNull(repository.saveHardware(hardware));
        assertNotNull(repository.getHardwareById(hardware.getId()));
    }

    @Test
    void getHardwareByIdNegativeTest() {
        assertThrows(RepositoryException.class, () -> repository.getHardwareById(hardware.getId()));
    }

    @Test
    void deleteHardwarePositiveTest() throws RepositoryException {
        assertNotNull(repository.saveHardware(hardware));
        repository.deleteHardware(hardware.getId());
        assertTrue(repository.getHardwareById(hardware.getId()).isArchive());
    }

    @Test
    void deleteHardwareNegativeTest() throws RepositoryException {
        assertThrows(RepositoryException.class, () -> repository.deleteHardware(hardware.getId()));
        assertNotNull(repository.saveHardware(hardware1));
        assertThrows(RepositoryException.class, () -> repository.deleteHardware(hardware.getId()));
        assertTrue(repository.getHardwareById(hardware1.getId()).isArchive());
    }

    @Test
    void getPresentHardwareListTest() throws RepositoryException {
        assertEquals(0, repository.getHardwareList(false).size());
        assertNotNull(repository.saveHardware(hardware));
        assertEquals(1, repository.getHardwareList(false).size());
        assertNotNull(repository.saveHardware(hardware1));
        assertEquals(1, repository.getHardwareList(false).size());
    }
    @Test
    void getArchiveClientsTest() throws RepositoryException {
        assertEquals(0, repository.getHardwareList(true).size());
        assertNotNull(repository.saveHardware(hardware));
        assertEquals(0, repository.getHardwareList(true).size());
        assertNotNull(repository.saveHardware(hardware1));
        assertEquals(1, repository.getHardwareList(true).size());
    }

    @AfterEach
    void afterEach() {
        Query query = em.createQuery("DELETE FROM Hardware ");
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
