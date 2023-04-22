package org.pl.user.module.controllers;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.pl.user.module.converters.UserConverter;
import org.pl.user.module.model.UserRest;
import org.pl.user.module.model.exceptions.RepositoryException;
import org.pl.user.module.model.exceptions.RepositoryRestException;
import org.pl.user.module.providers.ETagProvider;
import org.pl.user.module.userinterface.user.ReadUserUseCases;
import org.pl.user.module.userinterface.user.WriteUserUseCases;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Path("/user")
public class UserController {
    @Inject
    private ReadUserUseCases readUserUseCases;
    @Inject
    private WriteUserUseCases writeUserUseCases;
    @Inject
    private UserConverter userConverter;
    @Inject
    private ETagProvider eTagProvider;

    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id")String id) {
        var json = Json.createObjectBuilder();
        try {
            UUID uuid = UUID.fromString(id);
            UserRest user = userConverter.convert(readUserUseCases.get(uuid));
            if (user == null) {
                throw new RepositoryRestException("User not found");
            }
            return Response
                    .ok(user)
                    .header("ETag", eTagProvider.generateETag(user))
                    .build();
        } catch (IllegalArgumentException e) {
            json.add("error", "Given id is invalid");
            return Response.status(400).entity(json.build()).build();
        } catch (RepositoryException | RepositoryRestException e) {
            json.add("error", "User not found");
            return Response.status(404).entity(json.build()).build();
        }
    }

    @GET
    @Path("/username/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserByUsername(@PathParam("username")String username, @QueryParam("strict")String strict) {
        var json = Json.createObjectBuilder();
        try {
            if (Objects.equals(strict, "false")) {
                List<UserRest> clients = readUserUseCases.getUsersByUsername(username)
                        .stream()
                        .map(userConverter::convert)
                        .toList();
                return Response.ok(clients).build();
            } else {
                UserRest client = userConverter.convert(readUserUseCases.getUserByUsername(username));
                if (client == null) {
                    throw new RepositoryException("");
                }
                return Response.ok(client).build();
            }
        } catch (RepositoryException e) {
            json.add("error", "User not found");
            return Response.status(404).entity(json.build()).build();
        }
    }
}
