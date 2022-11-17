package org.pl.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Hardware;
import org.pl.services.HardwareService;

import java.util.UUID;

@Path("/hardware")
public class HardwareController {
    @Inject
    private HardwareService hardwareService; //something tu nie teges

    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHardwareById(@PathParam("id")String id) {
        try {
            UUID uuid = UUID.fromString(id);
            Hardware hardware = hardwareService.get(uuid);
            return Response.ok(hardware).build();
        } catch (IllegalArgumentException e) {
            return Response.status(400, "Given id is invalid").build();
        } catch (RepositoryException e) {
            return Response.status(404, "Hardware not found").build();
        }
    }
}
