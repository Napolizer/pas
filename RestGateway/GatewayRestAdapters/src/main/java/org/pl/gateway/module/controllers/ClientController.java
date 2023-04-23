package org.pl.gateway.module.controllers;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.pl.gateway.module.authentication.UserAuthenticator;
import org.pl.gateway.module.model.ClientRest;
import org.pl.gateway.module.model.RepairRest;
import org.pl.gateway.module.model.UserRestCredentials;
import org.pl.gateway.module.model.exceptions.ClientRestException;
import org.pl.gateway.module.model.exceptions.RepositoryRestException;
import org.pl.gateway.module.model.exceptions.authentication.InvalidCredentialsException;
import org.pl.gateway.module.model.exceptions.authentication.UserIsArchiveException;
import org.pl.gateway.module.model.exceptions.authentication.UserNotFoundException;
import org.pl.gateway.module.providers.ETagProvider;
import org.pl.gateway.module.providers.TokenProvider;
import org.pl.gateway.module.ports.userinterface.client.ReadClientUseCases;
import org.pl.gateway.module.ports.userinterface.client.WriteClientUseCases;
import org.pl.gateway.module.ports.userinterface.repair.ReadRepairUseCases;

import java.util.*;
import java.util.stream.Collectors;

@Path("/client")
public class ClientController {
    @Inject
    private ReadClientUseCases readClientUseCases;
    @Inject
    private WriteClientUseCases writeClientUseCases;
    @Inject
    private ReadRepairUseCases readRepairUseCases;
    @Inject
    private TokenProvider tokenProvider;
    @Inject
    private UserAuthenticator userAuthenticator;
    @Inject
    private ETagProvider eTagProvider;
    @Context
    private HttpHeaders httpHeaders;

    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(value={"USER", "EMPLOYEE", "ADMIN"})
    public Response getClientById(@PathParam("id")String id) {
        var json = Json.createObjectBuilder();
        try {
            UUID uuid = UUID.fromString(id);
            ClientRest client = readClientUseCases.get(uuid);
            if (client == null) {
                throw new RepositoryRestException("");
            }
            return Response
                    .ok(client)
                    .header("ETag", eTagProvider.generateETag(client))
                    .build();
        } catch (IllegalArgumentException e) {
            json.add("error", "Given id is invalid");
            return Response.status(400).entity(json.build()).build();
        } catch (RepositoryRestException e) {
            json.add("error", "Client not found");
            return Response.status(404).entity(json.build()).build();
        }
    }

    @GET
    @Path("/username/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(value={"USER", "EMPLOYEE", "ADMIN"})
    public Response getClientByUsername(@PathParam("username")String username, @QueryParam("strict")String strict) {
        var json = Json.createObjectBuilder();
        try {
            if (Objects.equals(strict, "false")) {
                List<ClientRest> clients = readClientUseCases.getClientsByUsername(username)
                        .stream()
                        .toList();
                return Response.ok(clients).build();
            } else {
                ClientRest client = readClientUseCases.getClientByUsername(username);
                if (client == null) {
                    throw new RepositoryRestException("");
                }
                return Response.ok(client).build();
            }
        } catch (RepositoryRestException e) {
            json.add("error", "Client not found");
            return Response.status(404).entity(json.build()).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    public Response addClient(@Valid @NotNull ClientRest client) {
        var json = Json.createObjectBuilder();
        try {
            ClientRest createdClient = writeClientUseCases.add(client);
            return Response.status(201).entity(createdClient).build();
        } catch (RepositoryRestException e) {
            json.add("error", "Client already exists");
            return Response.status(400).entity(json.build()).build();
        } catch (ClientRestException e) {
            json.add("error", "Invalid fields");
            return Response.status(400).entity(json.build()).build();
        }
    }

    @PUT
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateClient(ClientRest client, @PathParam("id")String id) {
        var json = Json.createObjectBuilder();
        try {
            String etag = httpHeaders.getHeaderString("If-Match");
            if (etag == null) {
                json.add("error", "Request is missing If-Match header");
                return Response.status(400).entity(json.build()).build();
            }
            UUID uuid = UUID.fromString(id);

            ClientRest existingClient = readClientUseCases.get(uuid);
            if (!eTagProvider.generateETag(existingClient).equals(etag)) {
                json.add("error", "Invalid If-Match signature");
                return Response.status(412).entity(json.build()).build();
            }

            ClientRest updatedClient = writeClientUseCases.updateClient(uuid, client);
            return Response.ok(updatedClient).build();
        } catch (IllegalArgumentException e) {
            json.add("error", "Invalid data in request");
            return Response.status(400).entity(json.build()).build();
       } catch (RepositoryRestException e) {
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
            ClientRest client = writeClientUseCases.archive(uuid);
            return Response.ok(client).build();
        } catch (IllegalArgumentException e) {
            json.add("error", "Invalid data in request");
            return Response.status(400).entity(json.build()).build();
        } catch (RepositoryRestException e) {
            if (e.getMessage().equals(RepositoryRestException.REPOSITORY_ARCHIVE_EXCEPTION)) {
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
    public Response changePassword(@PathParam("id")String id,@NotNull JsonValue jsonValue) {
        var json = Json.createObjectBuilder();
        try {
            JsonObject body = jsonValue.asJsonObject();
            UUID uuid = UUID.fromString(id);
            if (body.containsKey("newPassword")) {
                String newPassword = body.getString("newPassword");
                ClientRest client = writeClientUseCases.updatePassword(uuid, newPassword);
                return Response.ok(client).build();
            } else {
                json.add("error", "Invalid data in request");
                return Response.status(400).entity(json.build()).build();
            }
        } catch (IllegalArgumentException e) {
            json.add("error", "Invalid data in request");
            return Response.status(400).entity(json.build()).build();
        } catch (RepositoryRestException e) {
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
            ClientRest client = writeClientUseCases.dearchive(uuid);
            return Response.ok(client).build();
        } catch (IllegalArgumentException e) {
            json.add("error", "Invalid data in request");
            return Response.status(400).entity(json.build()).build();
        } catch (RepositoryRestException e) {
            if (e.getMessage().equals(RepositoryRestException.REPOSITORY_ARCHIVE_EXCEPTION)) {
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
            List<RepairRest> repairs = readRepairUseCases.getClientsPastRepairs(UUID.fromString(id))
                    .stream()
                    .collect(Collectors.toList());
            return Response.ok(repairs).build();
        } catch (IllegalArgumentException e) {
            json.add("error", "Given client id is invalid");
            return Response.status(400).entity(json.build()).build();
        } catch (RepositoryRestException e) {
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
            List<RepairRest> repairs = readRepairUseCases.getClientsPresentRepairs(UUID.fromString(id))
                    .stream()
                    .collect(Collectors.toList());
            return Response.ok(repairs).build();
        } catch (IllegalArgumentException e) {
            json.add("error", "Given client id is invalid");
            return Response.status(400).entity(json.build()).build();
        } catch (RepositoryRestException e) {
            json.add("error", "User with given id was not found");
            return Response.status(404).entity(json.build()).build();
        }
    }

    @GET
    @Path("/filter/{substr}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(value={"EMPLOYEE", "ADMIN"})
    public Response getAllClientsFilter(@PathParam("substr")String substr) {
        List<ClientRest> clients = readClientUseCases.getAllClientsFilter(substr)
                .stream()
                .toList();
        return Response.ok(clients).build();
    }
}
