package org.pl.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Client;
import org.pl.model.Hardware;
import org.pl.services.HardwareService;

import java.util.ArrayList;
import java.util.UUID;

@Path("/hardware")
public class HardwareController {
    @Inject
    private HardwareService hardwareService; //something tu nie teges

    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHardwareById(@PathParam("id")String id) {
        try {
            UUID uuid = UUID.fromString(id);
            Hardware hardware = hardwareService.get(uuid);
            return Response.ok(hardware).build();
        } catch (IllegalArgumentException e) {
            return Response.status(400, "Given id is invalid").build();
        } catch (RepositoryException e) {
            return Response.status(404, "Hardware not found").build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllHardwares() {
        //ArrayList<Hardware> hardwares = hardwareService.getAll(); //TODO dodac metode zwracającą wszystkie urzadzenia
        return Response.ok().build(); //wyrzucic liste w odpowiedzi
    }

//    @POST
//    @Produces(MediaType.TEXT_PLAIN)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response addHardware(Hardware hardware) {
//        try {
//            hardwareService.add(hardware);
//            return Response.status(201, "Created successfully").build();
//        } catch (RepositoryException e) {
//            return Response.status(400, "Hardware already exists").build();
//            //przy obecnej implementacji tego bledu nie powinno wyrzucic, bo hardware jest nadpisywany
//        }
//    }

    @PUT
    @Path("id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateHardware(Hardware hardware, @PathParam("id")String id) {
        try {
            UUID uuid = UUID.fromString(id);
            //hardwareService.update(uuid, hardware);
            //brak metody do updatu hardware
            return Response.ok(hardware).build();
        } catch (IllegalArgumentException e) {
            return Response.status(400, "Invalid data in request").build();
        } /*catch (RepositoryException e) {
            return Response.status(404, "Hardware does not exist").build();
        }*/
    }

    @DELETE
    @Path("id/{id}")
    public Response deleteHardware(@PathParam("id")String id) {
        try {
            UUID uuid = UUID.fromString(id);
            // tutaj pytanie czy hardware archive = false oznacza ze jest przypisany do repair
            if (!hardwareService.isHardwareArchive(uuid)) {
                hardwareService.archivize(uuid);
                return Response.ok("Deleted Successfully").build();
            } else
                return Response.status(400, "Hardware is in active repair").build();
        } catch (RepositoryException e) {
            return Response.status(404, "Hardware does not exist").build();
        }
    }
}
