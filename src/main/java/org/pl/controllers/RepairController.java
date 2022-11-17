package org.pl.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
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
    public Repair getRepairById(@PathParam("id") UUID id) throws RepositoryException {
        return repairService.get(id);
    }
}
