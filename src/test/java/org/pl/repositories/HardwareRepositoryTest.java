package org.pl.repositories;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.pl.exceptions.RepositoryException;
import org.pl.model.*;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.pl.model.Condition.AVERAGE;
import static org.pl.model.Condition.DUSTY;

public class HardwareRepositoryTest {
//    private HardwareRepository repository;
//    private Hardware hardware;
//    private Hardware hardware1;
//    private HardwareType computer, console;
//    private static EntityManagerFactory emf;
//    private static EntityManager em;
//
//    @BeforeAll
//    static void beforeAll() {
//        emf = Persistence.createEntityManagerFactory("POSTGRES_REPAIR_PU");
//        em = emf.createEntityManager();
//    }
//
//    @BeforeEach
//    void setUp() {
//        computer = Computer.builder().condition(DUSTY).build();
//        console = Console.builder().condition(DUSTY).build();
//        hardware = Hardware.builder()
//                .archive(false)
//                .hardwareType(computer)
//                .price(100)
//                .build();
//        hardware1 = Hardware.builder()
//                .archive(true)
//                .hardwareType(console)
//                .price(200)
//                .build();
//        repository = new HardwareRepository(em);
//    }
//
//    @Test
//    void saveHardwarePositiveTest() throws RepositoryException {
//        assertEquals(repository.saveHardware(hardware), hardware);
//        assertEquals(repository.getHardwareById(hardware.getId()), hardware);
//    }
//
//    @Test
//    void saveHardwareNegativeTest() throws RepositoryException {
//        assertNotNull(repository.saveHardware(hardware));
//        assertThrows(RepositoryException.class, () -> repository.saveHardware(hardware));
//    }
//
//    @Test
//    void checkHardwareIdCreatedByDatabaseTest() throws RepositoryException {
//        assertNotNull(repository.saveHardware(hardware));
//        assertNotNull(repository.saveHardware(hardware1));
//        assertNotEquals(hardware.getId(), hardware1.getId());
//        assertEquals(hardware.getId().getClass(), UUID.class);
//        assertEquals(hardware1.getId().getClass(), UUID.class);
//    }
//
//    @Test
//    void getHardwareByIdPositiveTest() throws RepositoryException {
//        assertNotNull(repository.saveHardware(hardware));
//        assertNotNull(repository.getHardwareById(hardware.getId()));
//    }
//
//    @Test
//    void getHardwareByIdNegativeTest() {
//        assertThrows(RepositoryException.class, () -> repository.getHardwareById(hardware.getId()));
//    }
//
////    @Test
////    void updateHardwarePositiveTest() throws RepositoryException {
////        assertNotNull(repository.saveHardware(hardware));
////        assertNotNull(repository.updateHardware(hardware.getId(), hardware1));
////        assertEquals(repository.getHardwareById(hardware.getId()).getId(), hardware1.getId());
////        assertEquals(repository.getHardwareById(hardware.getId()).getPrice(), hardware1.getPrice());
////        assertEquals(repository.getHardwareById(hardware.getId()).isArchive(), hardware1.isArchive());
////        assertEquals(repository.getHardwareById(hardware.getId()).getHardwareType().getCondition(), hardware1.getHardwareType().getCondition());
////    }
//
//    @Test
//    void deleteHardwarePositiveTest() throws RepositoryException {
//        assertNotNull(repository.saveHardware(hardware));
//        repository.deleteHardware(hardware.getId());
//        assertTrue(repository.getHardwareById(hardware.getId()).isArchive());
//    }
//
//    @Test
//    void deleteHardwareNegativeTest() throws RepositoryException {
//        assertThrows(RepositoryException.class, () -> repository.deleteHardware(hardware.getId()));
//        assertNotNull(repository.saveHardware(hardware1));
//        assertThrows(RepositoryException.class, () -> repository.deleteHardware(hardware.getId()));
//        assertTrue(repository.getHardwareById(hardware1.getId()).isArchive());
//    }
//
//    @Test
//    void getPresentHardwareListTest() throws RepositoryException {
//        assertEquals(0, repository.getHardwareList(false).size());
//        assertNotNull(repository.saveHardware(hardware));
//        assertEquals(1, repository.getHardwareList(false).size());
//        assertNotNull(repository.saveHardware(hardware1));
//        assertEquals(1, repository.getHardwareList(false).size());
//    }
//    @Test
//    void getArchiveClientsTest() throws RepositoryException {
//        assertEquals(0, repository.getHardwareList(true).size());
//        assertNotNull(repository.saveHardware(hardware));
//        assertEquals(0, repository.getHardwareList(true).size());
//        assertNotNull(repository.saveHardware(hardware1));
//        assertEquals(1, repository.getHardwareList(true).size());
//    }
//
//    @AfterEach
//    void afterEach() {
//        Query query1 = em.createQuery("DELETE FROM Hardware ");
//        Query query2 = em.createQuery("DELETE FROM HardwareType ");
//        em.getTransaction().begin();
//        query1.executeUpdate();
//        query2.executeUpdate();
//        em.getTransaction().commit();
//    }
//
//    @AfterAll
//    static void afterAll() {
//        if (emf != null)
//            emf.close();
//    }
}
