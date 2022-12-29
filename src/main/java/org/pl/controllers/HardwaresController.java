package org.pl.controllers;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.pl.model.Hardware;
import org.pl.services.HardwareService;

import java.util.List;

@Path("/hardwares")
@RolesAllowed(value={"USER", "EMPLOYEE", "ADMIN"})
public class HardwaresController {
    @Inject
    HardwareService hardwareService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllHardware() {
        List<Hardware> hardwareList = hardwareService.getAllHardwares();
        return Response.ok(hardwareList).build();
    }

}
