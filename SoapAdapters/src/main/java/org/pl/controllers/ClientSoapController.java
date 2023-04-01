package org.pl.controllers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import org.pl.converters.ClientConverter;
import org.pl.model.BasicSoap;
import org.pl.userinterface.client.ReadClientUseCases;
import org.pl.userinterface.client.WriteClientUseCases;

@WebService(serviceName = "ClientSoapController")
public class ClientSoapController {
    @Inject
    private WriteClientUseCases writeClientUseCases;
    @Inject
    private ReadClientUseCases readClientUseCases;
    @Inject
    private ClientConverter clientConverter;

    @WebMethod
    public BasicSoap hello() {
        System.out.println(clientConverter);
        return new BasicSoap();
    }
}
