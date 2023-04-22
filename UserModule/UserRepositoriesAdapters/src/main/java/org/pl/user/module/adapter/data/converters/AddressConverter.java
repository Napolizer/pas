package org.pl.user.module.adapter.data.converters;

import jakarta.enterprise.context.ApplicationScoped;
import org.pl.user.module.adapter.data.model.AddressEnt;
import org.pl.user.module.model.Address;

@ApplicationScoped
public class AddressConverter {
    public AddressEnt convert(Address address) {
        if (address == null) return null;
        return AddressEnt.builder()
            .city(address.getCity())
            .number(address.getNumber())
            .street(address.getStreet())
            .build();
    }

    public Address convert(AddressEnt addressEnt) {
        if (addressEnt == null) return null;
        return Address.builder()
                .city(addressEnt.getCity())
                .number(addressEnt.getNumber())
                .street(addressEnt.getStreet())
                .build();
    }
}