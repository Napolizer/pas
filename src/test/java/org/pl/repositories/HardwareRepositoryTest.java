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
    Repository<Entity> repository;
    Entity entity;
    Entity entity2;
    ArrayList<Entity> list;

    @BeforeEach
    void setUp() {
        entity = Hardware.builder()
                .archive(true)
                .hardwareType(new Computer(DUSTY))
                .price(100)
                .id(1)
                .build();
        entity2 = Hardware.builder()
                .archive(false)
                .hardwareType(new Computer(DUSTY))
                .price(100)
                .id(2)
                .build();
        list = new ArrayList<Entity>();
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
            repository.add(entity);
            assertEquals(1, repository.getElements().size());
            assertTrue(repository.getElements().get(0) instanceof Hardware);
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    @Test
    void archiviseTest() {
        try {
            repository.add(entity);
            assertThrows(RepositoryException.class, () -> repository.archivise(entity.getID()));
            repository.add(entity2);
            repository.archivise(entity2.getID());
            assertTrue(entity2.isArchive());
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getTest() {
        assertThrows(RepositoryException.class, () -> repository.get(-1));
        assertThrows(RepositoryException.class, () -> repository.get(entity.getID()));
        try {
            repository.add(entity);
            assertEquals(entity, repository.get(entity.getID()));
            assertThrows(RepositoryException.class, () -> repository.get(entity2.getID()));
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getSizeTest() {
        try {
            assertEquals(0, repository.getSize(true));
            assertEquals(0, repository.getSize(false));
            repository.add(entity);
            assertEquals(0, repository.getSize(true));
            assertEquals(1, repository.getSize(false));
            repository.add(entity2);
            assertEquals(1, repository.getSize(true));
            assertEquals(1, repository.getSize(false));
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    @Test
    void isArchiveTest() {
        try {
            repository.add(entity);
            assertTrue(repository.isArchive(entity.getID()));
            assertThrows(RepositoryException.class, () -> repository.isArchive(entity2.getID()));
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    @Test
    void unarchiviseTest() {
        try {
            repository.add(entity);
            repository.unarchivise(entity.getID());
            assertFalse(repository.isArchive(entity.getID()));
            assertThrows(RepositoryException.class, () -> repository.unarchivise(entity2.getID()));
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }
}
