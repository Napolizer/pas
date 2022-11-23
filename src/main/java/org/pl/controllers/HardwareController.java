package org.pl.controllers;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonValue;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.json.bind.JsonbException;
import jakarta.persistence.Persistence;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.pl.adapters.HardwareTypeAdapter;
import org.pl.exceptions.HardwareException;
import org.pl.exceptions.RepositoryException;
import org.pl.model.*;
import org.pl.repositories.HardwareRepository;
import org.pl.services.HardwareService;
import java.util.List;
import java.util.UUID;

import static org.pl.model.Condition.DUSTY;

@Path("/hardware")
public class HardwareController {
    @Inject
    private HardwareService hardwareService;

    HardwareController() {
        hardwareService = new HardwareService(new HardwareRepository());
    }

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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllHardwares() {
        List<Hardware> hardwares = hardwareService.getAllHardwares();
        return Response.ok(hardwares).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addHardware(@Valid Hardware hardware) {
        try {
            Hardware createdHardware = hardwareService.add(hardware);
            return Response.status(201).entity(createdHardware).build();
        } catch (JsonbException e) {
            return Response.status(400).build();
        } catch (RepositoryException e) {
            return Response.status(400, "Hardware already exists").build();
        } catch (HardwareException e) {
            return Response.status(400, "Invalid fields").build();
        }
    }

    @PUT
    @Path("id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateHardware(Hardware hardware, @PathParam("id")String id) {
        try {
            UUID uuid = UUID.fromString(id);
            hardwareService.updateHardware(uuid, hardware);
            return Response.ok(hardware).build();
        } catch (IllegalArgumentException e) {
            return Response.status(400, "Invalid data in request").build();
        } catch (RepositoryException e) {
            return Response.status(404, "Hardware does not exist").build();
        }
    }

    @DELETE
    @Path("id/{id}")
    public Response deleteHardware(@PathParam("id")String id) {
        try {
            UUID uuid = UUID.fromString(id);
            if (!hardwareService.isHardwareArchive(uuid)) {
                hardwareService.archive(uuid);
                return Response.ok("Deleted Successfully").build();
            } else
                return Response.status(400, "Hardware is in active repair").build();
        } catch (RepositoryException e) {
            return Response.status(404, "Hardware does not exist").build();
        }
    }
}
