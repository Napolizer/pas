package org.pl.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import org.junit.jupiter.api.*;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Address;
import org.pl.model.Basic;
import org.pl.model.Client;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ClientRepositoryTest {
    private ClientRepository repository;
    private Client client;
    private Client client1;
    private Address address;
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
                .archive(false)
                .clientType(new Basic())
                .phoneNumber("535-535-535")
                .balance(100.0)
                .firstName("John")
                .lastName("Doe")
                .address(address)
                .build();
        client1 = Client.builder()
                .archive(true)
                .clientType(new Basic())
                .phoneNumber("535-535-535")
                .balance(100.0)
                .firstName("John")
                .lastName("Doe")
                .address(address)
                .build();
        repository = new ClientRepository(em);
    }

    @Test
    void saveClientPositiveTest() throws RepositoryException {
        em.getTransaction().begin();
        assertEquals(repository.saveClient(client), client);
        em.getTransaction().commit();
        assertEquals(repository.getClientById(client.getId()), client);
    }
    @Test
    void saveClientNegativeTest() throws RepositoryException {
        em.getTransaction().begin();
        assertNotNull(repository.saveClient(client));
        em.getTransaction().commit();
        em.getTransaction().begin();
        assertThrows(RepositoryException.class, () -> repository.saveClient(client));
        em.getTransaction().commit();
    }
    @Test
    void checkClientIdCreatedByDatabaseTest() throws RepositoryException {
        em.getTransaction().begin();
        assertNotNull(repository.saveClient(client));
        assertNotNull(repository.saveClient(client1));
        em.getTransaction().commit();
        assertNotEquals(client.getId(), client1.getId());
        assertEquals(client.getId().getClass(), UUID.class);
        assertEquals(client1.getId().getClass(), UUID.class);
    }

    @Test
    void getClientByIdPositiveTest() throws RepositoryException {
        em.getTransaction().begin();
        assertNotNull(repository.saveClient(client));
        em.getTransaction().commit();
        assertNotNull(repository.getClientById(client.getId()));
    }

    @Test
    void getClientByIdNegativeTest() {
        assertThrows(RepositoryException.class, () -> repository.getClientById(client.getId()));
    }

    @Test
    void deleteClientPositiveTest() throws RepositoryException {
        em.getTransaction().begin();
        assertNotNull(repository.saveClient(client));
        repository.deleteClient(client.getId());
        em.getTransaction().commit();
        assertTrue(repository.getClientById(client.getId()).isArchive());
    }

    @Test
    void deleteClientNegativeTest() throws RepositoryException {
        assertThrows(RepositoryException.class, () -> repository.deleteClient(client.getId()));
        em.getTransaction().begin();
        assertNotNull(repository.saveClient(client1));
        em.getTransaction().commit();
        assertThrows(RepositoryException.class, () -> repository.deleteClient(client.getId()));
        assertTrue(repository.getClientById(client1.getId()).isArchive());
    }

    @AfterAll
    static void afterAll() {
        if (emf != null)
            emf.close();
    }
}