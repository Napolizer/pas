package org.pl.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Client;
import org.pl.services.ClientService;

import java.util.UUID;

@Path("/client")
public class ClientController {
    @Inject
    private ClientService clientService; //something tu nie teges

    @GET
    @Path("/id/{id}")
    public Client getClientById(@PathParam("id")UUID id) throws RepositoryException {
        return clientService.get(id);
    }
}
