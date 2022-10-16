package org.pl.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.exceptions.HardwareException;
import org.pl.exceptions.RepositoryException;
import org.pl.exceptions.ServiceException;
import org.pl.model.*;
import org.pl.repositories.HardwareRepository;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class HardwareServiceTest {
    HardwareType computer, console, monitor, phone;
    ArrayList<Hardware> hardwares;
    HardwareRepository hardwareRepository;
    HardwareService hardwareService;

    @BeforeEach
    void setUp() {
        computer = new Computer(Condition.FINE);
        console = new Console(Condition.VERY_BAD);
        monitor = new Monitor(Condition.DUSTY);
        phone = new Phone(Condition.UNREPAIRABLE);
        hardwares = new ArrayList<>();
        hardwareRepository = new HardwareRepository(hardwares);
        hardwareService = new HardwareService(hardwareRepository);
    }

    @Test
    void hardwareServiceAddPositiveTest() throws ServiceException, RepositoryException, HardwareException {
        hardwareService.add(1000, "computer", Condition.FINE);
        assertNotNull(hardwareService.get(0));
        hardwareService.add(100, "console", Condition.VERY_BAD);
        assertNotNull(hardwareService.get(1));
        hardwareService.add(10, "monitor", Condition.DUSTY);
        assertNotNull(hardwareService.get(2));
        hardwareService.add(10, "phone", Condition.UNREPAIRABLE);
        assertNotNull(hardwareService.get(3));
    }

    @Test
    void hardwareServiceAddNegativeTest() {
        assertThrows(ServiceException.class,
                ()-> hardwareService.add(1000, "Camputer", Condition.UNREPAIRABLE));
        assertThrows(HardwareException.class,
                ()-> hardwareService.add(-100, "console", Condition.UNREPAIRABLE));
        assertThrows(ServiceException.class,
                ()-> hardwareService.add(10, "mOndsItOr", Condition.UNREPAIRABLE));
        assertThrows(ServiceException.class,
                ()-> hardwareService.add(10, "mOndsItOr", "verygood"));
    }

    @Test
    void hardwareServiceGetInfoTest() throws ServiceException, HardwareException, RepositoryException {
        hardwareService.add(200, "monitor", "fine");
        String expectedInfo = "Hardware(id=0, archive=false, price=200, hardwareType=Monitor(condition=FINE))";
        assertEquals(expectedInfo, hardwareService.getInfo(0));
    }

    @Test
    void hardwareServiceRemovePositiveTest() throws HardwareException, RepositoryException {
        hardwareService.add(100, computer);
        assertEquals(1, hardwareService.getPresentSize());
        hardwareService.add(1000, console);
        assertEquals(2, hardwareService.getPresentSize());
        hardwareService.remove(1);
        assertEquals(1, hardwareService.getPresentSize());
        assertTrue(hardwareService.get(1).isArchive());
        hardwareService.remove(0);
        assertEquals(0, hardwareService.getPresentSize());
        assertTrue(hardwareService.get(0).isArchive());
    }

    @Test
    void hardwareServiceRemoveNegativeTest() throws HardwareException, RepositoryException {
        hardwareService.add(1000, phone);
        hardwareService.add(1000, computer);
        hardwareService.remove(1);
        assertThrows(RepositoryException.class,
                ()-> hardwareService.remove(1));
    }

    @Test
    void hardwareServiceGetSizeTest() throws RepositoryException, HardwareException {
        assertEquals(0, hardwareService.getPresentSize());
        assertEquals(0, hardwareService.getArchiveSize());
        hardwareService.add(100, monitor);
        assertEquals(1, hardwareService.getPresentSize());
        assertEquals(0, hardwareService.getArchiveSize());
        hardwareService.add(1000, phone);
        assertEquals(2, hardwareService.getPresentSize());
        assertEquals(0, hardwareService.getArchiveSize());
        hardwareService.remove(0);
        assertEquals(1, hardwareService.getPresentSize());
        assertEquals(1, hardwareService.getArchiveSize());
        hardwareService.remove(1);
        assertEquals(0, hardwareService.getPresentSize());
        assertEquals(2, hardwareService.getArchiveSize());
    }
}
