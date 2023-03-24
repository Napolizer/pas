package org.pl.converters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.pl.model.Client;
import org.pl.model.ClientSoap;

@ApplicationScoped
public class ClientConverter {
    @Inject
    private ClientTypeConverter clientTypeConverter;
    @Inject
    private AddressConverter addressConverter;
    @Inject
    private ClientAccessTypeConverter clientAccessTypeConverter;

    public ClientSoap convert(Client client) {
        if (client == null) return null;
        try {
            return new ClientSoap(client.getId(), client.getUsername(), client.getPassword(), client.getArchive(),
                    client.getBalance(), client.getFirstName(), client.getLastName(), client.getPhoneNumber(),
                    clientTypeConverter.convert(client.getClientType()), addressConverter.convert(client.getAddress()),
                    clientAccessTypeConverter.convert(client.getClientAccessType()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Client convert(ClientSoap clientSoap) {
        if (clientSoap == null) return null;
        try {
            return Client.builder()
                    .id(clientSoap.getId())
                    .username(clientSoap.getUsername())
                    .password(clientSoap.getPassword())
                    .archive(clientSoap.getArchive())
                    .balance(clientSoap.getBalance())
                    .firstName(clientSoap.getFirstName())
                    .lastName(clientSoap.getLastName())
                    .phoneNumber(clientSoap.getPhoneNumber())
                    .clientType(clientTypeConverter.convert(clientSoap.getClientType()))
                    .address(addressConverter.convert(clientSoap.getAddress()))
                    .clientAccessType(clientAccessTypeConverter.convert(clientSoap.getClientAccessType()))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
