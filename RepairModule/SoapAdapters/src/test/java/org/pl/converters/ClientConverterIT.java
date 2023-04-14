package org.pl.converters;

import jakarta.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
    public void convertClientFromDomainToSoapModelTest() {
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

        ClientSoap clientRest = clientConverter.convert(client);

        assertInstanceOf(ClientSoap.class, clientRest);
        assertInstanceOf(BasicSoap.class, clientRest.getClientType());
        assertEquals(ClientAccessTypeSoap.ADMINISTRATORS, clientRest.getClientAccessType());
        assertEquals(client.getId(), clientRest.getId());
        assertEquals(client.getFirstName(), clientRest.getFirstName());
        assertEquals(client.getLastName(), clientRest.getLastName());
        assertEquals(client.getUsername(), clientRest.getUsername());
        assertEquals(client.getPassword(), clientRest.getPassword());
        assertEquals(client.getPhoneNumber(), clientRest.getPhoneNumber());
        assertEquals(client.getArchive(), clientRest.getArchive());
        assertEquals(client.getBalance(), clientRest.getBalance());
        assertEquals(client.getClientType().getType(), clientRest.getClientType().getType());
        assertEquals(client.getClientType().getFactor(), clientRest.getClientType().getFactor());
        assertEquals(client.getClientType().getMaxRepairs(), clientRest.getClientType().getMaxRepairs());
        assertEquals(client.getAddress().getCity(), clientRest.getAddress().getCity());
        assertEquals(client.getAddress().getNumber(), clientRest.getAddress().getNumber());
        assertEquals(client.getAddress().getStreet(), clientRest.getAddress().getStreet());
    }

    @Test
    void convertClientFromSoapToDomainModelTest() {
        ClientTypeSoap basicClientTypeSoap = new BasicSoap();

        AddressSoap addressSoap = new AddressSoap("c", "2", "c");

        ClientSoap clientEnt = new ClientSoap(UUID.randomUUID(), "s", "s", false, 200.5, "a", "b", "33", basicClientTypeSoap,
                addressSoap, ClientAccessTypeSoap.ADMINISTRATORS);

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
        assertEquals(clientEnt.getArchive(), client.getArchive());
        assertEquals(clientEnt.getBalance(), client.getBalance());
        assertEquals(clientEnt.getClientType().getType(), client.getClientType().getType());
        assertEquals(clientEnt.getClientType().getFactor(), client.getClientType().getFactor());
        assertEquals(clientEnt.getClientType().getMaxRepairs(), client.getClientType().getMaxRepairs());
        assertEquals(clientEnt.getAddress().getCity(), client.getAddress().getCity());
        assertEquals(clientEnt.getAddress().getNumber(), client.getAddress().getNumber());
        assertEquals(clientEnt.getAddress().getStreet(), client.getAddress().getStreet());
    }
}
