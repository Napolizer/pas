package org.pl.gateway.module.controllers;


import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.pl.gateway.module.converters.ClientConverter;
import org.pl.gateway.module.model.ClientRest;

import java.util.List;

@Path("/clients")
@RolesAllowed(value={"EMPLOYEE", "ADMIN"})
public class ClientsController {
    @Inject
    private ReadClientUseCases readClientUseCases;
    @Inject
    private ClientConverter clientConverter;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllClients() {
        List<ClientRest> clients = readClientUseCases.getAllClients()
                .stream()
                .map(clientConverter::convert)
                .toList();
        return Response.ok(clients).build();
    }

}
