package org.pl.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.exceptions.HardwareException;

import static org.junit.jupiter.api.Assertions.*;
import static org.pl.model.Condition.*;

class RepairTest {

    Repair repair;
    Client client;
    Hardware hardware;

    @BeforeEach
    void setUp() {
        client = Client.builder()
                .clientType(new Premium())
                .address(new Address())
                .balance(300.0)
                .firstName("John")
                .lastName("Doe")
                .personalId(2)
                .phoneNumber("123-123-123")
                .archive(false)
                .build();
        hardware = new Hardware(1, false, 100, new Computer(DUSTY));

        repair = Repair.builder()
                .client(client)
                .hardware(hardware)
                .archive(false)
                .id(1)
                .build();
    }

    @Test
    void calculateRepairCost() throws HardwareException {
        assertEquals(5.0, repair.calculateRepairCost());
    }

    @Test
    void setArchive() {
        repair.setArchive(false);
        assertFalse(repair.isArchive());
        repair.setArchive(true);
        assertTrue(repair.isArchive());
    }

    @Test
    void getID() {
        assertEquals(1, repair.getID());
    }

    @Test
    void isArchive() {
        assertFalse(repair.isArchive());
    }

    @Test
    void getClient() {
        assertEquals(client, repair.getClient());
    }

    @Test
    void getHardware() {
        assertEquals(hardware, repair.getHardware());
    }

    @Test
    void setId() {
       repair.setId(12);
       assertEquals(12, repair.getID());
       repair.setId(0);
       assertEquals(0, repair.getID());
    }

    @Test
    void setClient() {
        Client newClient = Client.builder()
                .clientType(new Vip())
                .address(new Address())
                .balance(300.0)
                .firstName("John")
                .lastName("Doe")
                .personalId(2)
                .phoneNumber("123-123-123")
                .archive(false)
                .build();
        repair.setClient(newClient);
        assertEquals(newClient, repair.getClient());
        assertNotEquals(client, repair.getClient());
    }

    @Test
    void setHardware() {
       Hardware newHardware = new Hardware(2, false, 100, new Computer(DUSTY));
       repair.setHardware(newHardware);
       assertEquals(newHardware, repair.getHardware());
       assertNotEquals(hardware, repair.getHardware());
    }

    @Test
    void testEquals() {
        Client newClient = Client.builder()
                .clientType(new Premium())
                .address(new Address())
                .balance(300.0)
                .firstName("John")
                .lastName("Doe")
                .personalId(2)
                .phoneNumber("123-123-123")
                .archive(false)
                .build();
        Hardware newHardware = new Hardware(1, false, 100, new Computer(DUSTY));

        Repair newRepair = Repair.builder()
                .client(newClient)
                .hardware(newHardware)
                .archive(false)
                .id(1)
                .build();
        assertEquals(newRepair, repair);
        newRepair.setArchive(true);
        assertNotEquals(newRepair, repair);
    }

    @Test
    void testToString() {
        assertEquals("Repair(id=1, archive=false, client=Client(" +
                "archive=false, balance=300.0, firstName=John, lastName=Doe, personalId=2, phoneNumber=123-123-123, clientType=Premium(" +
                "super=ClientType(factor=0.9, maxRepairs=5, typeName=Premium)), address=Address(city=null, number=null, street=null)), " +
                "hardware=Hardware(id=1, archive=false, price=100, hardwareType=Computer(condition=DUSTY)))", repair.toString());
    }
}