package org.pl.controllers;

import jakarta.inject.Inject;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import org.pl.converters.ClientConverter;
import org.pl.model.ClientSoap;
import org.pl.userinterface.client.ReadClientUseCases;
import org.pl.userinterface.client.WriteClientUseCases;

import java.util.List;
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
    public List<ClientSoap> allClients() {
        System.out.println("readClientUseCases");
        System.out.println(readClientUseCases);
        System.out.println("clientConverter");
        System.out.println(clientConverter);
        return readClientUseCases.getAllClients()
                .stream()
                .map(clientConverter::convert)
                .collect(Collectors.toList());
    }
}
