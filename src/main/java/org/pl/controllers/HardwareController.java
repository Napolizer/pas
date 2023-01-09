package org.pl.controllers;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.bind.JsonbException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.pl.exceptions.HardwareException;
import org.pl.exceptions.RepositoryException;
import org.pl.model.*;
import org.pl.providers.ETagProvider;
import org.pl.repositories.HardwareRepository;
import org.pl.services.HardwareService;
import java.util.List;
import java.util.UUID;

@Path("/hardware")
public class HardwareController {
    @Inject
    private HardwareService hardwareService;
    @Inject
    private ETagProvider eTagProvider;

    HardwareController() {
        hardwareService = new HardwareService(new HardwareRepository());
    }

    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(value={"USER", "EMPLOYEE", "ADMIN"})
    public Response getHardwareById(@PathParam("id")String id) {
        var json = Json.createObjectBuilder();
        try {
            UUID uuid = UUID.fromString(id);
            Hardware hardware = hardwareService.get(uuid);
            if (hardware == null) {
                json.add("error", "Hardware with given id not found");
                return Response.status(404).entity(json.build()).build();
            }
            return Response
                    .ok(hardware)
                    .header("ETag", eTagProvider.generateETag(hardware))
                    .build();
        } catch (IllegalArgumentException | RepositoryException e) {
            json.add("error", e.getMessage());
            return Response.status(400).entity(json.build()).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed(value={"EMPLOYEE", "ADMIN"})
    public Response addHardware(@Valid @NotNull Hardware hardware) {
        var json = Json.createObjectBuilder();
        try {
            Hardware createdHardware = hardwareService.add(hardware);
            return Response.status(201).entity(createdHardware).build();
        } catch (RepositoryException | HardwareException | JsonbException e) {
            json.add("error", e.getMessage());
            return Response.status(400).entity(json).build();
        }
    }

    @PUT
    @Path("id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateHardware(@NotNull Hardware hardware, @PathParam("id")String id) {
        var json = Json.createObjectBuilder();
        try {
            UUID uuid = UUID.fromString(id);
            hardwareService.updateHardware(uuid, hardware);
            return Response.ok(hardware).build();
        } catch (IllegalArgumentException | RepositoryException e) {
            json.add("error", e.getMessage());
            return Response.status(400).entity(json.build()).build();
        }
    }

    @DELETE
    @Path("id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(value={"EMPLOYEE", "ADMIN"})
    public Response deleteHardware(@PathParam("id")String id) {
        var json = Json.createObjectBuilder();
        try {
            UUID uuid = UUID.fromString(id);
            if (!hardwareService.isHardwareArchive(uuid)) {
                hardwareService.archive(uuid);
                json.add("message", "Hardware deleted successfully");
                return Response.ok(json.build()).build();
            } else
                json.add("error", "Hardware is in active repair");
            return Response.status(400).entity(json.build()).build();
        } catch (RepositoryException e) {
            json.add("error", "Hardware does not exist");
            return Response.status(404).entity(json.build()).build();
        }
    }

    @GET
    @Path("/present")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(value={"EMPLOYEE", "ADMIN"})
    public Response getAllPresentHardware() {
        List<Hardware> hardware = hardwareService.getAllPresentHardware();
        return Response.ok(hardware).build();
    }

    @GET
    @Path("/present/filter/{substr}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(value={"EMPLOYEE", "ADMIN"})
    public Response getAllPresentHardwareFilter(@PathParam("substr")String substr) {
        List<Hardware> hardware = hardwareService.getAllPresentHardwareFilter(substr);
        return Response.ok(hardware).build();
    }
}
