package org.pl.gateway.module.controllers;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.pl.gateway.module.converters.RepairConverter;
import org.pl.gateway.module.model.RepairRest;
import org.pl.gateway.module.userinterface.repair.ReadRepairUseCases;

import java.util.List;

@Path("/repairs")
@RolesAllowed(value={"EMPLOYEE", "ADMIN"})
public class RepairsController {
    @Inject
    private ReadRepairUseCases readRepairUseCases;

    @Inject
    private RepairConverter repairConverter;
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRepairs() {
        List<RepairRest> repairs = readRepairUseCases.getAllRepairs()
                .stream()
                .map(repairConverter::convert)
                .toList();
        return Response.ok(repairs).build();
    }

}
