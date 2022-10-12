package org.pl.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    Address address;

    @BeforeEach
    void setUp() {
        address = Address.builder()
                .number("123")
                .street("White")
                .city("Lodz")
                .build();
    }

    @Test
    void getCity() {
        assertEquals("Lodz", address.getCity());
    }

    @Test
    void getNumber() {
        assertEquals("123", address.getNumber());
    }

    @Test
    void getStreet() {
        assertEquals("White", address.getStreet());
    }

    @Test
    void setCity() {
        address.setCity("Warsaw");
        assertEquals("Warsaw", address.getCity());
    }

    @Test
    void setNumber() {
        address.setNumber("321");
        assertEquals("321", address.getNumber());
    }

    @Test
    void setStreet() {
        address.setStreet("Black");
        assertEquals("Black", address.getStreet());
    }

    @Test
    void testEquals() {
        Address newAddress = Address.builder()
                .number("123")
                .city("London")
                .street("Tower")
                .build();

        Address sameAddress = Address.builder()
                .number("123")
                .street("White")
                .city("Lodz")
                .build();

        assertEquals(address, address);
        assertEquals(address, sameAddress);
        assertNotEquals(newAddress, address);
    }

    @Test
    void testToString() {
        assertEquals("Address(city=Lodz, number=123, street=White)", address.toString());
    }
}