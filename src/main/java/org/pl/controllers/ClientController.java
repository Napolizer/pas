package org.pl.controllers;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.pl.authentication.UserAuthenticator;
import org.pl.exceptions.ClientException;
import org.pl.exceptions.RepositoryException;
import org.pl.exceptions.authentication.InvalidCredentialsException;
import org.pl.exceptions.authentication.UserIsArchiveException;
import org.pl.exceptions.authentication.UserNotFoundException;
import org.pl.model.*;
import org.pl.providers.TokenProvider;
import org.pl.services.ClientService;
import org.pl.services.RepairService;

import java.util.*;

@Path("/client")
public class ClientController {
    @Inject
    private ClientService clientService;
    @Inject
    private RepairService repairService;
    @Inject
    private TokenProvider tokenProvider;
    @Inject
    private UserAuthenticator userAuthenticator;

    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(value={"USER", "EMPLOYEE", "ADMIN"})
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
    @RolesAllowed(value={"USER", "EMPLOYEE", "ADMIN"})
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
    @RolesAllowed("ADMIN")
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
    @RolesAllowed(value={"EMPLOYEE", "ADMIN"})
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
    @Path("/id/{id}/change_password")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(value={"USER", "EMPLOYEE", "ADMIN"})
    public Response changePassword(@PathParam("id")String id, String newPassword) {
        var json = Json.createObjectBuilder();
        try {
            UUID uuid = UUID.fromString(id);
            Client client = clientService.updatePassword(uuid, newPassword);
            return Response.ok(client).build();
        } catch (IllegalArgumentException e) {
            json.add("error", "Invalid data in request");
            return Response.status(400).entity(json.build()).build();
        } catch (RepositoryException e) {
            json.add("error", "Client does not exist");
            return Response.status(404).entity(json.build()).build();
        }
    }

    @PUT
    @Path("/id/{id}/activate")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(value={"EMPLOYEE", "ADMIN"})
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
    @RolesAllowed(value={"USER", "EMPLOYEE", "ADMIN"})
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
    @RolesAllowed(value={"USER", "EMPLOYEE", "ADMIN"})
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

    @GET
    @Path("/filter/{substr}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(value={"EMPLOYEE", "ADMIN"})
    public Response getAllClientsFilter(@PathParam("substr")String substr) {
        List<Client> clients = clientService.getAllClientsFilter(substr);
        return Response.ok(clients).build();
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(@NotNull @Valid UserCredentials userCredentials) {
        var json = Json.createObjectBuilder();
        try {
            Client client = userAuthenticator.authenticate(userCredentials);

            String token = tokenProvider.generateToken(client);
            json.add("token", token);
            return Response.ok(json.build()).build();
        } catch (UserNotFoundException e) {
            json.add("error", e.getMessage());
            return Response.status(404).entity(json.build()).build();
        } catch (InvalidCredentialsException e) {
            json.add("error", e.getMessage());
            return Response.status(401).entity(json.build()).build();
        } catch (UserIsArchiveException e) {
            json.add("error", e.getMessage());
            return Response.status(400).entity(json.build()).build();
        }
    }
}
