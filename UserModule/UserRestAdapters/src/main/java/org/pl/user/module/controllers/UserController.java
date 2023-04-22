package org.pl.user.module.controllers;

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
import org.pl.user.module.authentication.UserAuthenticator;
import org.pl.user.module.converters.UserConverter;
import org.pl.user.module.model.User;
import org.pl.user.module.model.UserRest;
import org.pl.user.module.model.UserRestCredentials;
import org.pl.user.module.model.exceptions.RepositoryException;
import org.pl.user.module.model.exceptions.RepositoryRestException;
import org.pl.user.module.model.exceptions.UserException;
import org.pl.user.module.model.exceptions.authentication.InvalidCredentialsException;
import org.pl.user.module.model.exceptions.authentication.UserIsArchiveException;
import org.pl.user.module.model.exceptions.authentication.UserNotFoundException;
import org.pl.user.module.providers.ETagProvider;
import org.pl.user.module.providers.TokenProvider;
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
    @Context
    private HttpHeaders httpHeaders;
    @Inject
    private TokenProvider tokenProvider;
    @Inject
    private UserAuthenticator userAuthenticator;

    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(value={"USER", "EMPLOYEE", "ADMIN"})
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
    @RolesAllowed(value={"USER", "EMPLOYEE", "ADMIN"})
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

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    public Response addUser(@Valid @NotNull UserRest user) {
        var json = Json.createObjectBuilder();
        try {
            User createdUser = writeUserUseCases.add(userConverter.convert(user));
            return Response.status(201).entity(userConverter.convert(createdUser)).build();
        } catch (RepositoryException e) {
            json.add("error", "User already exists");
            return Response.status(400).entity(json.build()).build();
        } catch (UserException e) {
            json.add("error", "Invalid fields");
            return Response.status(400).entity(json.build()).build();
        }
    }

    @PUT
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(UserRest user, @PathParam("id")String id) {
        var json = Json.createObjectBuilder();
        try {
            String etag = httpHeaders.getHeaderString("If-Match");
            if (etag == null) {
                json.add("error", "Request is missing If-Match header");
                return Response.status(400).entity(json.build()).build();
            }
            UUID uuid = UUID.fromString(id);

            User existingUser = readUserUseCases.get(uuid);
            if (!eTagProvider.generateETag(existingUser).equals(etag)) {
                json.add("error", "Invalid If-Match signature");
                return Response.status(412).entity(json.build()).build();
            }

            User updatedUser = writeUserUseCases.updateUser(uuid, userConverter.convert(user));
            return Response.ok(userConverter.convert(updatedUser)).build();
        } catch (IllegalArgumentException e) {
            json.add("error", "Invalid data in request");
            return Response.status(400).entity(json.build()).build();
        } catch (RepositoryException e) {
            json.add("error", "User does not exist");
            return Response.status(404).entity(json.build()).build();
        }
    }

    @PUT
    @Path("/id/{id}/deactivate")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(value={"EMPLOYEE", "ADMIN"})
    public Response deactivateUser(@PathParam("id")String id) {
        var json = Json.createObjectBuilder();
        try {
            UUID uuid = UUID.fromString(id);
            User user = writeUserUseCases.archive(uuid);
            return Response.ok(userConverter.convert(user)).build();
        } catch (IllegalArgumentException e) {
            json.add("error", "Invalid data in request");
            return Response.status(400).entity(json.build()).build();
        } catch (RepositoryException e) {
            if (e.getMessage().equals(RepositoryException.REPOSITORY_ARCHIVE_EXCEPTION)) {
                json.add("error", "User is already deactivated");
                return Response.status(400).entity(json.build()).build();
            } else {
                json.add("error", "User does not exist");
                json.add("exception", e.getMessage());
                return Response.status(404).entity(json.build()).build();
            }
        }
    }

    @PUT
    @Path("/id/{id}/activate")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(value={"EMPLOYEE", "ADMIN"})
    public Response activateUser(@PathParam("id")String id) {
        var json = Json.createObjectBuilder();
        try {
            UUID uuid = UUID.fromString(id);
            User user = writeUserUseCases.dearchive(uuid);
            return Response.ok(userConverter.convert(user)).build();
        } catch (IllegalArgumentException e) {
            json.add("error", "Invalid data in request");
            return Response.status(400).entity(json.build()).build();
        } catch (RepositoryException e) {
            if (e.getMessage().equals(RepositoryException.REPOSITORY_ARCHIVE_EXCEPTION)) {
                json.add("error", "User is already active");
                return Response.status(400).entity(json.build()).build();
            } else {
                json.add("error", "User does not exist");
                return Response.status(404).entity(json.build()).build();
            }
        }
    }

    @GET
    @Path("/filter/{substr}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(value={"EMPLOYEE", "ADMIN"})
    public Response getAllUsersFilter(@PathParam("substr")String substr) {
        List<UserRest> users = readUserUseCases.getAllUsersFilter(substr)
                .stream()
                .map(userConverter::convert)
                .toList();
        return Response.ok(users).build();
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(@NotNull @Valid UserRestCredentials userCredentials) {
        var json = Json.createObjectBuilder();
        try {
            UserRest client = userAuthenticator.authenticate(userCredentials);

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
                User user = writeUserUseCases.updatePassword(uuid, newPassword);
                return Response.ok(userConverter.convert(user)).build();
            } else {
                json.add("error", "Invalid data in request");
                return Response.status(400).entity(json.build()).build();
            }
        } catch (IllegalArgumentException e) {
            json.add("error", "Invalid data in request");
            return Response.status(400).entity(json.build()).build();
        } catch (RepositoryException e) {
            json.add("error", "User does not exist");
            return Response.status(404).entity(json.build()).build();
        }
    }
}
