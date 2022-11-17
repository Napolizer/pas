package org.pl.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.pl.exceptions.RepositoryException;
import org.pl.model.ClientType;
import org.pl.services.ClientService;

import java.util.UUID;

@Path("/client-type")
public class ClientTypeController {
    @Inject
    private ClientService clientService; //something tu nie teges

    @GET
    @Path("/id/{id}")
    public ClientType getClientTypeById(@PathParam("id") UUID id) throws RepositoryException {
        return null; //TODO Stworzyc w repo metode do szukania clientTYpe po id
    }
}
