package org.pl.controllers;

import jakarta.inject.Inject;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import org.pl.converters.ClientConverter;
import org.pl.model.ClientSoap;
import org.pl.model.exceptions.ClientException;
import org.pl.model.exceptions.RepositoryException;
import org.pl.userinterface.client.ReadClientUseCases;
import org.pl.userinterface.client.WriteClientUseCases;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@WebService(serviceName = "ClientSoapController")
public class ClientSoapController {
    @Inject
    private WriteClientUseCases writeClientUseCases;
    @Inject
    private ReadClientUseCases readClientUseCases;
    @Inject
    private ClientConverter clientConverter;

    @WebMethod
    public List<ClientSoap> getAllClients() {
        return readClientUseCases.getAllClients()
                .stream()
                .map(clientConverter::convert)
                .collect(Collectors.toList());
    }

    @WebMethod
    public ClientSoap getClientById(UUID id) throws RepositoryException {
        return clientConverter.convert(readClientUseCases.get(id));
    }

    @WebMethod
    public ClientSoap createClient(ClientSoap clientSoap) throws RepositoryException, ClientException {
        return clientConverter.convert(writeClientUseCases.add(clientConverter.convert(clientSoap)));
    }

    @WebMethod
    public ClientSoap updateClient(ClientSoap clientSoap) throws RepositoryException {
        return clientConverter.convert(writeClientUseCases.updateClient(clientSoap.getId(), clientConverter.convert(clientSoap)));
    }

    @WebMethod
    public void deleteClient(UUID id) throws RepositoryException {
        writeClientUseCases.archive(id);
    }
}
