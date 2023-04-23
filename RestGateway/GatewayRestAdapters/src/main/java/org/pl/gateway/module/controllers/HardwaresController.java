package org.pl.gateway.module.controllers;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.pl.gateway.module.model.HardwareRest;
import org.pl.gateway.module.ports.userinterface.hardware.ReadHardwareUseCases;

import java.util.List;

@Path("/hardwares")
@RolesAllowed(value={"USER", "EMPLOYEE", "ADMIN"})
public class HardwaresController {
    @Inject
    private ReadHardwareUseCases readHardwareUseCases;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllHardware() {
        List<HardwareRest> hardwareList = readHardwareUseCases.getAllHardwares()
                .stream()
                .toList();
        return Response.ok(hardwareList).build();
    }

}
