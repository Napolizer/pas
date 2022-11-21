package org.pl.controllers;

import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.pl.exceptions.HardwareException;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Condition;
import org.pl.model.Console;
import org.pl.model.Hardware;
import org.pl.services.HardwareService;
import java.util.List;
import java.util.UUID;

@Path("/hardware")
public class HardwareController {
//    @Inject
//    private HardwareService hardwareService; //something tu nie teges

//    @GET
//    @Path("/id/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getHardwareById(@PathParam("id")String id) {
//        try {
//            UUID uuid = UUID.fromString(id);
//            Hardware hardware = hardwareService.get(uuid);
//            return Response.ok(hardware).build();
//        } catch (IllegalArgumentException e) {
//            return Response.status(400, "Given id is invalid").build();
//        } catch (RepositoryException e) {
//            return Response.status(404, "Hardware not found").build();
//        }
//    }
//
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getAllHardwares() {
//        List<Hardware> hardwares = hardwareService.getAllHardwares();
//        return Response.ok(hardwares).build();
//    }

    @POST
//    @Produces(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addHardware(Hardware hardware) {
//        try {
//            hardwareService.add(hardware);
        var config = new JsonbConfig().withAdapters(new HardwareTypeAdapters());
        Jsonb jsonb = JsonbBuilder.create(config);
        hardware.setHardwareType(Console.builder().condition(Condition.AVERAGE).build());
        return Response.ok(jsonb.toJson(hardware)).build();
//        return Response.ok(hardware).build();
//            return Response.status(201, "Created successfully").build();
//        } catch (RepositoryException e) {
//            return Response.status(400, "Hardware already exists").build();
//        } catch (HardwareException e) {
//            return Response.status(400, "Invalid fields").build();
//        }
    }

//    @PUT
//    @Path("id/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response updateHardware(Hardware hardware, @PathParam("id")String id) {
//        try {
//            UUID uuid = UUID.fromString(id);
//            hardwareService.updateHardware(uuid, hardware);
//            return Response.ok(hardware).build();
//        } catch (IllegalArgumentException e) {
//            return Response.status(400, "Invalid data in request").build();
//        } catch (RepositoryException e) {
//            return Response.status(404, "Hardware does not exist").build();
//        }
//    }
//
//    @DELETE
//    @Path("id/{id}")
//    public Response deleteHardware(@PathParam("id")String id) {
//        try {
//            UUID uuid = UUID.fromString(id);
//            if (!hardwareService.isHardwareArchive(uuid)) {
//                hardwareService.archive(uuid);
//                return Response.ok("Deleted Successfully").build();
//            } else
//                return Response.status(400, "Hardware is in active repair").build();
//        } catch (RepositoryException e) {
//            return Response.status(404, "Hardware does not exist").build();
//        }
//    }
}
