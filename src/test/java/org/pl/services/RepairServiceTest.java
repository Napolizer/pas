package org.pl.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.exceptions.ClientException;
import org.pl.exceptions.HardwareException;
import org.pl.exceptions.RepairException;
import org.pl.exceptions.RepositoryException;
import org.pl.model.*;
import org.pl.repositories.RepairRepository;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class RepairServiceTest {
    Address address1, address2;
    Client client1, client2;
    Hardware hardware1, hardware2;
    HardwareType computer, monitor;
    ArrayList<Repair> repairs;
    RepairRepository repairRepository;
    RepairService repairService;

    @BeforeEach
    void setUp() {
        address1 = Address.builder()
                .city("Warszawa")
                .street("Uliczna")
                .number("34")
                .build();
        address2 = Address.builder()
                .city("Warszawa")
                .street("Kolorowa")
                .number("23")
                .build();
        client1 = Client.builder()
                .personalId(1)
                .firstName("Szymon")
                .lastName("Kowalski")
                .phoneNumber("123456789")
                .address(address1)
                .clientType(new Premium())
                .build();
        client2 = Client.builder()
                .personalId(2)
                .firstName("Kacper")
                .lastName("Jackowski")
                .phoneNumber("987654321")
                .address(address2)
                .clientType(new Vip())
                .build();
        computer = new Computer(Condition.DUSTY);
        monitor = new Monitor(Condition.AVERAGE);
        hardware1 = Hardware.builder()
                .id(1)
                .price(2000)
                .hardwareType(computer)
                .build();
        hardware2 = Hardware.builder()
                .id(2)
                .price(3000)
                .hardwareType(monitor)
                .build();
        repairs = new ArrayList<>();
        repairRepository = new RepairRepository(repairs);
        repairService = new RepairService(repairRepository);
    }

    @Test
    void repairServiceAddPositiveTest() throws RepositoryException, RepairException {
        repairService.add(client1, hardware1);
        assertNotNull(repairService.get(0));
        repairService.add(client2, hardware2);
        assertNotNull(repairService.get(1));
    }

    @Test
    void repairServiceAddNegativeTest() {
        assertThrows(RepairException.class,
                ()-> repairService.add(null, hardware1));
        assertThrows(RepairException.class,
                ()-> repairService.add(client1, null));
        assertThrows(RepositoryException.class,
                ()-> repairService.get(0));
    }

    @Test
    void repairServiceGetInfoTest() throws RepositoryException, RepairException {
        repairService.add(client1, hardware1);
        String expectedInfo = "Repair(id=0, archive=false, client=Client(archive=false, balance=0.0, firstName=Szymon, lastName=Kowalski, personalId=1, phoneNumber=123456789, clientType=Premium(), address=Address(city=Warszawa, number=34, street=Uliczna)), hardware=Hardware(id=1, archive=false, price=2000, hardwareType=Computer(condition=DUSTY)))";
        assertEquals(expectedInfo, repairService.getInfo(0));
    }

    @Test
    void repairServiceRemovePositiveTest() throws RepositoryException, RepairException, HardwareException, ClientException {
        repairService.add(client1, hardware1);
        assertEquals(1, repairService.getPresentSize());
        repairService.add(client2, hardware2);
        assertEquals(2, repairService.getPresentSize());
        repairService.repair(1);
        assertEquals(1, repairService.getPresentSize());
        assertTrue(repairService.get(1).isArchive());
        repairService.repair(0);
        assertTrue(repairService.get(0).isArchive());
    }

    @Test
    void repairServiceRemoveNegativeTest() throws HardwareException, RepositoryException, ClientException, RepairException {
        repairService.add(client1, hardware1);
        repairService.add(client2, hardware2);
        repairService.repair(1);
        assertThrows(RepositoryException.class,
                ()-> repairService.repair(1));
    }

    @Test
    void repairServiceGetSizeTest() throws RepositoryException, RepairException, HardwareException, ClientException {
        assertEquals(0, repairService.getPresentSize());
        assertEquals(0, repairService.getArchiveSize());
        repairService.add(client1, hardware1);
        assertEquals(1, repairService.getPresentSize());
        assertEquals(0, repairService.getArchiveSize());
        repairService.add(client2, hardware2);
        assertEquals(2, repairService.getPresentSize());
        assertEquals(0, repairService.getArchiveSize());
        repairService.repair(0);
        assertEquals(1, repairService.getPresentSize());
        assertEquals(1, repairService.getArchiveSize());
        repairService.repair(1);
        assertEquals(0, repairService.getPresentSize());
        assertEquals(2, repairService.getArchiveSize());
    }

    @Test
    void repairServiceRepairTest() throws RepositoryException, RepairException, HardwareException, ClientException {
        repairService.add(client1, hardware1);
        assertFalse(repairService.get(0).isArchive());
        repairService.repair(0);
        assertTrue(repairService.get(0).isArchive());
    }

    @Test
    void repairServiceRepairArchiveTest() throws RepositoryException, RepairException, HardwareException, ClientException {
        repairService.add(client1, hardware1);
        repairService.add(client1, hardware2);
        assertFalse(client1.isArchive());
        assertFalse(hardware1.isArchive());
        assertFalse(hardware2.isArchive());
        repairService.repair(0);
        assertFalse(client1.isArchive());
        assertTrue(hardware1.isArchive());
        assertFalse(hardware2.isArchive());
        repairService.repair(1);
        assertTrue(client1.isArchive());
        assertTrue(hardware1.isArchive());
        assertTrue(hardware2.isArchive());
    }

    @Test
    void repairServiceChangeBalanceTest() throws RepositoryException, RepairException, HardwareException, ClientException {
        repairService.add(client1, hardware1);
        repairService.add(client1, hardware2);
        assertEquals(0, client1.getBalance());
        repairService.repair(1);
        assertEquals(-2160, client1.getBalance());
        repairService.repair(0);
        assertEquals(-2164.5, client1.getBalance());
    }
}
