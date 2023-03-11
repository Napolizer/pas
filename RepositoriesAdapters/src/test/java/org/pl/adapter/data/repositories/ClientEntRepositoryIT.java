package org.pl.adapter.data.repositories;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.pl.adapter.data.model.*;
import org.pl.adapter.data.model.exceptions.RepositoryEntException;

import javax.transaction.*;
import java.io.File;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

@ExtendWith(ArquillianExtension.class)
public class ClientEntRepositoryIT {
    @Inject
    private ClientEntRepository clientEntRepository;

    @Inject
    private UserTransaction userTransaction;

    @Inject
    private EntityManager entityManager;

    private ClientEnt validClient;
    private AddressEnt validAddress;

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
        validAddress = AddressEnt.builder()
                .city("Lodz")
                .street("Przybyszewskiego")
                .number("21")
                .build();
        validClient = ClientEnt.builder()
                .id(UUID.randomUUID())
                .username("Username")
                .password("Password")
                .archive(false)
                .balance(100.0)
                .firstName("Tester")
                .lastName("Testowy")
                .phoneNumber("123456789")
                .clientTypeEnt(new BasicEnt())
                .addressEnt(validAddress)
                .clientAccessTypeEnt(ClientAccessTypeEnt.EMPLOYEES)
                .build();

        try {
            userTransaction.begin();
            entityManager.clear();
            userTransaction.commit();
        } catch (Exception ignored) {

        }


    }

    @Test
    public void repositoryInjectionTest() {
        assertThat(clientEntRepository, is(notNullValue()));
    }

    @Test
    public void saveClientPositiveTest() throws RepositoryEntException {
        ClientEnt savedClient = clientEntRepository.saveClient(validClient);
        assertThat(savedClient, is(notNullValue()));
        assertThat(savedClient.getId(), is(equalTo(validClient.getId())));
        assertThat(savedClient.getUsername(), is(equalTo(validClient.getUsername())));
        assertThat(savedClient.getPassword(), is(equalTo(validClient.getPassword())));
        assertThat(savedClient.getArchive(), is(equalTo(validClient.getArchive())));
        assertThat(savedClient.getBalance(), is(equalTo(validClient.getBalance())));
        assertThat(savedClient.getFirstName(), is(equalTo(validClient.getFirstName())));
        assertThat(savedClient.getLastName(), is(equalTo(validClient.getLastName())));
        assertThat(savedClient.getPhoneNumber(), is(equalTo(validClient.getPhoneNumber())));
        assertThat(savedClient.getClientTypeEnt(), is(equalTo(validClient.getClientTypeEnt())));
        assertThat(savedClient.getAddressEnt(), is(equalTo(validClient.getAddressEnt())));
        assertThat(savedClient.getClientAccessTypeEnt(), is(equalTo(validClient.getClientAccessTypeEnt())));
    }

    @Test
    public void saveClientNegativeTest() throws RepositoryEntException {
        ClientEnt savedClient = clientEntRepository.saveClient(validClient);
        assertThat(savedClient, is(notNullValue()));
        Assertions.assertThrows(RepositoryEntException.class, () -> clientEntRepository.saveClient(validClient));
    }

    @Test
    public void getClientByIdPositiveTest() throws RepositoryEntException {
        ClientEnt savedClient = clientEntRepository.saveClient(validClient);
        assertThat(savedClient, is(notNullValue()));
        assertThat(clientEntRepository.getClientById(savedClient.getId()), is(notNullValue()));
        assertThat(clientEntRepository.getClientById(savedClient.getId()), is(equalTo(savedClient)));
    }

    @Test
    public void getClientByIdNegativeTest() {
        Assertions.assertThrows(RepositoryEntException.class, () -> clientEntRepository.getClientById(UUID.randomUUID()));
    }

    @Test
    public void updateClientPositiveTest() throws RepositoryEntException {
        ClientEnt clientEnt = ClientEnt.builder()
                .id(UUID.randomUUID())
                .username("TEST")
                .password("test")
                .archive(true)
                .balance(101.0)
                .firstName("Test")
                .lastName("Testow")
                .phoneNumber("123456712")
                .clientTypeEnt(new PremiumEnt())
                .addressEnt(AddressEnt.builder()
                        .city("Krakow")
                        .street("Krakowska")
                        .number("12")
                        .build())
                .clientAccessTypeEnt(ClientAccessTypeEnt.ADMINISTRATORS)
                .build();
        ClientEnt savedClient = clientEntRepository.saveClient(validClient);
        assertThat(savedClient, is(notNullValue()));
        ClientEnt updatedClient = clientEntRepository.updateClient(savedClient.getId(), clientEnt);

        assertThat(updatedClient.getId(), is(equalTo(savedClient.getId())));
        assertThat(updatedClient.getPassword(), is(equalTo(savedClient.getPassword())));

        assertThat(updatedClient.getUsername(), is(equalTo(clientEnt.getUsername())));
        assertThat(updatedClient.getArchive(), is(equalTo(clientEnt.getArchive())));
        assertThat(updatedClient.getBalance(), is(equalTo(clientEnt.getBalance())));
        assertThat(updatedClient.getFirstName(), is(equalTo(clientEnt.getFirstName())));
        assertThat(updatedClient.getLastName(), is(equalTo(clientEnt.getLastName())));
        assertThat(updatedClient.getPhoneNumber(), is(equalTo(clientEnt.getPhoneNumber())));
        assertThat(updatedClient.getClientTypeEnt().getType(), is(equalTo(clientEnt.getClientTypeEnt().getType())));
        assertThat(updatedClient.getClientTypeEnt().getFactor(), is(equalTo(clientEnt.getClientTypeEnt().getFactor())));
        assertThat(updatedClient.getClientTypeEnt().getMaxRepairs(), is(equalTo(clientEnt.getClientTypeEnt().getMaxRepairs())));
        assertThat(updatedClient.getAddressEnt(), is(equalTo(clientEnt.getAddressEnt())));
        assertThat(updatedClient.getClientAccessTypeEnt(), is(equalTo(clientEnt.getClientAccessTypeEnt())));
    }

    @Test
    public void updateClientNegativeTest() throws RepositoryEntException {
        ClientEnt savedClient = clientEntRepository.saveClient(validClient);
        assertThat(savedClient, is(notNullValue()));
        Assertions.assertThrows(RepositoryEntException.class, () -> clientEntRepository.updateClient(UUID.randomUUID(), validClient));
        Assertions.assertThrows(RepositoryEntException.class, () -> clientEntRepository.updateClient(validClient.getId(), null));
    }

    @Test
    public void changePasswordPositiveTest() throws RepositoryEntException {
        ClientEnt savedClient = clientEntRepository.saveClient(validClient);
        assertThat(savedClient, is(notNullValue()));
        ClientEnt updatedClient = clientEntRepository.changePassword(validClient.getId(), "haslo123");

        assertThat(updatedClient.getId(), is(equalTo(savedClient.getId())));
        assertThat(updatedClient.getPassword(), not(is(equalTo(savedClient.getPassword()))));

        assertThat(updatedClient.getUsername(), is(equalTo(validClient.getUsername())));
        assertThat(updatedClient.getArchive(), is(equalTo(validClient.getArchive())));
        assertThat(updatedClient.getBalance(), is(equalTo(validClient.getBalance())));
        assertThat(updatedClient.getFirstName(), is(equalTo(validClient.getFirstName())));
        assertThat(updatedClient.getLastName(), is(equalTo(validClient.getLastName())));
        assertThat(updatedClient.getPhoneNumber(), is(equalTo(validClient.getPhoneNumber())));
        assertThat(updatedClient.getClientTypeEnt().getType(), is(equalTo(validClient.getClientTypeEnt().getType())));
        assertThat(updatedClient.getClientTypeEnt().getFactor(), is(equalTo(validClient.getClientTypeEnt().getFactor())));
        assertThat(updatedClient.getClientTypeEnt().getMaxRepairs(), is(equalTo(validClient.getClientTypeEnt().getMaxRepairs())));
        assertThat(updatedClient.getAddressEnt(), is(equalTo(validClient.getAddressEnt())));
        assertThat(updatedClient.getClientAccessTypeEnt(), is(equalTo(validClient.getClientAccessTypeEnt())));
    }

    @Test
    public void changePasswordNegativeTest() throws RepositoryEntException {
        ClientEnt savedClient = clientEntRepository.saveClient(validClient);
        assertThat(savedClient, is(notNullValue()));
        Assertions.assertThrows(RepositoryEntException.class, () -> clientEntRepository.changePassword(UUID.randomUUID(), "haslo"));
        Assertions.assertThrows(RepositoryEntException.class, () -> clientEntRepository.changePassword(validClient.getId(), null));
    }

    @Test
    public void deleteClientPositiveTest() throws RepositoryEntException {
        ClientEnt savedClient = clientEntRepository.saveClient(validClient);
        assertThat(savedClient, is(notNullValue()));
        ClientEnt deletedClient = clientEntRepository.deleteClient(savedClient.getId());
        assertThat(deletedClient.getArchive(), is(true));
        Assertions.assertDoesNotThrow(() -> clientEntRepository.getClientById(savedClient.getId()));
    }

    @Test
    public void deleteClientNegativeTest() throws RepositoryEntException {
        validClient.setArchive(true);
        ClientEnt savedClient = clientEntRepository.saveClient(validClient);
        assertThat(savedClient, is(notNullValue()));
        Assertions.assertThrows(RepositoryEntException.class, () -> clientEntRepository.deleteClient(validClient.getId()));
        Assertions.assertThrows(RepositoryEntException.class, () -> clientEntRepository.deleteClient(UUID.randomUUID()));
        Assertions.assertThrows(RepositoryEntException.class, () -> clientEntRepository.deleteClient(null));
    }

    @Test
    public void getClientsPositiveTest() throws RepositoryEntException {
        ClientEnt savedClient1 = clientEntRepository.saveClient(validClient);
        assertThat(savedClient1, is(notNullValue()));
        savedClient1.setId(UUID.randomUUID());
        savedClient1.setArchive(true);
        ClientEnt savedClient2 = clientEntRepository.saveClient(savedClient1);
        assertThat(savedClient2, is(notNullValue()));
        assertThat(clientEntRepository.getClients(false), is(notNullValue()));
        assertThat(clientEntRepository.getClients(true), is(notNullValue()));
    }

    @Test
    public void getAllClientsPositiveTest() throws RepositoryEntException {
        ClientEnt savedClient1 = clientEntRepository.saveClient(validClient);
        assertThat(savedClient1, is(notNullValue()));
        assertThat(clientEntRepository.getAllClients(), is(notNullValue()));
        savedClient1.setId(UUID.randomUUID());
        ClientEnt savedClient2 = clientEntRepository.saveClient(savedClient1);
        assertThat(savedClient2, is(notNullValue()));
        assertThat(clientEntRepository.getAllClients(), is(notNullValue()));
    }

    @Test
    public void getClientByUsernamePositiveTest() throws RepositoryEntException {
        ClientEnt savedClient1 = clientEntRepository.saveClient(validClient);
        assertThat(savedClient1, is(notNullValue()));
        assertThat(clientEntRepository.getClientByUsername(savedClient1.getUsername()), is(equalTo(savedClient1)));
    }

    @Test
    public void getClientByUsernameNegativeTest() {
        Assertions.assertThrows(RepositoryEntException.class, () -> clientEntRepository.getClientByUsername("mkdmsajnajhk"));
        Assertions.assertThrows(RepositoryEntException.class, () -> clientEntRepository.getClientByUsername(null));
    }

    @Test
    public void getAllClientsFilterPositiveTest() throws RepositoryEntException {
        assertThat(clientEntRepository.getAllClientsFilter("1234567890123456789012345678901234567890").size(), is(equalTo(0)));
        ClientEnt savedClient1 = clientEntRepository.saveClient(validClient);
        assertThat(savedClient1, is(notNullValue()));
        assertThat(clientEntRepository.getAllClientsFilter("").size(), is(notNullValue()));
        String id = savedClient1.getId().toString();
        id = id.substring(0, id.length() - 3);
        assertThat(clientEntRepository.getAllClientsFilter(id).size(), is(notNullValue()));
    }

    @Test
    public void getClientsByUsernamePositiveTest() throws RepositoryEntException {
        ClientEnt savedClient1 = clientEntRepository.saveClient(validClient);
        assertThat(savedClient1, is(notNullValue()));
        assertThat(clientEntRepository.getClientsByUsername("").size(), is(notNullValue()));
        assertThat(clientEntRepository.getClientsByUsername("User").size(), is(equalTo(1)));
        assertThat(clientEntRepository.getClientsByUsername("Usernameee").size(), is(equalTo(0)));
        savedClient1.setId(UUID.randomUUID());
        savedClient1.setUsername("Usernam");
        ClientEnt savedClient2 = clientEntRepository.saveClient(savedClient1);
        assertThat(savedClient2, is(notNullValue()));
        assertThat(clientEntRepository.getClientsByUsername("").size(), is(notNullValue()));
        assertThat(clientEntRepository.getClientsByUsername("User").size(), is(equalTo(2)));
        assertThat(clientEntRepository.getClientsByUsername("Username").size(), is(equalTo(1)));
    }

    @Test
    public void restoreClientPositiveTest() throws RepositoryEntException {
        validClient.setArchive(true);
        ClientEnt savedClient = clientEntRepository.saveClient(validClient);
        assertThat(savedClient, is(notNullValue()));
        ClientEnt restoredClient = clientEntRepository.restoreClient(savedClient.getId());
        assertThat(restoredClient.getArchive(), is(false));
    }

    @Test
    public void restoreClientNegativeTest() throws RepositoryEntException {
        ClientEnt savedClient = clientEntRepository.saveClient(validClient);
        assertThat(savedClient, is(notNullValue()));
        Assertions.assertThrows(RepositoryEntException.class, () -> clientEntRepository.restoreClient(savedClient.getId()));
        Assertions.assertThrows(RepositoryEntException.class, () -> clientEntRepository.restoreClient(UUID.randomUUID()));
    }
}
