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
        em.getTransaction().begin();
        assertEquals(repository.saveHardware(hardware), hardware);
        em.getTransaction().commit();
        assertEquals(repository.getHardwareById(hardware.getId()), hardware);
    }

    @Test
    void saveHardwareNegativeTest() throws RepositoryException {
        em.getTransaction().begin();
        assertNotNull(repository.saveHardware(hardware));
        em.getTransaction().commit();
        em.getTransaction().begin();
        assertThrows(RepositoryException.class, () -> repository.saveHardware(hardware));
        em.getTransaction().commit();
    }

    @Test
    void checkHardwareIdCreatedByDatabaseTest() throws RepositoryException {
        em.getTransaction().begin();
        assertNotNull(repository.saveHardware(hardware));
        assertNotNull(repository.saveHardware(hardware1));
        em.getTransaction().commit();
        assertNotEquals(hardware.getId(), hardware1.getId());
        assertEquals(hardware.getId().getClass(), UUID.class);
        assertEquals(hardware1.getId().getClass(), UUID.class);
    }

    @Test
    void getHardwareByIdPositiveTest() throws RepositoryException {
        em.getTransaction().begin();
        assertNotNull(repository.saveHardware(hardware));
        em.getTransaction().commit();
        assertNotNull(repository.getHardwareById(hardware.getId()));
    }

    @Test
    void getHardwareByIdNegativeTest() {
        assertThrows(RepositoryException.class, () -> repository.getHardwareById(hardware.getId()));
    }

    @Test
    void deleteHardwarePositiveTest() throws RepositoryException {
        em.getTransaction().begin();
        assertNotNull(repository.saveHardware(hardware));
        repository.deleteHardware(hardware.getId());
        em.getTransaction().commit();
        assertTrue(repository.getHardwareById(hardware.getId()).isArchive());
    }

    @Test
    void deleteHardwareNegativeTest() throws RepositoryException {
        assertThrows(RepositoryException.class, () -> repository.deleteHardware(hardware.getId()));
        em.getTransaction().begin();
        assertNotNull(repository.saveHardware(hardware1));
        em.getTransaction().commit();
        assertThrows(RepositoryException.class, () -> repository.deleteHardware(hardware.getId()));
        assertTrue(repository.getHardwareById(hardware1.getId()).isArchive());
    }

    @AfterAll
    static void afterAll() {
        if (emf != null)
            emf.close();
    }
}
