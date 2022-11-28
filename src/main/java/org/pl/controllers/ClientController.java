package org.pl.controllers;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.pl.exceptions.ClientException;
import org.pl.exceptions.RepositoryException;
import org.pl.model.*;
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
        var json = Json.createObjectBuilder();
        try {
            UUID uuid = UUID.fromString(id);
            Client client = clientService.get(uuid);
            if (client == null) {
                throw new RepositoryException("");
            }
            return Response.ok(client).build();
        } catch (IllegalArgumentException e) {
            json.add("error", "Given id is invalid");
            return Response.status(400).entity(json.build()).build();
        } catch (RepositoryException e) {
            json.add("error", "Client not found");
            return Response.status(404).entity(json.build()).build();
        }
    }

    @GET
    @Path("/username/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClientByUsername(@PathParam("username")String username, @QueryParam("strict")String strict) throws RepositoryException {
        var json = Json.createObjectBuilder();
        try {
            if (Objects.equals(strict, "false")) {
                List<Client> clients = clientService.getClientsByUsername(username);
                return Response.ok(clients).build();
            } else {
                Client client = clientService.getClientByUsername(username);
                if (client == null) {
                    throw new RepositoryException("");
                }
                return Response.ok(client).build();
            }
        } catch (RepositoryException e) {
            json.add("error", "Client not found");
            return Response.status(404).entity(json.build()).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addClient(@Valid @NotNull Client client) {
        var json = Json.createObjectBuilder();
        try {
            Client createdClient = clientService.add(client);
            return Response.status(201).entity(createdClient).build();
        } catch (RepositoryException e) {
            json.add("error", "Client already exists");
            return Response.status(400).entity(json.build()).build();
        } catch (ClientException e) {
            json.add("error", "Invalid fields");
            return Response.status(400).entity(json.build()).build();
        }
    }

    @PUT
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateClient(Client client, @PathParam("id")String id) {
        var json = Json.createObjectBuilder();
        try {
            UUID uuid = UUID.fromString(id);
            Client updatedClient = clientService.updateClient(uuid, client);
            return Response.ok(updatedClient).build();
        } catch (IllegalArgumentException e) {
            json.add("error", "Invalid data in request");
            return Response.status(400).entity(json.build()).build();
       } catch (RepositoryException e) {
            json.add("error", "Client does not exist");
            return Response.status(404).entity(json.build()).build();
        }
    }

    @PUT
    @Path("/id/{id}/deactivate")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deactivateClient(@PathParam("id")String id) {
        var json = Json.createObjectBuilder();
        try {
            UUID uuid = UUID.fromString(id);
            Client client = clientService.archive(uuid);
            return Response.ok(client).build();
        } catch (IllegalArgumentException e) {
            json.add("error", "Invalid data in request");
            return Response.status(400).entity(json.build()).build();
        } catch (RepositoryException e) {
            if (e.getMessage().equals(RepositoryException.REPOSITORY_ARCHIVE_EXCEPTION)) {
                json.add("error", "Client is already deactivated");
                return Response.status(400).entity(json.build()).build();
            } else {
                json.add("error", "Client does not exist");
                json.add("exception", e.getMessage());
                return Response.status(404).entity(json.build()).build();
            }
        }
    }

    @PUT
    @Path("/id/{id}/activate")
    @Produces(MediaType.APPLICATION_JSON)
    public Response activateClient(@PathParam("id")String id) {
        var json = Json.createObjectBuilder();
        try {
            UUID uuid = UUID.fromString(id);
            Client client = clientService.dearchive(uuid);
            return Response.ok(client).build();
        } catch (IllegalArgumentException e) {
            json.add("error", "Invalid data in request");
            return Response.status(400).entity(json.build()).build();
        } catch (RepositoryException e) {
            if (e.getMessage().equals(RepositoryException.REPOSITORY_ARCHIVE_EXCEPTION)) {
                json.add("error", "Client is already active");
                return Response.status(400).entity(json.build()).build();
            } else {
                json.add("error", "Client does not exist");
                return Response.status(404).entity(json.build()).build();
            }
        }
    }

    @GET
    @Path("/id/{id}/repair/past")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClientsPastRepairs(@PathParam("id")String id) {
        var json = Json.createObjectBuilder();
        try {
            List<Repair> repairs = repairService.getClientsPastRepairs(UUID.fromString(id));
            return Response.ok(repairs).build();
        } catch (IllegalArgumentException e) {
            json.add("error", "Given client id is invalid");
            return Response.status(400).entity(json.build()).build();
        } catch (RepositoryException e) {
            json.add("error", "User with given id was not found");
            return Response.status(404).entity(json.build()).build();
        }
    }

    @GET
    @Path("/id/{id}/repair/present")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClientsPresentRepairs(@PathParam("id")String id) {
        var json = Json.createObjectBuilder();
        try {
            List<Repair> repairs = repairService.getClientsPresentRepairs(UUID.fromString(id));
            return Response.ok(repairs).build();
        } catch (IllegalArgumentException e) {
            json.add("error", "Given client id is invalid");
            return Response.status(400).entity(json.build()).build();
        } catch (RepositoryException e) {
            json.add("error", "User with given id was not found");
            return Response.status(404).entity(json.build()).build();
        }
    }
}
