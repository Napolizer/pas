package org.pl.controllers;

import jakarta.annotation.Resource;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.hibernate.service.spi.InjectService;
import org.pl.adapters.RepairAdapter;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Hardware;
import org.pl.model.Repair;
import org.pl.services.RepairService;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Path("/repair")
public class RepairController {
    @Inject
    private RepairService repairService;
    @Inject
    private Principal principal;
    @Context
    private SecurityContext securityContext;

    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(value={"USER", "EMPLOYEE", "ADMIN"})
    public Response getRepairById(@PathParam("id")String id) {
        var json = Json.createObjectBuilder();
        try {
            UUID uuid = UUID.fromString(id);
            Repair repair = repairService.get(uuid);
            return Response.ok(repair).build();
        } catch (IllegalArgumentException e) {
            json.add("error", "Given id is invalid");
            return Response.status(400).entity(json.build()).build();
        } catch (RepositoryException e) {
            json.add("error", "Repair not found");
            return Response.status(404).entity(json.build()).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed(value={"USER", "EMPLOYEE", "ADMIN"})
    public Response addRepair(@NotNull @Valid Repair repair) {
        if (securityContext.isUserInRole("USER")) {
            if (!repair.getClient().getUsername().equals(principal.getName())) {
                return Response.status(403).build();
            }
        }
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
    public Response updateRepair(@NotNull Repair repair, @PathParam("id")String id) {
        var json = Json.createObjectBuilder();
        try {
            UUID uuid = UUID.fromString(id);
            Repair updatedRepair = repairService.updateRepair(uuid, repair);
            return Response.ok(updatedRepair).build();
        } catch (IllegalArgumentException e) {
            json.add("error", "Invalid data in request");
            return Response.status(400).entity(json.build()).build();
        } catch (RepositoryException e) {
            json.add("error", "Repair does not exist");
            return Response.status(404).entity(json.build()).build();
        }
    }

    @DELETE
    @Path("id/{id}")
    @RolesAllowed(value={"USER", "EMPLOYEE", "ADMIN"})
    public Response deleteRepair(@PathParam("id")String id) {
        var json = Json.createObjectBuilder();
        try {
            UUID uuid = UUID.fromString(id);
            if (!repairService.isRepairArchive(uuid)) {
                Repair deletedRepair = repairService.repair(uuid);
                return Response.ok(deletedRepair).build();
            } else {
                json.add("error", "Repair is already archive");
                return Response.status(400).entity(json.build()).build();
            }
        } catch (RepositoryException e) {
            json.add("error", "Given request is invalid");
            return Response.status(400).entity(json.build()).build();
        } catch (Exception e) {
            json.add("error", "Repair does not exist");
            return Response.status(404).entity(json.build()).build();
        }
    }
}
