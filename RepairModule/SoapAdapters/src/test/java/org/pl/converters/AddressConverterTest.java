package org.pl.converters;

import org.junit.jupiter.api.Test;
import org.pl.model.Address;
import org.pl.model.AddressSoap;

import static org.junit.jupiter.api.Assertions.*;

public class AddressConverterTest {
    private final AddressConverter addressConverter = new AddressConverter();
    @Test
    void convertAddressFromDomainToSoapModelTest() {
        Address address = Address.builder()
                .city("city")
                .number("7")
                .street("street")
                .build();
        AddressSoap addressRest = addressConverter.convert(address);
        assertEquals(address.getCity(), addressRest.getCity());
        assertEquals(address.getNumber(), addressRest.getNumber());
        assertEquals(address.getStreet(), addressRest.getStreet());
        assertInstanceOf(AddressSoap.class, addressRest);
    }

    @Test
    void convertAddressFromSoapToDomainModelTest() {
        AddressSoap addressSoap = new AddressSoap("city", "7", "street");
        Address address = addressConverter.convert(addressSoap);
        assertEquals(addressSoap.getCity(), address.getCity());
        assertEquals(addressSoap.getNumber(), address.getNumber());
        assertEquals(addressSoap.getStreet(), address.getStreet());
        assertInstanceOf(Address.class, address);
    }
}
