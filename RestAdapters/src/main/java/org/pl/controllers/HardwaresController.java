package org.pl.controllers;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.pl.converters.HardwareConverter;
import org.pl.model.HardwareRest;
import org.pl.userinterface.hardware.ReadHardwareQueries;

import java.util.List;

@Path("/hardwares")
@RolesAllowed(value={"USER", "EMPLOYEE", "ADMIN"})
public class HardwaresController {
    @Inject
    private ReadHardwareQueries readHardwareQueries;
    @Inject
    private HardwareConverter hardwareConverter;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllHardware() {
        List<HardwareRest> hardwareList = readHardwareQueries.getAllHardwares()
                .stream()
                .map(hardwareConverter::convert)
                .toList();
        return Response.ok(hardwareList).build();
    }

}
