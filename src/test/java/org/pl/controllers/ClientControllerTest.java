package org.pl.controllers;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.exceptions.ClientException;
import org.pl.exceptions.HardwareException;
import org.pl.exceptions.RepositoryException;
import org.pl.model.*;
import org.pl.repositories.ClientRepository;
import org.pl.services.ClientService;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@ExtendWith(CdiTestJunitExtension.class)
class ClientControllerTest {
    private EntityManagerFactory emf;
    private EntityManager em;
    private Address address;
    private Client client1, client2;
    private ClientService clientService;
    final String BASE_URL = "http://localhost:8080/pobi-java-1.0-SNAPSHOT/api";
    private jakarta.ws.rs.client.Client client;

    @BeforeEach
    @PostConstruct
    void setup() throws HardwareException, RepositoryException {
        emf = Persistence.createEntityManagerFactory("POSTGRES_REPAIR_PU");
        em = emf.createEntityManager();
        clientService = new ClientService(new ClientRepository(em));
        address = Address.builder()
                .street("White")
                .number("123")
                .city("Lodz")
                .build();
        client1 = org.pl.model.Client.builder()
                .username("JohnDoe")
                .clientAccessType(ClientAccessType.USER)
                .archive(false)
                .clientType(new Basic())
                .phoneNumber("535-535-535")
                .balance(100.0)
                .firstName("John")
                .lastName("Doe")
                .address(address)
                .build();
        client2 = org.pl.model.Client.builder()
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
//        var clientBuilder = ClientBuilder.newBuilder();
//        client = clientBuilder.build();
    }

    @Test
    void getUserByIdPositiveTest() throws RepositoryException, ClientException {
        clientService.add(client1);
        clientService.add(client2);

//        WebTarget target = client.target(BASE_URL + "/client/id/" + client1.getId());
//                Student student = target.path("rest").path("api").request(MediaType.APPLICATION_JSON).get(Student.class);
//        assertNotNull(student);
//        assertEquals("Marcin",student.getFirstName());
//        Response response = target.request().get();
//        assertEquals(200, response.getStatus());
//        assertEquals(client1, response.getEntity());

    }
}