package org.pl.converters;

import jakarta.enterprise.context.ApplicationScoped;
import org.pl.model.Address;
import org.pl.model.AddressSoap;

@ApplicationScoped
public class AddressConverter {
    public AddressSoap convert(Address address) {
        if (address == null) return null;
        return new AddressSoap(address.getCity(), address.getNumber(), address.getStreet());
    }

    public Address convert(AddressSoap addressSoap) {
        if (addressSoap == null) return null;
        return Address.builder()
                .city(addressSoap.getCity())
                .number(addressSoap.getNumber())
                .street(addressSoap.getStreet())
                .build();
    }
}
