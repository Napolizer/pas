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
import org.pl.model.ClientAccessType;

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
                .username("johny")
                .clientAccessType(ClientAccessType.EMPLOYEE)
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
        assertEquals(repository.saveClient(client), client);
        assertEquals(repository.getClientById(client.getId()), client);
    }
    @Test
    void saveClientNegativeTest() throws RepositoryException {
        assertNotNull(repository.saveClient(client));
        assertThrows(RepositoryException.class, () -> repository.saveClient(client));
    }
    @Test
    void checkClientIdCreatedByDatabaseTest() throws RepositoryException {
        assertNotNull(repository.saveClient(client));
        assertNotNull(repository.saveClient(client1));
        assertNotEquals(client.getId(), client1.getId());
        assertEquals(client.getId().getClass(), UUID.class);
        assertEquals(client1.getId().getClass(), UUID.class);
    }

    @Test
    void getClientByIdPositiveTest() throws RepositoryException {
        assertNotNull(repository.saveClient(client));
        assertNotNull(repository.getClientById(client.getId()));
    }

    @Test
    void getClientByIdNegativeTest() {
        assertThrows(RepositoryException.class, () -> repository.getClientById(client.getId()));
    }

    @Test
    void deleteClientPositiveTest() throws RepositoryException {
        assertNotNull(repository.saveClient(client));
        repository.deleteClient(client.getId());
        assertTrue(repository.getClientById(client.getId()).isArchive());
    }

    @Test
    void deleteClientNegativeTest() throws RepositoryException {
        assertThrows(RepositoryException.class, () -> repository.deleteClient(client.getId()));
        assertNotNull(repository.saveClient(client1));
        assertThrows(RepositoryException.class, () -> repository.deleteClient(client.getId()));
        assertTrue(repository.getClientById(client1.getId()).isArchive());
    }

    @Test
    void getPresentClientsTest() throws RepositoryException {
        assertEquals(0, repository.getClients(false).size());
        assertNotNull(repository.saveClient(client));
        assertEquals(1, repository.getClients(false).size());
        assertNotNull(repository.saveClient(client1));
        assertEquals(1, repository.getClients(false).size());
    }
    @Test
    void getArchiveClientsTest() throws RepositoryException {
        assertEquals(0, repository.getClients(true).size());
        assertNotNull(repository.saveClient(client));
        assertEquals(0, repository.getClients(true).size());
        assertNotNull(repository.saveClient(client1));
        assertEquals(1, repository.getClients(true).size());
    }

    @AfterEach
    void afterEach() {
        Query query = em.createQuery("DELETE FROM Client ");
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