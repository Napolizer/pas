package org.pl.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Repair;
import org.pl.services.RepairService;

import java.util.UUID;

@Path("repair")
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
}
