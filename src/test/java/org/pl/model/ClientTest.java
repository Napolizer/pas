package org.pl.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.exceptions.ClientException;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    Client client;
    Address address;

    @BeforeEach
    void setUp() {
        address = Address.builder()
                .street("White")
                .number("123")
                .city("Lodz")
                .build();

        client = Client.builder()
                .personalId(0)
                .clientType(new Basic())
                .phoneNumber("535-535-535")
                .balance(100)
                .firstName("John")
                .lastName("Doe")
                .address(address)
                .build();
    }

    @Test
    void calculateDiscount() throws ClientException {
        assertEquals(0.0, client.calculateDiscount(100));
    }

    @Test
    void changeBalance() {
        assertEquals(100, client.getBalance());
        client.changeBalance(100);
        assertEquals(200, client.getBalance());
        client.changeBalance(-200);
        assertEquals(0, client.getBalance());
    }

    @Test
    void setArchive() {
        client.setArchive(true);
        assertTrue(client.isArchive());
        client.setArchive(false);
        assertFalse(client.isArchive());
    }

    @Test
    void isArchive() {
        assertFalse(client.isArchive());
    }

    @Test
    void getBalance() {
        assertEquals(100, client.getBalance());
    }

    @Test
    void getFirstName() {
        assertEquals("John", client.getFirstName());
    }

    @Test
    void getLastName() {
        assertEquals("Doe", client.getLastName());
    }

    @Test
    void getPersonalId() {
        assertEquals(0, client.getPersonalId());
    }

    @Test
    void getPhoneNumber() {
        assertEquals("535-535-535", client.getPhoneNumber());
    }

    @Test
    void getClientType() {
        assertEquals(new Basic(), client.getClientType());
    }

    @Test
    void getAddress() {
        assertEquals(address, client.getAddress());
    }

    @Test
    void setBalance() {
        client.setBalance(200);
        assertEquals(200, client.getBalance());
    }

    @Test
    void setFirstName() {
        client.setFirstName("Ian");
        assertEquals("Ian", client.getFirstName());
    }

    @Test
    void setLastName() {
        client.setLastName("Nowak");
        assertEquals("Nowak", client.getLastName());
    }

    @Test
    void setPersonalId() {
        client.setPersonalId(1092);
        assertEquals(1092, client.getPersonalId());
    }

    @Test
    void setPhoneNumber() {
        client.setPhoneNumber("200-200-200");
        assertEquals("200-200-200", client.getPhoneNumber());
    }

    @Test
    void setClientType() {
       client.setClientType(new Vip());
       assertEquals(new Vip(), client.getClientType());
    }

    @Test
    void setAddress() {
        client.setAddress(new Address("Lublin", "123", "Green"));
        assertNotEquals(address, client.getAddress());
    }

    @Test
    void testEquals() {
        Client sameClient = Client.builder()
                .personalId(0)
                .clientType(new Basic())
                .phoneNumber("535-535-535")
                .balance(100)
                .firstName("John")
                .lastName("Doe")
                .address(address)
                .build();
        Client differentClient = Client.builder()
                .personalId(2)
                .clientType(new Basic())
                .phoneNumber("111-111-111")
                .balance(100)
                .firstName("John")
                .lastName("Doe")
                .address(address)
                .build();
        assertEquals(sameClient, client);
        assertNotEquals(differentClient, client);
    }

}