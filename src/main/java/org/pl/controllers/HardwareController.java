package org.pl.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
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
    public Hardware getHardwareById(@PathParam("id") UUID id) throws RepositoryException {
        return hardwareService.get(id);
    }
}
