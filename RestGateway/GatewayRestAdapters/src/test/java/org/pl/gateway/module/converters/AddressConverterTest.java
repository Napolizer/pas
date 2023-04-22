package org.pl.gateway.module.converters;

import org.junit.jupiter.api.Test;
import org.pl.gateway.module.converters.AddressConverter;
import org.pl.gateway.module.model.AddressRest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class AddressConverterTest {
    private final AddressConverter addressConverter = new AddressConverter();
    @Test
    void convertAddressFromDomainToEntModelTest() {
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
    void convertAddressFromEntToDomainModelTest() {
        AddressRest addressEnt = AddressRest.builder()
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
