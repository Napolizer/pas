package org.pl.user.module.controllers;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.pl.user.module.converters.UserConverter;
import org.pl.user.module.model.UserRest;
import org.pl.user.module.userinterface.user.ReadUserUseCases;

import java.util.List;

@Path("/users")
@RolesAllowed(value = {"EMPLOYEE", "ADMIN"})
public class UsersController {
    @Inject
    private ReadUserUseCases readUserUseCases;
    @Inject
    private UserConverter userConverter;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllClients() {
        List<UserRest> users = readUserUseCases.getAllUsers()
                .stream()
                .map(userConverter::convert)
                .toList();
        return Response.ok(users).build();
    }
}
