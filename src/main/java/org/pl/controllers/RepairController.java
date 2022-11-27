package org.pl.controllers;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.pl.adapters.RepairAdapter;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Hardware;
import org.pl.model.Repair;
import org.pl.services.RepairService;

import java.util.List;
import java.util.UUID;

@Path("/repair")
public class RepairController {
    @Inject
    private RepairService repairService; //something tu nie teges

    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRepairById(@PathParam("id")String id) {
        try {
            UUID uuid = UUID.fromString(id);
            Repair repair = repairService.get(uuid);
            return Response.ok(repair).build();
        } catch (IllegalArgumentException e) {
            return Response.status(400, "Given id is invalid").build();
        } catch (RepositoryException e) {
            return Response.status(404, "Repair not found").build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRepairs() {
        List<Repair> repairs = repairService.getAllRepairs();
        return Response.ok(repairs).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addRepair(@NotNull @Valid Repair repair) {
        var json = Json.createObjectBuilder();
        try {
            Repair createdRepair = repairService.add(repair);
            return Response.status(201).entity(createdRepair).build();
        } catch (RepositoryException e) {
            json.add("error", "Repair already exists");
            return Response.status(400).build();
        }
    }

    @PUT
    @Path("id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateRepair(Repair repair, @PathParam("id")String id) {
        try {
            UUID uuid = UUID.fromString(id);
            repairService.updateRepair(uuid, repair);
            return Response.ok(repair).build();
        } catch (IllegalArgumentException e) {
            return Response.status(400, "Invalid data in request").build();
        } catch (RepositoryException e) {
            return Response.status(404, "Repair does not exist").build();
        }
    }

    @DELETE
    @Path("id/{id}")
    public Response deleteRepair(@PathParam("id")String id) {
        try {
            UUID uuid = UUID.fromString(id);
            if (!repairService.isRepairArchive(uuid)) {
                repairService.archivize(uuid);
                return Response.ok("Deleted Successfully").build();
            } else
                return Response.status(400, "Repair is already deleted").build();
        } catch (RepositoryException e) {
            return Response.status(404, "Repair does not exist").build();
        }
    }
}
