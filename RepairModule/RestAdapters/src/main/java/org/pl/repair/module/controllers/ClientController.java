package org.pl.repair.module.controllers;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.pl.repair.module.converters.ClientConverter;
import org.pl.repair.module.converters.RepairConverter;
import org.pl.repair.module.model.Client;
import org.pl.repair.module.model.ClientRest;
import org.pl.repair.module.model.RepairRest;
import org.pl.repair.module.model.exceptions.ClientException;
import org.pl.repair.module.model.exceptions.RepositoryException;
import org.pl.repair.module.model.exceptions.RepositoryRestException;
import org.pl.repair.module.providers.ETagProvider;
import org.pl.repair.module.userinterface.client.ReadClientUseCases;
import org.pl.repair.module.userinterface.client.WriteHardwareUseCase;
import org.pl.repair.module.userinterface.repair.ReadRepairUseCases;

import java.util.*;
import java.util.stream.Collectors;

@Path("/client")
public class ClientController {
    @Inject
    private ReadClientUseCases readClientUseCases;
    @Inject
    private WriteHardwareUseCase writeClientUseCases;
    @Inject
    private ReadRepairUseCases readRepairUseCases;
    @Inject
    private ClientConverter clientConverter;
    @Inject
    private RepairConverter repairConverter;
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
            ClientRest client = clientConverter.convert(readClientUseCases.get(uuid));
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
        } catch (RepositoryException | RepositoryRestException e) {
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
                        .map(clientConverter::convert)
                        .toList();
                return Response.ok(clients).build();
            } else {
                ClientRest client = clientConverter.convert(readClientUseCases.getClientByUsername(username));
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
    public Response addClient(@Valid @NotNull ClientRest client) {
        var json = Json.createObjectBuilder();
        try {
            Client createdClient = writeClientUseCases.add(clientConverter.convert(client));
            return Response.status(201).entity(clientConverter.convert(createdClient)).build();
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
    public Response updateClient(ClientRest client, @PathParam("id")String id) {
        var json = Json.createObjectBuilder();
        try {
            String etag = httpHeaders.getHeaderString("If-Match");
            if (etag == null) {
                json.add("error", "Request is missing If-Match header");
                return Response.status(400).entity(json.build()).build();
            }
            UUID uuid = UUID.fromString(id);

            Client existingClient = readClientUseCases.get(uuid);
            if (!eTagProvider.generateETag(existingClient).equals(etag)) {
                json.add("error", "Invalid If-Match signature");
                return Response.status(412).entity(json.build()).build();
            }

            Client updatedClient = writeClientUseCases.updateClient(uuid, clientConverter.convert(client));
            return Response.ok(clientConverter.convert(updatedClient)).build();
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
            Client client = writeClientUseCases.archive(uuid);
            return Response.ok(clientConverter.convert(client)).build();
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
    @RolesAllowed(value={"EMPLOYEE", "ADMIN"})
    public Response activateClient(@PathParam("id")String id) {
        var json = Json.createObjectBuilder();
        try {
            UUID uuid = UUID.fromString(id);
            Client client = writeClientUseCases.dearchive(uuid);
            return Response.ok(clientConverter.convert(client)).build();
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
            List<RepairRest> repairs = readRepairUseCases.getClientsPastRepairs(UUID.fromString(id))
                    .stream()
                    .map(repairConverter::convert)
                    .collect(Collectors.toList());
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
            List<RepairRest> repairs = readRepairUseCases.getClientsPresentRepairs(UUID.fromString(id))
                    .stream()
                    .map(repairConverter::convert)
                    .collect(Collectors.toList());
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
        List<ClientRest> clients = readClientUseCases.getAllClientsFilter(substr)
                .stream()
                .map(clientConverter::convert)
                .toList();
        return Response.ok(clients).build();
    }
}
