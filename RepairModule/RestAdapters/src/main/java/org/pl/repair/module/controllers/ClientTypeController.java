package org.pl.repair.module.controllers;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.pl.repair.module.converters.ClientTypeConverter;
import org.pl.repair.module.model.ClientTypeRest;
import org.pl.repair.module.model.exceptions.RepositoryException;
import org.pl.gateway.module.userinterface.client.ReadClientUseCases;

import java.util.UUID;

@Path("/client-type")
@RolesAllowed(value={"USER", "EMPLOYEE", "ADMIN"})
public class ClientTypeController {
    @Inject
    private ReadClientUseCases readClientUseCases;
    @Inject
    private ClientTypeConverter clientTypeConverter;

    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClientTypeById(@PathParam("id")String id) {
        var json = Json.createObjectBuilder();
        try {
            UUID uuid = UUID.fromString(id);
            ClientTypeRest clientType = clientTypeConverter.convert(readClientUseCases.getClientTypeById(uuid));
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
