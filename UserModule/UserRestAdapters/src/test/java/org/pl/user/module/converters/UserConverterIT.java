package org.pl.user.module.converters;

import jakarta.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.pl.user.module.model.*;

import java.io.File;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ArquillianExtension.class)
class UserConverterIT {
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
    void convertClientFromDomainToRestModelTest() {
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

        UserRest userRest = userConverter.convert(user);

        assertInstanceOf(UserRest.class, userRest);
        assertEquals(UserAccessTypeRest.ADMINISTRATORS, userRest.getUserAccessType());
        assertEquals(user.getId(), userRest.getId());
        assertEquals(user.getFirstName(), userRest.getFirstName());
        assertEquals(user.getLastName(), userRest.getLastName());
        assertEquals(user.getUsername(), userRest.getUsername());
        assertEquals(user.getPassword(), userRest.getPassword());
        assertEquals(user.getPhoneNumber(), userRest.getPhoneNumber());
        assertEquals(user.getArchive(), userRest.getArchive());
        assertEquals(user.getAddress().getCity(), userRest.getAddress().getCity());
        assertEquals(user.getAddress().getNumber(), userRest.getAddress().getNumber());
        assertEquals(user.getAddress().getStreet(), userRest.getAddress().getStreet());
    }

    @Test
    void convertClientFromRestToDomainModelTest() {
        AddressRest addressRest = AddressRest.builder()
                .city("c")
                .number("2")
                .street("c")
                .build();

        UserRest userRest = UserRest.builder()
                .id(UUID.randomUUID())
                .firstName("a")
                .lastName("b")
                .username("s")
                .password("s")
                .archive(false)
                .phoneNumber("33")
                .address(addressRest)
                .userAccessType(UserAccessTypeRest.ADMINISTRATORS)
                .build();

        User user = userConverter.convert(userRest);

        assertInstanceOf(User.class, user);
        assertEquals(UserAccessType.ADMINISTRATORS, user.getUserAccessType());
        assertEquals(userRest.getId(), user.getId());
        assertEquals(userRest.getFirstName(), user.getFirstName());
        assertEquals(userRest.getLastName(), user.getLastName());
        assertEquals(userRest.getUsername(), user.getUsername());
        assertEquals(userRest.getPassword(), user.getPassword());
        assertEquals(userRest.getPhoneNumber(), user.getPhoneNumber());
        assertEquals(userRest.getArchive(), user.getArchive());
        assertEquals(userRest.getAddress().getCity(), user.getAddress().getCity());
        assertEquals(userRest.getAddress().getNumber(), user.getAddress().getNumber());
        assertEquals(userRest.getAddress().getStreet(), user.getAddress().getStreet());
    }
}