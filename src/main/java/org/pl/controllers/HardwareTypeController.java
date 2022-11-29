package org.pl.controllers;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Hardware;
import org.pl.model.HardwareType;
import org.pl.services.HardwareService;

import java.util.UUID;

@Path("/hardware-type")
public class HardwareTypeController {
    @Inject
    private HardwareService hardwareService;

    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHardwareTypeById(@PathParam("id")String id) {
        var json = Json.createObjectBuilder();
        try {
            UUID uuid = UUID.fromString(id);
            HardwareType hardwareType = hardwareService.getHardwareTypeById(uuid);
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
