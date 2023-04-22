package org.pl.user.module.converters;

import jakarta.enterprise.context.ApplicationScoped;
import org.pl.user.module.model.Address;
import org.pl.user.module.model.AddressRest;

@ApplicationScoped
public class AddressConverter {
    public AddressRest convert(Address address) {
        if (address == null) return null;
        return AddressRest.builder()
                .city(address.getCity())
                .number(address.getNumber())
                .street(address.getStreet())
                .build();
    }

    public Address convert(AddressRest addressRest) {
        if (addressRest == null) return null;
        return Address.builder()
                .city(addressRest.getCity())
                .number(addressRest.getNumber())
                .street(addressRest.getStreet())
                .build();
    }
}
