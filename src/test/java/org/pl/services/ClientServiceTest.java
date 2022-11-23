package org.pl.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.*;
import org.pl.exceptions.ClientException;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Address;
import org.pl.model.Basic;
import org.pl.model.Client;
import org.pl.model.ClientAccessType;
import org.pl.repositories.ClientRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ClientServiceTest {
//    private ClientRepository clientRepository;
//    private Address address;
//    private ClientService clientService;
//    private Client client;
//    private Client client1;
//    private Client client2;
//    private Client client3;
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
//        address = Address.builder()
//                .street("White")
//                .number("123")
//                .city("Lodz")
//                .build();
//        Address address2 = Address.builder()
//                .city("Katowice")
//                .street("Porajska")
//                .number("34")
//                .build();
//        client = Client.builder()
//                .username("JohnDoe")
//                .clientAccessType(ClientAccessType.USER)
//                .archive(false)
//                .clientType(new Basic())
//                .phoneNumber("535-535-535")
//                .balance(0.0)
//                .firstName("John")
//                .lastName("Doe")
//                .address(address)
//                .build();
//        client1 = Client.builder()
//                .username("doe")
//                .clientAccessType(ClientAccessType.EMPLOYEE)
//                .archive(true)
//                .clientType(new Basic())
//                .phoneNumber("535-535-535")
//                .balance(100.0)
//                .firstName("John")
//                .lastName("Doe")
//                .address(address)
//                .build();
//        client2 = Client.builder()
//                .username("johny")
//                .clientAccessType(ClientAccessType.ADMINISTRATOR)
//                .archive(false)
//                .clientType(new Basic())
//                .phoneNumber("535-535-535")
//                .balance(100.0)
//                .firstName("John")
//                .lastName("Doe")
//                .address(address2)
//                .build();
//        client3 = Client.builder()
//                .username("DoeJohn")
//                .clientAccessType(ClientAccessType.USER)
//                .archive(false)
//                .clientType(new Basic())
//                .phoneNumber("535-535-535")
//                .balance(100.0)
//                .firstName("John")
//                .lastName("Doe")
//                .address(address2)
//                .build();
//        clientRepository = new ClientRepository(em);
//        clientService = new ClientService(clientRepository);
//    }
//
//    @Test
//    void clientServiceAddPositiveTest() throws RepositoryException, ClientException {
//        clientService.add(client);
//        assertNotNull(clientService.get(client.getId()));
//        clientService.add(client1);
//        assertNotNull(clientService.get(client1.getId()));
//        clientService.add(client2);
//        assertNotNull(clientService.get(client2.getId()));
//        clientService.add(client3);
//        assertNotNull(clientService.get(client3.getId()));
//    }
//
//    @Test
//    void clientServiceAddNegativeTest() {
//        assertThrows(ClientException.class,
//                ()-> clientService.add(Client.builder().firstName("").build()));
//        assertThrows(ClientException.class,
//                ()-> clientService.add(Client.builder().lastName("").build()));
//        assertThrows(ClientException.class,
//                ()-> clientService.add(Client.builder().phoneNumber("").build()));
//        assertThrows(ClientException.class,
//                ()-> clientService.add(Client.builder().clientType(null).build()));
//    }
//
//    @Test
//    void clientServiceRemovePositiveTest() throws RepositoryException, ClientException {
//        clientService.add(client);
//        assertEquals(1, clientService.getPresentSize());
//        clientService.add(client2);
//        assertEquals(2, clientService.getPresentSize());
//        clientService.archive(client.getId());
//        assertEquals(1, clientService.getPresentSize());
//        assertTrue(clientService.get(client.getId()).isArchive());
//        assertFalse(clientService.get(client2.getId()).isArchive());
//        clientService.archive(client2.getId());
//        assertEquals(0, clientService.getPresentSize());
//    }
//
//    @Test
//    void clientServiceRemoveNegativeTest() throws RepositoryException, ClientException {
//        clientService.add(client);
//        clientService.add(client2);
//        clientService.archive(client.getId());
//        assertThrows(RepositoryException.class,
//                ()-> clientService.archive(client.getId()));
//    }
//
//    @Test
//    void clientServiceGetSizeTest() throws RepositoryException, ClientException {
//        assertEquals(0, clientService.getPresentSize());
//        assertEquals(0, clientService.getArchiveSize());
//        clientService.add(client);
//        assertEquals(1, clientService.getPresentSize());
//        assertEquals(0, clientService.getArchiveSize());
//        clientService.add(client2);
//        assertEquals(2, clientService.getPresentSize());
//        assertEquals(0, clientService.getArchiveSize());
//        clientService.archive(client.getId());
//        assertEquals(1, clientService.getPresentSize());
//        assertEquals(1, clientService.getArchiveSize());
//        clientService.archive(client2.getId());
//        assertEquals(0, clientService.getPresentSize());
//        assertEquals(2, clientService.getArchiveSize());
//    }
//
//    @Test
//    void clientServiceGetClientBalanceTest() throws RepositoryException, ClientException {
//        clientService.add(client);
//        assertEquals(0.0, clientService.getClientBalance(client.getId()));
//        clientService.get(client.getId()).changeBalance(100);
//        assertEquals(100.0, clientService.getClientBalance(client.getId()));
//    }
//
//    @Test
//    void getClientByUsernamePositiveTest() throws RepositoryException, ClientException {
//        clientService.add(client);
//        assertEquals(client, clientService.getClientByUsername(client.getUsername()));
//    }
//
//    @Test
//    void getClientByUsernameNegativeTest() {
//        assertThrows(RepositoryException.class, () -> clientService.getClientByUsername(client.getUsername()));
//    }
//
//    @Test
//    void getClientsByUsernameTest() throws RepositoryException, ClientException {
//        assertEquals(0, clientService.getClientsByUsername("oh").size());
//        clientService.add(client);
//        assertEquals(1, clientService.getClientsByUsername("oh").size());
//        clientService.add(client1);
//        assertEquals(1, clientService.getClientsByUsername("oh").size());
//        clientService.add(client2);
//        assertEquals(2, clientService.getClientsByUsername("oh").size());
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
