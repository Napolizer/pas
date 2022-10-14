package org.pl.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.pl.model.Condition.*;

class HardwareTest {

    Hardware hardware;

    @BeforeEach
    void setUp() {
        hardware = Hardware.builder()
                .hardwareType(new Computer(DUSTY))
                .price(100)
                .id(1)
                .build();
    }

    @Test
    void repair() {
        assertEquals(DUSTY, hardware.getHardwareType().getCondition());
        hardware.repair();
        assertEquals(FINE, hardware.getHardwareType().getCondition());
    }

    @Test
    void setArchive() {
        assertFalse(hardware.isArchive());
        hardware.setArchive(true);
        assertTrue(hardware.isArchive());
        hardware.setArchive(true);
        assertTrue(hardware.isArchive());
        hardware.setArchive(false);
        assertFalse(hardware.isArchive());
        hardware.setArchive(false);
        assertFalse(hardware.isArchive());
    }

    @Test
    void getID() {
        assertEquals(1, hardware.getID());
    }

    @Test
    void isArchive() {
        assertFalse(hardware.isArchive());
    }

    @Test
    void getPrice() {
        assertEquals(100, hardware.getPrice());
    }

    @Test
    void getHardwareType() {
        assertEquals(new Computer(DUSTY), hardware.getHardwareType());
    }

    @Test
    void setId() {
        hardware.setId(0);
        assertEquals(0, hardware.getID());
        hardware.setId(20);
        assertEquals(20, hardware.getID());
    }

    @Test
    void setPrice() {
        hardware.setPrice(500);
        assertEquals(500, hardware.getPrice());
        hardware.setPrice(900);
        assertEquals(900, hardware.getPrice());
    }

    @Test
    void setHardwareType() {
        hardware.setHardwareType(new Monitor(UNREPAIRABLE));
        assertEquals(new Monitor(UNREPAIRABLE), hardware.getHardwareType());
        hardware.setHardwareType(new Phone(FINE));
        assertEquals(new Phone(FINE), hardware.getHardwareType());
    }

    @Test
    void testEquals() {
        assertEquals(new Hardware(1, false, 100, new Computer(DUSTY)), hardware);
        assertNotEquals(new Hardware(2, true, 100, new Computer(DUSTY)), hardware);
        assertNotEquals(new Hardware(1, false, 120, new Computer(DUSTY)), hardware);
        assertNotEquals(new Hardware(1, false, 100, new Computer(FINE)), hardware);
        assertNotEquals(new Hardware(1, false, 100, new Phone(DUSTY)), hardware);
    }

    @Test
    void testToString() {
        assertEquals("Hardware(id=1, archive=false, price=100, hardwareType=Computer(condition=DUSTY))", hardware.toString());
    }
}