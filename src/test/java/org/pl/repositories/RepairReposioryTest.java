package org.pl.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.exceptions.ClientException;
import org.pl.exceptions.HardwareException;
import org.pl.exceptions.RepositoryException;
import org.pl.model.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.pl.model.Condition.DUSTY;

public class RepairReposioryTest {
    RepairRepository repository;
    Address address;
    Client client;
    Hardware hardware;
    Repair repair1;
    Repair repair2;
    ArrayList<Repair> list;

    @BeforeEach
    void setUp() {
        address = Address.builder()
                .street("White")
                .number("123")
                .city("Lodz")
                .build();

        client = Client.builder()
                .clientType(new Premium())
                .address(address)
                .balance(300.0)
                .firstName("John")
                .lastName("Doe")
                .personalId(0)
                .phoneNumber("123-123-123")
                .archive(false)
                .build();
        hardware = Hardware.builder()
                .archive(false)
                .hardwareType(new Computer(DUSTY))
                .price(100)
                .id(1)
                .build();
        repair1 = Repair.builder()
                .client(client)
                .hardware(hardware)
                .archive(true)
                .id(0)
                .build();
        repair2 = Repair.builder()
                .client(client)
                .hardware(hardware)
                .archive(false)
                .id(1)
                .build();
        list = new ArrayList<>();
        repository = RepairRepository.builder().elements(list).build();
    }

    @Test
    void getElementsTest() {
        assertNotNull(repository.getElements());
        assertEquals(0, repository.getElements().size());
    }

    @Test
    void addTest() throws RepositoryException {
        assertThrows(RepositoryException.class, () -> repository.add(null));
        assertEquals(0, repository.getElements().size());
        repository.add(repair1);
        assertEquals(1, repository.getElements().size());
        assertNotNull(repository.getElements().get(0));
    }

    @Test
    void archiviseTest() throws RepositoryException {
        repository.add(repair1);
        assertThrows(RepositoryException.class, () -> repository.archivise(repair1.getID()));
        repository.add(repair2);
        repository.archivise(repair2.getID());
        assertTrue(repair2.isArchive());
    }

    @Test
    void getTest() throws RepositoryException {
        assertThrows(RepositoryException.class, () -> repository.get(-1));
        assertThrows(RepositoryException.class, () -> repository.get(repair1.getID()));
        repository.add(repair1);
        assertEquals(repair1, repository.get(repair1.getID()));
        assertThrows(RepositoryException.class, () -> repository.get(repair2.getID()));
    }

    @Test
    void getSizeTest() throws RepositoryException {
        assertEquals(0, repository.getSize(true));
        assertEquals(0, repository.getSize(false));
        repository.add(repair1);
        assertEquals(0, repository.getSize(true));
        assertEquals(1, repository.getSize(false));
        repository.add(repair2);
        assertEquals(1, repository.getSize(true));
        assertEquals(1, repository.getSize(false));
    }

    @Test
    void isArchiveTest() throws RepositoryException {
        repository.add(repair1);
        assertTrue(repository.isArchive(repair1.getID()));
        assertThrows(RepositoryException.class, () -> repository.isArchive(repair2.getID()));
        repository.add(repair1);
        assertTrue(repository.isArchive(repair2.getID()));
    }

    @Test
    void unarchiviseTest() throws RepositoryException {
        repository.add(repair1);
        repository.unarchivise(repair1.getID());
        assertFalse(repository.isArchive(repair1.getID()));
        assertThrows(RepositoryException.class, () -> repository.unarchivise(repair2.getID()));
    }

    @Test
    void getClientRepairs() throws RepositoryException {
        assertEquals(0, repository.getClientRepairs(client));
        repository.add(repair1);
        assertEquals(1, repository.getClientRepairs(client));
        repository.add(repair2);
        assertEquals(2, repository.getClientRepairs(client));
    }

    @Test
    void repairTest() throws RepositoryException, HardwareException, ClientException {
        repository.add(repair1);
        repository.repair(0);
        assertTrue(repository.get(0).isArchive());
    }
}
