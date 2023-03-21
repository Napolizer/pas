package org.pl.services;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.pl.adapter.data.model.ClientEnt;
import org.pl.model.Address;
import org.pl.model.Basic;
import org.pl.model.Client;
import org.pl.model.ClientAccessType;
import org.pl.model.exceptions.ClientException;
import org.pl.model.exceptions.RepositoryException;

import javax.transaction.UserTransaction;
import java.io.File;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ArquillianExtension.class)
class ClientServiceIT {
    @Inject
    private ClientService clientService;
    @Inject
    private UserTransaction userTransaction;
    @Inject
    private EntityManager entityManager;
    private Address validAddress;
    private Client validClient;

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
    void setUp() {
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
        try {
            userTransaction.begin();
            entityManager.clear();
            userTransaction.commit();
        } catch (Exception ignored) {

        }
    }

    @Test
    void addClientPositiveTest() throws RepositoryException, ClientException {
        Client createdClient = clientService.add(validClient);
        assertEquals(validClient.getFirstName(), createdClient.getFirstName());
        assertEquals(validClient.getLastName(), createdClient.getLastName());
        assertEquals(validClient.getUsername(), createdClient.getUsername());
        assertEquals(validClient.getArchive(), createdClient.getArchive());
        assertEquals(validClient.getBalance(), createdClient.getBalance());
        assertEquals(validClient.getPassword(), createdClient.getPassword());
        assertEquals(validClient.getPhoneNumber(), createdClient.getPhoneNumber());
        assertEquals(validClient.getClientType().getType(), createdClient.getClientType().getType());
        assertEquals(validClient.getClientAccessType(), createdClient.getClientAccessType());
        assertEquals(validClient.getAddress().getCity(), createdClient.getAddress().getCity());
        assertEquals(validClient.getAddress().getNumber(), createdClient.getAddress().getNumber());
        assertEquals(validClient.getAddress().getStreet(), createdClient.getAddress().getStreet());
    }

    @Test
    void addClientNegativeTestFirstNameIsEmpty() {
        validClient.setFirstName("");
        assertThrows(ClientException.class, () -> clientService.add(validClient));
    }

    @Test
    void addClientNegativeTestLastNameIsEmpty() {
        validClient.setLastName("");
        assertThrows(ClientException.class, () -> clientService.add(validClient));
    }

    @Test
    void addClientNegativeTestPhoneNumberIsEmpty() {
        validClient.setPhoneNumber("");
        assertThrows(ClientException.class, () -> clientService.add(validClient));
    }

    @Test
    void addClientNegativeTestAddressIsEmpty() {
        validClient.setAddress(null);
        assertThrows(ClientException.class, () -> clientService.add(validClient));
    }

    @Test
    void getClientPositiveTest() throws RepositoryException, ClientException {
        Client createdClient = clientService.add(validClient);
        assertEquals(validClient.getFirstName(), clientService.get(createdClient.getId()).getFirstName());
        assertEquals(validClient.getLastName(), clientService.get(createdClient.getId()).getLastName());
        assertEquals(validClient.getUsername(), clientService.get(createdClient.getId()).getUsername());
        assertEquals(validClient.getArchive(), clientService.get(createdClient.getId()).getArchive());
        assertEquals(validClient.getBalance(), clientService.get(createdClient.getId()).getBalance());
        assertEquals(validClient.getPassword(), clientService.get(createdClient.getId()).getPassword());
        assertEquals(validClient.getPhoneNumber(), clientService.get(createdClient.getId()).getPhoneNumber());
        assertEquals(validClient.getClientType().getType(), clientService.get(createdClient.getId()).getClientType().getType());
        assertEquals(validClient.getClientAccessType(), clientService.get(createdClient.getId()).getClientAccessType());
        assertEquals(validClient.getAddress().getCity(), clientService.get(createdClient.getId()).getAddress().getCity());
        assertEquals(validClient.getAddress().getNumber(), clientService.get(createdClient.getId()).getAddress().getNumber());
        assertEquals(validClient.getAddress().getStreet(), clientService.get(createdClient.getId()).getAddress().getStreet());
    }

    @Test
    void getClientNegativeTest() {
        assertThrows(RepositoryException.class, () -> clientService.get(validClient.getId()));
    }

    @Test
    void getClientInfoTest() throws RepositoryException, ClientException {
        Client createdClient = clientService.add(validClient);
        String clientInfo = createdClient.toString();
        assertEquals(clientInfo, clientService.getInfo(createdClient.getId()));
    }

    @Test
    void getClientBalanceTest() throws RepositoryException, ClientException {
        Client createdClient = clientService.add(validClient);
        Double clientBalance = validClient.getBalance();
        assertEquals(clientBalance, clientService.getClientBalance(createdClient.getId()));
    }

    @Test
    void isClientArchive() throws RepositoryException, ClientException {
        Client createdClient = clientService.add(validClient);
        Boolean isClientArchive = createdClient.getArchive();
        assertEquals(isClientArchive, clientService.isClientArchive(createdClient.getId()));
    }

    @Test
    void archiveClientPositiveTest() throws RepositoryException, ClientException {
        Client createdClient = clientService.add(validClient);
        assertFalse(clientService.get(createdClient.getId()).getArchive());
        clientService.archive(createdClient.getId());
        assertTrue(clientService.get(createdClient.getId()).getArchive());
    }

    @Test
    void getAllClientsTest() throws RepositoryException, ClientException {
        Client validClient2 = Client.builder()
                .id(UUID.randomUUID())
                .username("Username7")
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
        clientService.add(validClient);
        assertTrue(clientService.getAllClients().size() > 0);
        clientService.add(validClient2);
        assertTrue(clientService.getAllClients().size() > 1);
    }

    @Test
    void getClientByUsernameTest() throws RepositoryException, ClientException {
        Client createdClient = clientService.add(validClient);
        String clientUsername = validClient.getUsername();
        assertEquals(createdClient, clientService.getClientByUsername(clientUsername));
    }

    @Test
    void getPresentAndArchiveSizeTest() throws RepositoryException, ClientException {
        assertEquals(0, clientService.getPresentSize());
        assertEquals(0, clientService.getArchiveSize());
        Client createdClient = clientService.add(validClient);
        assertEquals(1, clientService.getPresentSize());
        assertEquals(0, clientService.getArchiveSize());
        clientService.archive(createdClient.getId());
        assertEquals(0, clientService.getPresentSize());
        assertEquals(1, clientService.getArchiveSize());

    }

    @Test
    void getAllClientsFilterTest() throws RepositoryException, ClientException {
        Client validClient2 = Client.builder()
                .id(UUID.randomUUID())
                .username("Username7")
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
        assertEquals(0, clientService.getAllClientsFilter("123454").size());
        Client createdClient = clientService.add(validClient);
        Client createdClient2 = clientService.add(validClient2);
        assertEquals(2, clientService.getAllClientsFilter("").size());
        assertEquals(1, clientService.getAllClientsFilter(createdClient.getId().toString()).size());
    }

    @Test
    void getClientsByUsernameTest() throws RepositoryException, ClientException {
        Client validClient2 = Client.builder()
                .id(UUID.randomUUID())
                .username("Username7")
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
        clientService.add(validClient);
        clientService.add(validClient2);
        assertEquals(2, clientService.getClientsByUsername("").size());
        assertEquals(2, clientService.getClientsByUsername("User").size());
        assertEquals(1, clientService.getClientsByUsername("Username7").size());
        assertEquals(0, clientService.getClientsByUsername("Usernameeee").size());
    }

    @Test
    void updateClientPositiveTest() throws RepositoryException, ClientException {
        Client createdClient = clientService.add(validClient);
        createdClient.setUsername("newUsername");
        createdClient.setPhoneNumber("54321");
        clientService.updateClient(createdClient.getId(), createdClient);
        assertEquals("newUsername", clientService.get(createdClient.getId()).getUsername());
        assertEquals("54321", clientService.get(createdClient.getId()).getPhoneNumber());
        assertEquals(validClient.getPassword(), clientService.get(createdClient.getId()).getPassword());
    }

    @Test
    void updateClientNegativeTest() {
        assertThrows(RepositoryException.class, () -> clientService.updateClient(validClient.getId(), validClient));
    }

    @Test
    void updatePasswordPositiveTest() throws RepositoryException, ClientException {
        Client createdClient = clientService.add(validClient);
        assertEquals("Password", clientService.get(createdClient.getId()).getPassword());
        clientService.updatePassword(createdClient.getId(), "newPassword");
        assertEquals("newPassword", clientService.get(createdClient.getId()).getPassword());
    }

    @Test
    void updatePasswordNegativeTest() {
        assertThrows(RepositoryException.class, () -> clientService.updatePassword(validClient.getId(), "newPassword"));
    }

    @Test
    void dearchivePositiveTest() throws RepositoryException, ClientException {
        validClient.setArchive(true);
        Client createdClient = clientService.add(validClient);
        assertTrue(clientService.get(createdClient.getId()).getArchive());
        clientService.dearchive(createdClient.getId());
        assertFalse(clientService.get(createdClient.getId()).getArchive());
    }

    @Test
    void dearchiveNegativeTest() throws RepositoryException, ClientException {
        Client createdClient = clientService.add(validClient);
        assertFalse(clientService.get(createdClient.getId()).getArchive());
        assertThrows(RepositoryException.class, () -> clientService.dearchive(createdClient.getId()));
    }

    @Test
    void getClientTypeByIdPositiveTest() throws RepositoryException, ClientException {
        Client createdClient = clientService.add(validClient);
        assertEquals(createdClient.getClientType(), clientService.getClientTypeById(createdClient.getClientType().getId()));
    }

    @Test
    void getClientTypeByIdNegativeTest() {
        assertThrows(RepositoryException.class, () -> clientService.getClientTypeById(validClient.getId()));
    }
}