package org.pl.adapter.data.converters;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pl.adapter.data.model.*;
import org.pl.model.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ClientConverterTest {
    @Mock
    private ClientTypeConverter clientTypeConverter;
    @Mock
    private AddressConverter addressConverter;
    @Mock
    private ClientAccessTypeConverter clientAccessTypeConverter;
    @InjectMocks
    private ClientConverter clientConverter = new ClientConverter();

    @Test
    public void convertClientFromDomainToEntModelTest() {
        ClientType basicClientType = new Basic();

        Address address = Address.builder()
                .city("c")
                .number("2")
                .street("c")
                .build();

        Client client = Client.builder()
                .id(UUID.randomUUID())
                .firstName("a")
                .lastName("b")
                .username("s")
                .password("s")
                .archive(false)
                .balance(200.5)
                .phoneNumber("33")
                .clientType(basicClientType)
                .address(address)
                .clientAccessType(ClientAccessType.ADMINISTRATORS)
                .build();

        ClientEnt clientEnt = clientConverter.convert(client);

        verify(clientTypeConverter).convert(basicClientType);
        verify(addressConverter).convert(address);
        verify(clientAccessTypeConverter).convert(ClientAccessType.ADMINISTRATORS);

        assertInstanceOf(ClientEnt.class, clientEnt);
        assertEquals(client.getId(), clientEnt.getId());
        assertEquals(client.getFirstName(), clientEnt.getFirstName());
        assertEquals(client.getLastName(), clientEnt.getLastName());
        assertEquals(client.getUsername(), clientEnt.getUsername());
        assertEquals(client.getPassword(), clientEnt.getPassword());
        assertEquals(client.getPhoneNumber(), clientEnt.getPhoneNumber());
        assertEquals(client.isArchive(), clientEnt.isArchive());
        assertEquals(client.getPhoneNumber(), clientEnt.getPhoneNumber());
        assertEquals(client.getBalance(), clientEnt.getBalance());
    }

    @Test
    void convertClientFromEntToDomainModelTest() {
        ClientTypeEnt basicClientTypeEnt = new BasicEnt();

        AddressEnt addressEnt = AddressEnt.builder()
                .city("c")
                .number("2")
                .street("c")
                .build();

        ClientEnt clientEnt = ClientEnt.builder()
                .id(UUID.randomUUID())
                .firstName("a")
                .lastName("b")
                .username("s")
                .password("s")
                .archive(false)
                .balance(200.5)
                .phoneNumber("33")
                .clientTypeEnt(basicClientTypeEnt)
                .addressEnt(addressEnt)
                .clientAccessTypeEnt(ClientAccessTypeEnt.ADMINISTRATORS)
                .build();

        Client client = clientConverter.convert(clientEnt);

        verify(clientTypeConverter).convert(basicClientTypeEnt);
        verify(addressConverter).convert(addressEnt);
        verify(clientAccessTypeConverter).convert(ClientAccessTypeEnt.ADMINISTRATORS);

        assertInstanceOf(Client.class, client);
        assertEquals(clientEnt.getId(), client.getId());
        assertEquals(clientEnt.getFirstName(), client.getFirstName());
        assertEquals(clientEnt.getLastName(), client.getLastName());
        assertEquals(clientEnt.getUsername(), client.getUsername());
        assertEquals(clientEnt.getPassword(), client.getPassword());
        assertEquals(clientEnt.getPhoneNumber(), client.getPhoneNumber());
        assertEquals(clientEnt.isArchive(), client.isArchive());
        assertEquals(clientEnt.getPhoneNumber(), client.getPhoneNumber());
        assertEquals(clientEnt.getBalance(), client.getBalance());
    }
}
