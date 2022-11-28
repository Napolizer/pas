package org.pl.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.pl.model.Repair;
import org.pl.services.RepairService;

import java.util.List;

@Path("/repairs")
public class RepairsController {
    @Inject
    RepairService repairService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRepairs() {
        List<Repair> repairs = repairService.getAllRepairs();
        return Response.ok(repairs).build();
    }

}
