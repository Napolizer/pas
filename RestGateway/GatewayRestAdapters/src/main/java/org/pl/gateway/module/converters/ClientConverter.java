package org.pl.gateway.module.converters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.pl.gateway.module.model.Client;
import org.pl.gateway.module.model.ClientRest;

@ApplicationScoped
public class ClientConverter {
    @Inject
    private ClientTypeConverter clientTypeConverter;
    @Inject
    private AddressConverter addressConverter;
    @Inject
    private ClientAccessTypeConverter clientAccessTypeConverter;

    public ClientRest convert(Client client) {
        if (client == null) return null;
        try {
            return ClientRest
                    .builder()
                    .id(client.getId())
                    .username(client.getUsername())
                    .password(client.getPassword())
                    .archive(client.getArchive())
                    .balance(client.getBalance())
                    .firstName(client.getFirstName())
                    .lastName(client.getLastName())
                    .phoneNumber(client.getPhoneNumber())
                    .clientType(clientTypeConverter.convert(client.getClientType()))
                    .address(addressConverter.convert(client.getAddress()))
                    .clientAccessType(clientAccessTypeConverter.convert(client.getClientAccessType()))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Client convert(ClientRest clientEnt) {
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
                    .clientType(clientTypeConverter.convert(clientEnt.getClientType()))
                    .address(addressConverter.convert(clientEnt.getAddress()))
                    .clientAccessType(clientAccessTypeConverter.convert(clientEnt.getClientAccessType()))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
