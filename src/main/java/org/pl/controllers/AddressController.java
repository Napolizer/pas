package org.pl.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Address;
import org.pl.model.Client;
import org.pl.services.ClientService;

import java.util.UUID;

@Path("/address")
public class AddressController {
    @Inject
    private ClientService clientService;

    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAddressById(@PathParam("id")String id) {
        try {
            UUID uuid = UUID.fromString(id);
            Client address = clientService.get(uuid); //TODO Stworzyc w repo metode do szukania adresow po id
            return Response.ok(address).build();
        } catch (IllegalArgumentException e) {
            return Response.status(400, "Given id is invalid").build();
        } catch (RepositoryException e) {
            return Response.status(404, "Address not found").build();
        }
    }
}
