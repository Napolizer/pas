package org.pl.user.module.converters;

import org.junit.jupiter.api.Test;
import org.pl.user.module.model.Address;
import org.pl.user.module.model.AddressRest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class AddressConverterTest {
    private final AddressConverter addressConverter = new AddressConverter();
    @Test
    void convertAddressFromDomainToRestModelTest() {
        Address address = Address.builder()
                .city("city")
                .number("7")
                .street("street")
                .build();
        AddressRest addressRest = addressConverter.convert(address);
        assertEquals(address.getCity(), addressRest.getCity());
        assertEquals(address.getNumber(), addressRest.getNumber());
        assertEquals(address.getStreet(), addressRest.getStreet());
        assertInstanceOf(AddressRest.class, addressRest);
    }

    @Test
    void convertAddressFromRestToDomainModelTest() {
        AddressRest addressRest = AddressRest.builder()
                .city("city")
                .number("7")
                .street("street")
                .build();
        Address address = addressConverter.convert(addressRest);
        assertEquals(addressRest.getCity(), address.getCity());
        assertEquals(addressRest.getNumber(), address.getNumber());
        assertEquals(addressRest.getStreet(), address.getStreet());
        assertInstanceOf(Address.class, address);
    }
}