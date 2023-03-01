package org.pl.controllers;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.pl.exceptions.RepositoryException;
import org.pl.model.ClientType;
import org.pl.services.ClientService;

import java.util.UUID;

@Path("/client-type")
@RolesAllowed(value={"USER", "EMPLOYEE", "ADMIN"})
public class ClientTypeController {
    @Inject
    private ClientService clientService;

    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClientTypeById(@PathParam("id")String id) {
        var json = Json.createObjectBuilder();
        try {
            UUID uuid = UUID.fromString(id);
            ClientType clientType = clientService.getClientTypeById(uuid);
            return Response.ok(clientType).build();
        } catch (IllegalArgumentException e) {
            json.add("error", "Given id is invalid");
            return Response.status(400).entity(json.build()).build();
        } catch (RepositoryException e) {
            json.add("error", "ClientType not found");
            return Response.status(404).entity(json.build()).build();
        }
    }
}
