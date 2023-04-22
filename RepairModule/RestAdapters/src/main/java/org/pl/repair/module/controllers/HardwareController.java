package org.pl.repair.module.controllers;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.bind.JsonbException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.pl.repair.module.converters.HardwareConverter;
import org.pl.repair.module.model.HardwareRest;
import org.pl.repair.module.model.exceptions.HardwareException;
import org.pl.repair.module.model.exceptions.RepositoryException;
import org.pl.repair.module.providers.ETagProvider;
import org.pl.repair.module.userinterface.hardware.ReadHardwareUseCases;
import org.pl.repair.module.userinterface.hardware.WriteHardwareUseCases;

import java.util.List;
import java.util.UUID;

@Path("/hardware")
public class HardwareController {
    @Inject
    private ReadHardwareUseCases readHardwareUseCases;
    @Inject
    private WriteHardwareUseCases writeHardwareUseCases;
    @Inject
    private HardwareConverter hardwareConverter;
    @Inject
    private ETagProvider eTagProvider;
    @Context
    private HttpHeaders httpHeaders;

    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(value={"USER", "EMPLOYEE", "ADMIN"})
    public Response getHardwareById(@PathParam("id")String id) {
        var json = Json.createObjectBuilder();
        try {
            UUID uuid = UUID.fromString(id);
            HardwareRest hardware = hardwareConverter.convert(readHardwareUseCases.get(uuid));
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
    public Response addHardware(@Valid @NotNull HardwareRest hardware) {
        var json = Json.createObjectBuilder();
        try {
            HardwareRest createdHardware = hardwareConverter.convert(writeHardwareUseCases.add(hardwareConverter.convert(hardware)));
            return Response.status(201).entity(createdHardware).build();
        } catch (RepositoryException | HardwareException | JsonbException e) {
            json.add("error", e.getMessage());
            return Response.status(400).entity(json).build();
        }
    }

    @PUT
    @Path("id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateHardware(@NotNull HardwareRest hardware, @PathParam("id")String id) {
        var json = Json.createObjectBuilder();
        try {
            String etag = httpHeaders.getHeaderString("If-Match");
            if (etag == null) {
                json.add("error", "Request is missing If-Match header");
                return Response.status(400).entity(json.build()).build();
            }
            UUID uuid = UUID.fromString(id);

            HardwareRest existingHardware = hardwareConverter.convert(readHardwareUseCases.get(uuid));
            if (!eTagProvider.generateETag(existingHardware).equals(etag)) {
                json.add("error", "Invalid If-Match signature");
                return Response.status(412).entity(json.build()).build();
            }

            HardwareRest updatedHardware = hardwareConverter.convert(writeHardwareUseCases.updateHardware(uuid, hardwareConverter.convert(hardware)));
            return Response.ok(updatedHardware).build();
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
            if (!readHardwareUseCases.isHardwareArchive(uuid)) {
                writeHardwareUseCases.archive(uuid);
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
    @RolesAllowed(value={"USER", "EMPLOYEE", "ADMIN"})
    public Response getAllPresentHardware() {
        List<HardwareRest> hardware = readHardwareUseCases.getAllPresentHardware()
                .stream()
                .map(hardwareConverter::convert)
                .toList();
        return Response.ok(hardware).build();
    }

    @GET
    @Path("/present/filter/{substr}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(value={"USER", "EMPLOYEE", "ADMIN"})
    public Response getAllPresentHardwareFilter(@PathParam("substr")String substr) {
        List<HardwareRest> hardware = readHardwareUseCases.getAllPresentHardwareFilter(substr)
                .stream()
                .map(hardwareConverter::convert)
                .toList();
        return Response.ok(hardware).build();
    }
}
