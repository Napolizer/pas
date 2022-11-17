package org.pl.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.pl.exceptions.ClientException;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Client;
import org.pl.model.Repair;
import org.pl.services.ClientService;
import org.pl.services.RepairService;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Path("/client")
public class ClientController {
    @Inject
    private ClientService clientService;
    @Inject
    private RepairService repairService;

    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClientById(@PathParam("id")String id) {
        try {
            UUID uuid = UUID.fromString(id);
            Client client = clientService.get(uuid);
            return Response.ok(client).build();
        } catch (IllegalArgumentException e) {
            return Response.status(400, "Given id is invalid").build();
        } catch (RepositoryException e) {
            return Response.status(404, "Client not found").build();
        }
    }

    @GET
    @Path("/username/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClientByUsername(@PathParam("username")String username, @QueryParam("strict")String strict) throws RepositoryException {
        try {
            if (Objects.equals(strict, "true")) {
                Client client = clientService.getClientByUsername(username);
                return Response.ok(client).build();
            } else if (Objects.equals(strict, "false")) {
                List<Client> clients = clientService.getClientsByUsername(username);
                return Response.ok(clients).build();
            } else {
                return Response.status(400, "Given query parameter is invalid").build();
            }
        } catch (RepositoryException e) {
            return Response.status(404, "Client not found").build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllClients() {
        List<Client> clients = clientService.getAllCLients();
        return Response.ok(clients).build();
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addClient(Client client) {
        try {
            clientService.add(client);
            return Response.status(201, "Created successfully").build();
        } catch (RepositoryException e) {
            return Response.status(400, "Client already exists").build();
        } catch (ClientException e) {
            return Response.status(400, "Invalid fields").build();
        }
    }

    @PUT
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateClient(Client client, @PathParam("id")String id) {
        try {
            UUID uuid = UUID.fromString(id);
            clientService.updateClient(uuid, client);
            return Response.ok(client).build();
        } catch (IllegalArgumentException e) {
            return Response.status(400, "Invalid data in request").build();
       } catch (RepositoryException e) {
            return Response.status(404, "Client does not exist").build();
        }
    }

    @PUT
    @Path("/id/{id}/deactivate")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deactivateClient(@PathParam("id")String id) {
        try {
            UUID uuid = UUID.fromString(id);
            Client client = clientService.archive(uuid);
            return Response.ok(client).build();
        } catch (IllegalArgumentException e) {
            return Response.status(400, "Invalid data in request").build();
        } catch (RepositoryException e) {
            return Response.status(404, "Client does not exist").build();
        }
    }

    @PUT
    @Path("/id/{id}/activate")
    @Produces(MediaType.APPLICATION_JSON)
    public Response activateClient(@PathParam("id")String id) {
        try {
            UUID uuid = UUID.fromString(id);
            Client client = clientService.dearchivize(uuid);
            return Response.ok(client).build();
        } catch (IllegalArgumentException e) {
            return Response.status(400, "Invalid data in request").build();
        } catch (RepositoryException e) {
            return Response.status(404, "Client does not exist").build();
        }
    }

    @GET
    @Path("/id/{id}/repair/past")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClientsPastRepairs(@PathParam("id")String id) {
        try {
            List<Repair> repairs = repairService.getClientsPastRepairs(UUID.fromString(id));
            return Response.ok("Repairs returned successfully").build();
        } catch (IllegalArgumentException e) {
            return Response.status(400, "Given id is invalid").build();
        }
    }

    @GET
    @Path("/id/{id}/repair/present")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClientsPresentRepairs(@PathParam("id")String id) {
        try {
            List<Repair> repairs = repairService.getClientsPresentRepairs(UUID.fromString(id));
            return Response.ok("Repairs returned successfully").build();
        } catch (IllegalArgumentException e) {
            return Response.status(400, "Given id is invalid").build();
        }
    }
}
