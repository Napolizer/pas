package org.pl.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.exceptions.RepositoryException;
import org.pl.model.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.pl.model.Condition.DUSTY;

public class HardwareRepositoryTest {
    HardwareRepository repository;
    Hardware hardware1;
    Hardware hardware2;
    ArrayList<Hardware> list;

    @BeforeEach
    void setUp() {
        hardware1 = Hardware.builder()
                .archive(true)
                .hardwareType(new Computer(DUSTY))
                .price(100)
                .id(0)
                .build();
        hardware2 = Hardware.builder()
                .archive(false)
                .hardwareType(new Computer(DUSTY))
                .price(100)
                .id(1)
                .build();
        list = new ArrayList<>();
        repository = HardwareRepository.builder().elements(list).build();
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
            repository.add(hardware1);
            assertEquals(1, repository.getElements().size());
            assertTrue(repository.getElements().get(0) instanceof Hardware);
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    @Test
    void archiviseTest() {
        try {
            repository.add(hardware1);
            assertThrows(RepositoryException.class, () -> repository.archivise(hardware1.getID()));
            repository.add(hardware2);
            repository.archivise(hardware2.getID());
            assertTrue(hardware2.isArchive());
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getTest() {
        assertThrows(RepositoryException.class, () -> repository.get(-1));
        assertThrows(RepositoryException.class, () -> repository.get(hardware1.getID()));
        try {
            repository.add(hardware1);
            assertEquals(hardware1, repository.get(hardware1.getID()));
            assertThrows(RepositoryException.class, () -> repository.get(hardware2.getID()));
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getSizeTest() {
        try {
            assertEquals(0, repository.getSize(true));
            assertEquals(0, repository.getSize(false));
            repository.add(hardware1);
            assertEquals(0, repository.getSize(true));
            assertEquals(1, repository.getSize(false));
            repository.add(hardware2);
            assertEquals(1, repository.getSize(true));
            assertEquals(1, repository.getSize(false));
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    @Test
    void isArchiveTest() {
        try {
            repository.add(hardware1);
            assertTrue(repository.isArchive(hardware1.getID()));
            assertThrows(RepositoryException.class, () -> repository.isArchive(hardware2.getID()));
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    @Test
    void unarchiviseTest() {
        try {
            repository.add(hardware1);
            repository.unarchivise(hardware1.getID());
            assertFalse(repository.isArchive(hardware1.getID()));
            assertThrows(RepositoryException.class, () -> repository.unarchivise(hardware2.getID()));
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }
}
