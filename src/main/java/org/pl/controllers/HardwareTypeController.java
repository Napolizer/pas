package org.pl.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.pl.exceptions.RepositoryException;
import org.pl.model.HardwareType;
import org.pl.services.HardwareService;

import java.util.UUID;

@Path("/hardware-type")
public class HardwareTypeController {
    @Inject
    private HardwareService hardwareService; //something tu nie teges

    @GET
    @Path("/id/{id}")
    public HardwareType getHardwareTypeById(@PathParam("id") UUID id) throws RepositoryException {
        return null; //TODO Stworzyc w repo metode do szukania hardwareType po id
    }
}
