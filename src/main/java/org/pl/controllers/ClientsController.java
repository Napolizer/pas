package org.pl.controllers;


import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.pl.model.Client;
import org.pl.services.ClientService;

import java.util.List;

@Path("/clients")
@RolesAllowed(value={"EMPLOYEE", "ADMIN"})
public class ClientsController {
    @Inject
    ClientService clientService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllClients() {
        List<Client> clients = clientService.getAllClients();
        return Response.ok(clients).build();
    }

}
