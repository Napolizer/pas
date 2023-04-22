package org.pl.gateway.module.converters;

import jakarta.enterprise.context.ApplicationScoped;
import org.pl.gateway.module.model.AddressRest;

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

    public Address convert(AddressRest addressEnt) {
        if (addressEnt == null) return null;
        return Address.builder()
                .city(addressEnt.getCity())
                .number(addressEnt.getNumber())
                .street(addressEnt.getStreet())
                .build();
    }
}
