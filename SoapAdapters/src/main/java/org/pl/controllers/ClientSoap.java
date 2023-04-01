package org.pl.controllers;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import org.pl.model.BasicSoap;

@WebService(serviceName = "ClientSoapController")
public interface ClientSoap {
    @WebMethod
    public BasicSoap hello();
}
