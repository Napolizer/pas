package org.pl.user.module.adapter.data.converters;

import jakarta.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.pl.user.module.adapter.data.model.AddressEnt;
import org.pl.user.module.adapter.data.model.UserAccessTypeEnt;
import org.pl.user.module.adapter.data.model.UserEnt;
import org.pl.user.module.model.Address;
import org.pl.user.module.model.User;
import org.pl.user.module.model.UserAccessType;

import java.io.File;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@ExtendWith(ArquillianExtension.class)
public class UserConverterIT {
    @Inject
    private UserConverter userConverter;

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
    void converterInjectionTest() {
        assertThat(userConverter, is(notNullValue()));
    }

    @Test
    void convertClientFromDomainToEntModelTest() {
        Address address = Address.builder()
                .city("c")
                .number("2")
                .street("c")
                .build();

        User user = User.builder()
                .id(UUID.randomUUID())
                .firstName("a")
                .lastName("b")
                .username("s")
                .password("s")
                .archive(false)
                .phoneNumber("33")
                .address(address)
                .userAccessType(UserAccessType.ADMINISTRATORS)
                .build();

        UserEnt userEnt = userConverter.convert(user);

        assertInstanceOf(UserEnt.class, userEnt);
        assertEquals(UserAccessTypeEnt.ADMINISTRATORS, userEnt.getUserAccessType());
        assertEquals(user.getId(), userEnt.getId());
        assertEquals(user.getFirstName(), userEnt.getFirstName());
        assertEquals(user.getLastName(), userEnt.getLastName());
        assertEquals(user.getUsername(), userEnt.getUsername());
        assertEquals(user.getPassword(), userEnt.getPassword());
        assertEquals(user.getPhoneNumber(), userEnt.getPhoneNumber());
        assertEquals(user.getArchive(), userEnt.getArchive());
        assertEquals(user.getAddress().getCity(), userEnt.getAddressEnt().getCity());
        assertEquals(user.getAddress().getNumber(), userEnt.getAddressEnt().getNumber());
        assertEquals(user.getAddress().getStreet(), userEnt.getAddressEnt().getStreet());
    }

    @Test
    void convertClientFromEntToDomainModelTest() {
        AddressEnt addressEnt = AddressEnt.builder()
                .city("c")
                .number("2")
                .street("c")
                .build();

        UserEnt userEnt = UserEnt.builder()
                .id(UUID.randomUUID())
                .firstName("a")
                .lastName("b")
                .username("s")
                .password("s")
                .archive(false)
                .phoneNumber("33")
                .addressEnt(addressEnt)
                .userAccessType(UserAccessTypeEnt.ADMINISTRATORS)
                .build();

        User user = userConverter.convert(userEnt);

        assertInstanceOf(User.class, user);
        assertEquals(UserAccessType.ADMINISTRATORS, user.getUserAccessType());
        assertEquals(userEnt.getId(), user.getId());
        assertEquals(userEnt.getFirstName(), user.getFirstName());
        assertEquals(userEnt.getLastName(), user.getLastName());
        assertEquals(userEnt.getUsername(), user.getUsername());
        assertEquals(userEnt.getPassword(), user.getPassword());
        assertEquals(userEnt.getPhoneNumber(), user.getPhoneNumber());
        assertEquals(userEnt.getArchive(), user.getArchive());
        assertEquals(userEnt.getAddressEnt().getCity(), user.getAddress().getCity());
        assertEquals(userEnt.getAddressEnt().getNumber(), user.getAddress().getNumber());
        assertEquals(userEnt.getAddressEnt().getStreet(), user.getAddress().getStreet());
    }
}
