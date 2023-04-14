package org.pl.adapter.data.converters;

import jakarta.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.pl.adapter.data.model.*;
import org.pl.model.*;

import java.io.File;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@ExtendWith(ArquillianExtension.class)
public class ClientConverterIT {
    @Inject
    private ClientConverter clientConverter;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "org.pl")
                .addPackages(true, "org.hamcrest")
                .addAsResource(new File("src/main/resources/"),"")
                .addAsResource(new File("target/classes/META-INF/"), "META-INF/")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void converterInjectionTest() {
        assertThat(clientConverter, is(notNullValue()));
    }

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

        assertInstanceOf(ClientEnt.class, clientEnt);
        assertInstanceOf(BasicEnt.class, clientEnt.getClientTypeEnt());
        assertEquals(ClientAccessTypeEnt.ADMINISTRATORS, clientEnt.getClientAccessTypeEnt());
        assertEquals(client.getId(), clientEnt.getId());
        assertEquals(client.getFirstName(), clientEnt.getFirstName());
        assertEquals(client.getLastName(), clientEnt.getLastName());
        assertEquals(client.getUsername(), clientEnt.getUsername());
        assertEquals(client.getPassword(), clientEnt.getPassword());
        assertEquals(client.getPhoneNumber(), clientEnt.getPhoneNumber());
        assertEquals(client.getArchive(), clientEnt.isArchive());
        assertEquals(client.getBalance(), clientEnt.getBalance());
        assertEquals(client.getClientType().getType(), clientEnt.getClientTypeEnt().getType());
        assertEquals(client.getClientType().getFactor(), clientEnt.getClientTypeEnt().getFactor());
        assertEquals(client.getClientType().getMaxRepairs(), clientEnt.getClientTypeEnt().getMaxRepairs());
        assertEquals(client.getAddress().getCity(), clientEnt.getAddressEnt().getCity());
        assertEquals(client.getAddress().getNumber(), clientEnt.getAddressEnt().getNumber());
        assertEquals(client.getAddress().getStreet(), clientEnt.getAddressEnt().getStreet());
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

        assertInstanceOf(Client.class, client);
        assertInstanceOf(Basic.class, client.getClientType());
        assertEquals(ClientAccessType.ADMINISTRATORS, client.getClientAccessType());
        assertEquals(clientEnt.getId(), client.getId());
        assertEquals(clientEnt.getFirstName(), client.getFirstName());
        assertEquals(clientEnt.getLastName(), client.getLastName());
        assertEquals(clientEnt.getUsername(), client.getUsername());
        assertEquals(clientEnt.getPassword(), client.getPassword());
        assertEquals(clientEnt.getPhoneNumber(), client.getPhoneNumber());
        assertEquals(clientEnt.isArchive(), client.getArchive());
        assertEquals(clientEnt.getBalance(), client.getBalance());
        assertEquals(clientEnt.getClientTypeEnt().getType(), client.getClientType().getType());
        assertEquals(clientEnt.getClientTypeEnt().getFactor(), client.getClientType().getFactor());
        assertEquals(clientEnt.getClientTypeEnt().getMaxRepairs(), client.getClientType().getMaxRepairs());
        assertEquals(clientEnt.getAddressEnt().getCity(), client.getAddress().getCity());
        assertEquals(clientEnt.getAddressEnt().getNumber(), client.getAddress().getNumber());
        assertEquals(clientEnt.getAddressEnt().getStreet(), client.getAddress().getStreet());
    }
}
