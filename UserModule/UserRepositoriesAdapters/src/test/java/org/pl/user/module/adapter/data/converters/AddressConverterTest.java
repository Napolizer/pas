package org.pl.user.module.adapter.data.converters;

import org.junit.jupiter.api.Test;
import org.pl.user.module.adapter.data.model.AddressEnt;
import org.pl.user.module.model.Address;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class AddressConverterTest {
    private final AddressConverter addressConverter = new AddressConverter();
    @Test
    void convertAddressFromDomainToEntModelTest() {
        Address address = Address.builder()
                .city("city")
                .number("7")
                .street("street")
                .build();
        AddressEnt addressEnt = addressConverter.convert(address);
        assertEquals(address.getCity(), addressEnt.getCity());
        assertEquals(address.getNumber(), addressEnt.getNumber());
        assertEquals(address.getStreet(), addressEnt.getStreet());
        assertInstanceOf(AddressEnt.class, addressEnt);
    }

    @Test
    void convertAddressFromEntToDomainModelTest() {
        AddressEnt addressEnt = AddressEnt.builder()
                .city("city")
                .number("7")
                .street("street")
                .build();
        Address address = addressConverter.convert(addressEnt);
        assertEquals(addressEnt.getCity(), address.getCity());
        assertEquals(addressEnt.getNumber(), address.getNumber());
        assertEquals(addressEnt.getStreet(), address.getStreet());
        assertInstanceOf(Address.class, address);
    }
}