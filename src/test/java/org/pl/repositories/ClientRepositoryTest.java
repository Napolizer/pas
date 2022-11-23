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
//    private ClientRepository repository;
//    private Client client;
//    private Client client1;
//    private Address address;
//    private static EntityManager em;
//    private static EntityManagerFactory emf;
//
//
//    @BeforeAll
//    static void beforeAll() {
//        emf = Persistence.createEntityManagerFactory("POSTGRES_REPAIR_PU");
//        em = emf.createEntityManager();
//    }
//    @BeforeEach
//    void setUp() {
//        address = Address.builder()
//                .street("White")
//                .number("123")
//                .city("Lodz")
//                .build();
//        client = Client.builder()
//                .username("JohnDoe")
//                .clientAccessType(ClientAccessType.USER)
//                .archive(false)
//                .clientType(new Basic())
//                .phoneNumber("535-535-535")
//                .balance(100.0)
//                .firstName("John")
//                .lastName("Doe")
//                .address(address)
//                .build();
//        client1 = Client.builder()
//                .username("johny")
//                .clientAccessType(ClientAccessType.EMPLOYEE)
//                .archive(true)
//                .clientType(new Basic())
//                .phoneNumber("535-535-535")
//                .balance(100.0)
//                .firstName("John")
//                .lastName("Doe")
//                .address(address)
//                .build();
//        repository = new ClientRepository(em);
//    }
//
//    @Test
//    void saveClientPositiveTest() throws RepositoryException {
//        assertEquals(repository.saveClient(client), client);
//        assertEquals(repository.getClientById(client.getId()), client);
//    }
//    @Test
//    void saveClientNegativeTest() throws RepositoryException {
//        assertNotNull(repository.saveClient(client));
//        assertThrows(RepositoryException.class, () -> repository.saveClient(client));
//    }
//    @Test
//    void checkClientIdCreatedByDatabaseTest() throws RepositoryException {
//        assertNotNull(repository.saveClient(client));
//        assertNotNull(repository.saveClient(client1));
//        assertNotEquals(client.getId(), client1.getId());
//        assertEquals(client.getId().getClass(), UUID.class);
//        assertEquals(client1.getId().getClass(), UUID.class);
//    }
//
//    @Test
//    void getClientByIdPositiveTest() throws RepositoryException {
//        assertNotNull(repository.saveClient(client));
//        assertNotNull(repository.getClientById(client.getId()));
//    }
//
//    @Test
//    void getClientByIdNegativeTest() {
//        assertThrows(RepositoryException.class, () -> repository.getClientById(client.getId()));
//    }
//
//    @Test
//    void updateClientPositiveTest() throws RepositoryException {
//        assertNotNull(repository.saveClient(client));
//        assertNotNull(repository.updateClient(client.getId(), client1));
//        assertEquals(repository.getClientById(client.getId()).getId(), client.getId());
//        assertEquals(repository.getClientById(client.getId()).getClientType().getFactor(), client1.getClientType().getFactor());
//        assertEquals(repository.getClientById(client.getId()).getClientType().getMaxRepairs(), client1.getClientType().getMaxRepairs());
//        assertEquals(repository.getClientById(client.getId()).getClientType().getTypeName(), client1.getClientType().getTypeName());
//        assertEquals(repository.getClientById(client.getId()).getBalance(), client1.getBalance());
//        assertEquals(repository.getClientById(client.getId()).getArchive(), client1.isArchive());
//        assertEquals(repository.getClientById(client.getId()).getFirstName(), client1.getFirstName());
//        assertEquals(repository.getClientById(client.getId()).getLastName(), client1.getLastName());
//        assertEquals(repository.getClientById(client.getId()).getPhoneNumber(), client1.getPhoneNumber());
//        assertEquals(repository.getClientById(client.getId()).getAddress(), client1.getAddress());
//        assertEquals(repository.getClientById(client.getId()).getClientAccessType(), client1.getClientAccessType());
//    }
//
//    @Test
//    void deleteClientPositiveTest() throws RepositoryException {
//        assertNotNull(repository.saveClient(client));
//        repository.deleteClient(client.getId());
//        assertTrue(repository.getClientById(client.getId()).isArchive());
//    }
//
//    @Test
//    void deleteClientNegativeTest() throws RepositoryException {
//        assertThrows(RepositoryException.class, () -> repository.deleteClient(client.getId()));
//        assertNotNull(repository.saveClient(client1));
//        assertThrows(RepositoryException.class, () -> repository.deleteClient(client.getId()));
//        assertTrue(repository.getClientById(client1.getId()).isArchive());
//    }
//
//    @Test
//    void getPresentClientsTest() throws RepositoryException {
//        assertEquals(0, repository.getClients(false).size());
//        assertNotNull(repository.saveClient(client));
//        assertEquals(1, repository.getClients(false).size());
//        assertNotNull(repository.saveClient(client1));
//        assertEquals(1, repository.getClients(false).size());
//    }
//    @Test
//    void getArchiveClientsTest() throws RepositoryException {
//        assertEquals(0, repository.getClients(true).size());
//        assertNotNull(repository.saveClient(client));
//        assertEquals(0, repository.getClients(true).size());
//        assertNotNull(repository.saveClient(client1));
//        assertEquals(1, repository.getClients(true).size());
//    }
//
//    @Test
//    void getAllClientsTest() throws RepositoryException {
//        assertEquals(0, repository.getAllClients().size());
//        assertNotNull(repository.saveClient(client));
//        assertEquals(1, repository.getAllClients().size());
//        assertNotNull(repository.saveClient(client1));
//        assertEquals(2, repository.getAllClients().size());
//    }
//
//    @Test
//    void getClientByUsernamePositiveTest() throws RepositoryException {
//        assertNotNull(repository.saveClient(client));
//        assertEquals(repository.getClientByUsername(client.getUsername()), client);
//    }
//
//    @Test
//    void getClientByUsernameNegativeTest() {
//        assertThrows(RepositoryException.class, () -> repository.getClientByUsername(client.getUsername()));
//    }
//
//    @Test
//    void getClientsByUsernameTest() throws RepositoryException {
//        assertEquals(0, repository.getClientsByUsername("oh").size());
//        assertNotNull(repository.saveClient(client));
//        assertEquals(1, repository.getClientsByUsername("oh").size());
//        assertNotNull(repository.saveClient(client1));
//        assertEquals(2, repository.getClientsByUsername("oh").size());
//    }
//
//    @Test
//    void restoreClientPositiveTest() throws RepositoryException {
//        assertNotNull(repository.saveClient(client));
//        assertNotNull(repository.deleteClient(client.getId()));
//        assertNotNull(repository.restoreClient(client.getId()));
//        assertFalse(repository.getClientById(client.getId()).isArchive());
//    }
//
//    @Test
//    void restoreClientNegativeTest() {
//        assertThrows(RepositoryException.class, () -> repository.restoreClient(client.getId()));
//    }
//
//    @AfterEach
//    void afterEach() {
//        Query query = em.createQuery("DELETE FROM Client ");
//        em.getTransaction().begin();
//        query.executeUpdate();
//        em.getTransaction().commit();
//    }
//
//    @AfterAll
//    static void afterAll() {
//        if (emf != null)
//            emf.close();
//    }
}