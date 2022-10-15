package org.pl.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Address;
import org.pl.model.Basic;
import org.pl.model.Client;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ClientRepositoryTest {
    ClientRepository repository;
    Client client1;
    Client client2;
    Address address;
    ArrayList<Client> list;

    @BeforeEach
    void setUp() {
        address = Address.builder()
                .street("White")
                .number("123")
                .city("Lodz")
                .build();
        client1 = Client.builder()
                .archive(true)
                .clientType(new Basic())
                .phoneNumber("535-535-535")
                .balance(100)
                .firstName("John")
                .lastName("Doe")
                .personalId(0)
                .address(address)
                .build();
        client2 = Client.builder()
                .archive(false)
                .clientType(new Basic())
                .phoneNumber("535-535-535")
                .balance(100)
                .firstName("John")
                .lastName("Doe")
                .personalId(1)
                .address(address)
                .build();
        list = new ArrayList<>();
        repository = ClientRepository.builder().elements(list).build();
    }

    @Test
    void getElementsTest() {
        assertTrue(repository.getElements() != null);
        assertEquals(0, repository.getElements().size());
    }

    @Test
    void addTest() {
        assertThrows(RepositoryException.class, () -> repository.add(null));
        assertEquals(0, repository.getElements().size());
        try {
            repository.add(client1);
            assertEquals(1, repository.getElements().size());
            assertTrue(repository.getElements().get(0) != null);
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    @Test
    void archiviseTest() {
        try {
            repository.add(client1);
            assertThrows(RepositoryException.class, () -> repository.archivise(client1.getID()));
            repository.add(client2);
            repository.archivise(client2.getID());
            assertTrue(client2.isArchive());
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getTest() {
        assertThrows(RepositoryException.class, () -> repository.get(-1));
        assertThrows(RepositoryException.class, () -> repository.get(client1.getID()));
        try {
            repository.add(client1);
            assertEquals(client1, repository.get(client1.getID()));
            assertThrows(RepositoryException.class, () -> repository.get(client2.getID()));
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getSizeTest() {
        try {
            assertEquals(0, repository.getSize(true));
            assertEquals(0, repository.getSize(false));
            repository.add(client1);
            assertEquals(0, repository.getSize(true));
            assertEquals(1, repository.getSize(false));
            repository.add(client2);
            assertEquals(1, repository.getSize(true));
            assertEquals(1, repository.getSize(false));
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    @Test
    void isArchiveTest() {
        try {
            repository.add(client1);
            assertTrue(repository.isArchive(client1.getID()));
            assertThrows(RepositoryException.class, () -> repository.isArchive(client2.getID()));
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    @Test
    void unarchiviseTest() {
        try {
            repository.add(client1);
            repository.unarchivise(client1.getID());
            assertFalse(repository.isArchive(client1.getID()));
            assertThrows(RepositoryException.class, () -> repository.unarchivise(client2.getID()));
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }
}