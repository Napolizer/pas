package org.pl.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import org.junit.jupiter.api.*;
import org.pl.exceptions.ClientException;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Address;
import org.pl.model.Basic;
import org.pl.model.Client;
import org.pl.repositories.ClientRepository;

import static org.junit.jupiter.api.Assertions.*;

public class ClientServiceTest {
    private ClientRepository clientRepository;
    private Address address;
    private ClientService clientService;
    private Client client;
    private Client client1;
    private Client client2;
    private Client client3;
    private static EntityManagerFactory emf;
    private static EntityManager em;

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
        Address address2 = Address.builder()
                .city("Katowice")
                .street("Porajska")
                .number("34")
                .build();
        client = Client.builder()
                .archive(false)
                .clientType(new Basic())
                .phoneNumber("535-535-535")
                .balance(0.0)
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
        client2 = Client.builder()
                .archive(false)
                .clientType(new Basic())
                .phoneNumber("535-535-535")
                .balance(100.0)
                .firstName("John")
                .lastName("Doe")
                .address(address2)
                .build();
        client3 = Client.builder()
                .archive(false)
                .clientType(new Basic())
                .phoneNumber("535-535-535")
                .balance(100.0)
                .firstName("John")
                .lastName("Doe")
                .address(address2)
                .build();
        clientRepository = new ClientRepository(em);
        clientService = new ClientService(clientRepository);
    }

    @Test
    void clientServiceAddPositiveTest() throws RepositoryException, ClientException {
        clientService.add(client);
        assertNotNull(clientService.get(client.getId()));
        clientService.add(client1);
        assertNotNull(clientService.get(client1.getId()));
        clientService.add(client2);
        assertNotNull(clientService.get(client2.getId()));
        clientService.add(client3);
        assertNotNull(clientService.get(client3.getId()));
    }

    @Test
    void clientServiceAddNegativeTest() {
        assertThrows(ClientException.class,
                ()-> clientService.add(Client.builder().firstName("").build()));
        assertThrows(ClientException.class,
                ()-> clientService.add(Client.builder().lastName("").build()));
        assertThrows(ClientException.class,
                ()-> clientService.add(Client.builder().phoneNumber("").build()));
        assertThrows(ClientException.class,
                ()-> clientService.add(Client.builder().clientType(null).build()));
    }

    @Test
    void clientServiceToStringTest() throws RepositoryException, ClientException {
        clientService.add(client);
        String idString = client.getId().toString();
        String expectedInfo = "Client(id=" + idString + ", username=null, archive=false, balance=0.0, firstName=John, lastName=Doe, phoneNumber=535-535-535, clientType=Basic(), address=Address(city=Lodz, number=123, street=White), clientAccessType=null)";
        assertEquals(expectedInfo, clientService.getInfo(client.getId()));
    }

    @Test
    void clientServiceRemovePositiveTest() throws RepositoryException, ClientException {
        clientService.add(client);
        assertEquals(1, clientService.getPresentSize());
        clientService.add(client2);
        assertEquals(2, clientService.getPresentSize());
        clientService.archive(client.getId());
        assertEquals(1, clientService.getPresentSize());
        assertTrue(clientService.get(client.getId()).isArchive());
        assertFalse(clientService.get(client2.getId()).isArchive());
        clientService.archive(client2.getId());
        assertEquals(0, clientService.getPresentSize());
    }

    @Test
    void clientServiceRemoveNegativeTest() throws RepositoryException, ClientException {
        clientService.add(client);
        clientService.add(client2);
        clientService.archive(client.getId());
        assertThrows(RepositoryException.class,
                ()-> clientService.archive(client.getId()));
    }

    @Test
    void clientServiceGetSizeTest() throws RepositoryException, ClientException {
        assertEquals(0, clientService.getPresentSize());
        assertEquals(0, clientService.getArchiveSize());
        clientService.add(client);
        assertEquals(1, clientService.getPresentSize());
        assertEquals(0, clientService.getArchiveSize());
        clientService.add(client2);
        assertEquals(2, clientService.getPresentSize());
        assertEquals(0, clientService.getArchiveSize());
        clientService.archive(client.getId());
        assertEquals(1, clientService.getPresentSize());
        assertEquals(1, clientService.getArchiveSize());
        clientService.archive(client2.getId());
        assertEquals(0, clientService.getPresentSize());
        assertEquals(2, clientService.getArchiveSize());
    }

    @Test
    void clientServiceGetClientBalanceTest() throws RepositoryException, ClientException {
        clientService.add(client);
        assertEquals(0.0, clientService.getClientBalance(client.getId()));
        clientService.get(client.getId()).changeBalance(100);
        assertEquals(100.0, clientService.getClientBalance(client.getId()));
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
