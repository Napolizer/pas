package org.pl.adapter.data.converters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.pl.adapter.data.model.*;
import org.pl.model.Client;

@ApplicationScoped
public class ClientConverter {
    @Inject
    private ClientTypeConverter clientTypeConverter;
    @Inject
    private AddressConverter addressConverter;
    @Inject
    private ClientAccessTypeConverter clientAccessTypeConverter;

    public ClientEnt convert(Client client) {
        if (client == null) return null;
        try {
            return ClientEnt
                    .builder()
                    .id(client.getId())
                    .username(client.getUsername())
                    .password(client.getPassword())
                    .archive(client.getArchive())
                    .balance(client.getBalance())
                    .firstName(client.getFirstName())
                    .lastName(client.getLastName())
                    .phoneNumber(client.getPhoneNumber())
                    .clientTypeEnt(clientTypeConverter.convert(client.getClientType()))
                    .addressEnt(addressConverter.convert(client.getAddress()))
                    .clientAccessTypeEnt(clientAccessTypeConverter.convert(client.getClientAccessType()))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Client convert(ClientEnt clientEnt) {
        if (clientEnt == null) return null;
        try {
            return Client.builder()
                    .id(clientEnt.getId())
                    .username(clientEnt.getUsername())
                    .password(clientEnt.getPassword())
                    .archive(clientEnt.getArchive())
                    .balance(clientEnt.getBalance())
                    .firstName(clientEnt.getFirstName())
                    .lastName(clientEnt.getLastName())
                    .phoneNumber(clientEnt.getPhoneNumber())
                    .clientType(clientTypeConverter.convert(clientEnt.getClientTypeEnt()))
                    .address(addressConverter.convert(clientEnt.getAddressEnt()))
                    .clientAccessType(clientAccessTypeConverter.convert(clientEnt.getClientAccessTypeEnt()))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
