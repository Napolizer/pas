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
import org.pl.repair.module.converters.HardwareTypeConverter;
import org.pl.repair.module.model.HardwareTypeRest;
import org.pl.repair.module.model.exceptions.RepositoryException;
import org.pl.repair.module.userinterface.hardware.ReadHardwareUseCases;


import java.util.UUID;

@Path("/hardware-type")
@RolesAllowed(value={"USER", "EMPLOYEE", "ADMIN"})
public class HardwareTypeController {
    @Inject
    private ReadHardwareUseCases readHardwareUseCases;
    @Inject
    private HardwareTypeConverter hardwareTypeConverter;

    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHardwareTypeById(@PathParam("id")String id) {
        var json = Json.createObjectBuilder();
        try {
            UUID uuid = UUID.fromString(id);
            HardwareTypeRest hardwareType = hardwareTypeConverter.convert(readHardwareUseCases.getHardwareTypeById(uuid));
            return Response.ok(hardwareType).build();
        } catch (IllegalArgumentException e) {
            json.add("error", "Given id is invalid");
            return Response.status(400).entity(json.build()).build();
        } catch (RepositoryException e) {
            json.add("error", "HardwareType not found");
            return Response.status(404).entity(json.build()).build();
        }
    }
}
