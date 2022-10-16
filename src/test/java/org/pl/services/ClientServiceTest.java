package org.pl.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.exceptions.ClientException;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Address;
import org.pl.model.Client;
import org.pl.repositories.ClientRepository;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ClientServiceTest {
    ClientRepository clientRepository;
    Address address;
    ArrayList<Client> clientsList;
    ClientService clientService;

    @BeforeEach
    void setUp() {
        address = Address.builder()
                .street("White")
                .number("123")
                .city("Lodz")
                .build();
        clientsList = new ArrayList<>();
        clientRepository = new ClientRepository(clientsList);
        clientService = new ClientService(clientRepository);
    }

    @Test
    void clientServiceAddPositiveTest() throws RepositoryException, ClientException {
        clientService.add("Andrzej", "Niesapkowski", "603462321", address);
        assertNotNull(clientService.get(0));
        clientService.add("Anna", "Annowska", "124464322", address);
        assertNotNull(clientService.get(1));
        clientService.add("Jaskier", "Uzdolniony", "222222222", address);
        assertNotNull(clientService.get(2));
        clientService.add("Pepe", "Monocyklowy", "121424622", address);
        assertNotNull(clientService.get(3));
    }

    @Test
    void clientServiceAddNegativeTest() {
        assertThrows(ClientException.class,
                ()-> clientService.add("", "Niesapkowski", "603462321", address));
        assertThrows(ClientException.class,
                ()-> clientService.add("Anna", "", "603462321", address));
        assertThrows(ClientException.class,
                ()-> clientService.add("Andrzej", "Niesapkowski", "", address));
        assertThrows(ClientException.class,
                ()-> clientService.add("Andrzej", "Niesapkowski", "603462321", null));
    }

    @Test
    void clientServiceToStringTest() throws RepositoryException, ClientException {
        clientService.add("Pan", "Tadeusz", "280618349", address);
        String expectedInfo = "Client(archive=false, balance=0.0, firstName=Pan, lastName=Tadeusz, personalId=0, phoneNumber=280618349, clientType=null, address=Address(city=Lodz, number=123, street=White))";
        assertEquals(expectedInfo, clientService.getInfo(0));
    }

    @Test
    void clientServiceRemovePositiveTest() throws RepositoryException, ClientException {
        Address address2 = Address.builder()
                         .city("Katowice")
                         .street("Porajska")
                         .number("34")
                         .build();
        clientService.add("Jakub", "Stokrotka", "5876321210", address);
        assertEquals(1, clientService.getPresentSize());
        clientService.add("Marcin", "Steczkowski", "8765432109", address2);
        assertEquals(2, clientService.getPresentSize());
        clientService.remove(1);
        assertEquals(1, clientService.getPresentSize());
        assertTrue(clientService.get(1).isArchive());
        assertFalse(clientService.get(0).isArchive());
        clientService.remove(0);
        assertEquals(0, clientService.getPresentSize());
    }

    @Test
    void clientServiceRemoveNegativeTest() throws RepositoryException, ClientException {
        Address address2 = Address.builder()
                .city("Katowice")
                .street("Porajska")
                .number("34")
                .build();
        clientService.add("Jakub", "Stokrotka", "5876321210", address);
        clientService.add("Marcin", "Steczkowski", "8765432109", address2);
        clientService.remove(0);
        assertThrows(RepositoryException.class,
                ()-> clientService.remove(0));
    }

    @Test
    void clientServiceGetSizeTest() throws RepositoryException, ClientException {

        assertEquals(0, clientService.getPresentSize());
        assertEquals(0, clientService.getArchiveSize());
        clientService.add("Kuba", "Jakubowski", "1234567890", address);
        assertEquals(1, clientService.getPresentSize());
        assertEquals(0, clientService.getArchiveSize());
        Address address2 = Address.builder()
                .city("Katowice")
                .street("Porajska")
                .number("34")
                .build();
        clientService.add("Piotr", "Piotrkowski", "0987654321", address2);
        assertEquals(2, clientService.getPresentSize());
        assertEquals(0, clientService.getArchiveSize());
        clientService.remove(0);
        assertEquals(1, clientService.getPresentSize());
        assertEquals(1, clientService.getArchiveSize());
        clientService.remove(1);
        assertEquals(0, clientService.getPresentSize());
        assertEquals(2, clientService.getArchiveSize());
    }

    @Test
    void clientServiceGetClientBalanceTest() throws RepositoryException, ClientException {
        clientService.add("Adam", "Adamowski", "1234565432", address);
        assertEquals(0, clientService.getClientBalance(0));
        clientService.get(0).changeBalance(100);
        assertEquals(100, clientService.getClientBalance(0));
    }
}
