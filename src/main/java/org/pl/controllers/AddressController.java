package org.pl.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Address;
import org.pl.services.ClientService;

import java.util.UUID;

@Path("/address")
public class AddressController {
    @Inject
    private ClientService clientService; //something tu nie teges

    @GET
    @Path("/id/{id}")
    public Address getAddressById(@PathParam("id") UUID id) throws RepositoryException {
        return null; //TODO Stworzyc w repo metode do szukania adresow po id
    }
}
