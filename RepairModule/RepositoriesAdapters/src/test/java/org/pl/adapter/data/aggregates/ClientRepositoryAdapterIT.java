package org.pl.adapter.data.aggregates;


import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.pl.model.Address;
import org.pl.model.Basic;
import org.pl.model.Client;
import org.pl.model.ClientAccessType;
import org.pl.model.exceptions.RepositoryException;

import javax.transaction.UserTransaction;
import java.io.File;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ArquillianExtension.class)
public class ClientRepositoryAdapterIT {
    @Inject
    private ClientRepositoryAdapter clientRepositoryAdapter;
    @Inject
    private UserTransaction userTransaction;
    @Inject
    private EntityManager entityManager;
    private Client validClient;
    private Client validClient2;
    private Address validAddress;


    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "org.pl")
                .addPackages(true, "org.hamcrest")
                .addAsResource(new File("src/main/resources/"),"")
                .addAsResource(new File("target/classes/META-INF/"), "META-INF/")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @BeforeEach
    void setup() {
        validAddress = Address.builder()
                .city("Lodz")
                .street("Przybyszewskiego")
                .number("21")
                .build();
        validClient = Client.builder()
                .id(UUID.randomUUID())
                .username("Username")
                .password("Password")
                .archive(false)
                .balance(100.0)
                .firstName("Tester")
                .lastName("Testowy")
                .phoneNumber("123456789")
                .clientType(new Basic())
                .address(validAddress)
                .clientAccessType(ClientAccessType.EMPLOYEES)
                .build();
        validClient2 = Client.builder()
                .id(UUID.randomUUID())
                .username("Username2")
                .password("Password")
                .archive(true)
                .balance(100.0)
                .firstName("Tester")
                .lastName("Testowy")
                .phoneNumber("123456789")
                .clientType(new Basic())
                .address(validAddress)
                .clientAccessType(ClientAccessType.EMPLOYEES)
                .build();
        try {
            userTransaction.begin();
            entityManager.clear();
            userTransaction.commit();
        } catch (Exception ignored) {

        }
    }

    @Test
    void createClientPositiveTest() throws RepositoryException {
        Client createdClient = clientRepositoryAdapter.createClient(validClient);
        assertNotNull(createdClient.getId());
        assertEquals(validClient.getUsername(), createdClient.getUsername());
        assertEquals(validClient.getPassword(), createdClient.getPassword());
        assertEquals(validClient.getArchive(), createdClient.getArchive());
        assertEquals(validClient.getBalance(), createdClient.getBalance());
        assertEquals(validClient.getFirstName(), createdClient.getFirstName());
        assertEquals(validClient.getLastName(), createdClient.getLastName());
        assertEquals(validClient.getPhoneNumber(), createdClient.getPhoneNumber());
        assertEquals(validClient.getClientType().getType(), createdClient.getClientType().getType());
        assertEquals(validClient.getAddress(), createdClient.getAddress());
        assertEquals(validClient.getClientAccessType(), createdClient.getClientAccessType());
    }

    @Test
    void createClientNegativeTest() {
        assertDoesNotThrow(() -> clientRepositoryAdapter.createClient(validClient));
        assertThrows(RepositoryException.class, () -> clientRepositoryAdapter.createClient(validClient));
    }

    @Test
    void getClientByUuidPositiveTest() throws RepositoryException {
        Client createdClient = clientRepositoryAdapter.createClient(validClient);
        assertEquals(validClient.getUsername(), clientRepositoryAdapter.getClient(createdClient.getId()).getUsername());
    }

    @Test
    void getClientByUuidNegativeTest() {
        assertThrows(RepositoryException.class, () -> clientRepositoryAdapter.getClient(validClient.getId()));
    }

    @Test
    void changePasswordPositiveTest() throws RepositoryException {
        Client createdClient = clientRepositoryAdapter.createClient(validClient);
        assertEquals("Password", clientRepositoryAdapter.getClient(createdClient.getId()).getPassword());
        assertDoesNotThrow(() -> clientRepositoryAdapter.changePassword(createdClient.getId(), "newPassword"));
        assertEquals("newPassword", clientRepositoryAdapter.getClient(createdClient.getId()).getPassword());
    }

    @Test
    void changePasswordNegativeTest() {
        assertThrows(RepositoryException.class, () -> clientRepositoryAdapter.changePassword(validClient.getId(), "newPassword"));
    }

    @Test
    void deleteClientPositiveTest() throws RepositoryException {
        Client createdClient = clientRepositoryAdapter.createClient(validClient);
        assertNotNull(clientRepositoryAdapter.getClient(createdClient.getId()));
        Client createdClient2 = clientRepositoryAdapter.deleteClient(createdClient.getId());
        assertTrue(clientRepositoryAdapter.getClient(createdClient2.getId()).getArchive());
    }

    @Test
    void deleteClientNegativeTest() {
        assertThrows(RepositoryException.class, () -> clientRepositoryAdapter.deleteClient(validClient.getId()));
    }

    @Test
    void getAllClientsFilterTest() throws RepositoryException {
        assertEquals(0, clientRepositoryAdapter.getAllClientsFilter(validClient.getId().toString()).size());
        validClient.setUsername("Username7");
        Client createdClient = clientRepositoryAdapter.createClient(validClient);
        clientRepositoryAdapter.createClient(validClient2);
        assertEquals(1, clientRepositoryAdapter.getAllClientsFilter(createdClient.getId().toString()).size());
    }

    @Test
    void getAllClientsTest() throws RepositoryException {
        assertEquals(0, clientRepositoryAdapter.getAllClients().size());
        clientRepositoryAdapter.createClient(validClient);
        assertEquals(1, clientRepositoryAdapter.getAllClients().size());
        clientRepositoryAdapter.createClient(validClient2);
        assertEquals(2, clientRepositoryAdapter.getAllClients().size());
    }

    @Test
    void getClientByUsernamePositiveTest() throws RepositoryException {
        Client createdClient = clientRepositoryAdapter.createClient(validClient);
        assertEquals(createdClient.getId(), clientRepositoryAdapter.getClientByUsername(validClient.getUsername()).getId());
    }

    @Test
    void getClientByUsernameNegativeTest() {
        assertThrows(RepositoryException.class, () -> clientRepositoryAdapter.getClientByUsername(validClient.getUsername()));
    }

    @Test
    void getClientListTest() throws RepositoryException {
        assertEquals(0, clientRepositoryAdapter.getClientList(false).size());
        assertEquals(0, clientRepositoryAdapter.getClientList(true).size());
        clientRepositoryAdapter.createClient(validClient);
        assertEquals(1, clientRepositoryAdapter.getClientList(false).size());
        assertEquals(0, clientRepositoryAdapter.getClientList(true).size());
        clientRepositoryAdapter.createClient(validClient2);
        assertEquals(1, clientRepositoryAdapter.getClientList(false).size());
        assertEquals(1, clientRepositoryAdapter.getClientList(true).size());
    }

    @Test
    void getClientsByUsernameTest() throws RepositoryException {
        assertEquals(0, clientRepositoryAdapter.getClientsByUsername("Username14").size());
        clientRepositoryAdapter.createClient(validClient);
        assertEquals(1, clientRepositoryAdapter.getClientsByUsername("Username14").size());
    }

    @Test
    void restoreClientPositiveTest() throws RepositoryException {
        Client createdClient = clientRepositoryAdapter.createClient(validClient);
        clientRepositoryAdapter.deleteClient(createdClient.getId());
        assertTrue(clientRepositoryAdapter.getClient(createdClient.getId()).getArchive());
        clientRepositoryAdapter.restoreClient(createdClient.getId());
        assertFalse(clientRepositoryAdapter.getClient(createdClient.getId()).getArchive());
    }

    @Test
    void restoreClientNegativeTest() throws RepositoryException {
        assertThrows(RepositoryException.class, () -> clientRepositoryAdapter.restoreClient(validClient.getId()));
        Client createdClient = clientRepositoryAdapter.createClient(validClient);
        assertThrows(RepositoryException.class, () -> clientRepositoryAdapter.restoreClient(createdClient.getId()));
    }

    @Test
    void updateClientPositiveTest() throws RepositoryException {
        Client createdClient = clientRepositoryAdapter.createClient(validClient);
        Client updatedClient = clientRepositoryAdapter.updateClient(createdClient.getId(), validClient2);
        assertEquals(createdClient.getId(), createdClient.getId());
        assertEquals(createdClient.getPassword(), updatedClient.getPassword());
        assertEquals(validClient2.getUsername(), updatedClient.getUsername());
        assertEquals(validClient2.getArchive(), updatedClient.getArchive());
        assertEquals(validClient2.getBalance(), updatedClient.getBalance());
        assertEquals(validClient2.getFirstName(), updatedClient.getFirstName());
        assertEquals(validClient2.getLastName(), updatedClient.getLastName());
        assertEquals(validClient2.getPhoneNumber(), updatedClient.getPhoneNumber());
        assertEquals(validClient2.getClientType().getType(), updatedClient.getClientType().getType());
        assertEquals(validClient2.getAddress(), updatedClient.getAddress());
        assertEquals(validClient2.getClientAccessType(), updatedClient.getClientAccessType());
    }
}
