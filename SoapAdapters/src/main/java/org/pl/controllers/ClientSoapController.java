package org.pl.controllers;

import jakarta.inject.Inject;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import org.pl.model.BasicSoap;
import org.pl.userinterface.client.ReadClientUseCases;
import org.pl.userinterface.client.WriteClientUseCases;

@WebService(serviceName = "ClientAPI")
public class ClientSoapController {
    @Inject
    private WriteClientUseCases writeClientUseCases;
    @Inject
    private ReadClientUseCases readClientUseCases;

    @WebMethod
    public BasicSoap hello() {
        return new BasicSoap();
    }
}
