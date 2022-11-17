package org.pl.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Hardware;
import org.pl.model.Repair;
import org.pl.services.RepairService;

import java.util.UUID;

@Path("repair")
public class RepairController {
    @Inject
    private RepairService repairService; //something tu nie teges

    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRepairById(@PathParam("id")String id) {
        try {
            UUID uuid = UUID.fromString(id);
            Repair repair = repairService.get(uuid);
            return Response.ok(repair).build();
        } catch (IllegalArgumentException e) {
            return Response.status(400, "Given id is invalid").build();
        } catch (RepositoryException e) {
            return Response.status(404, "Repair not found").build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRepairs() {
        //ArrayList<Repair> hardwares = repairService.getAll(); //TODO dodac metode zwracającą wszystkie naprawy
        return Response.ok().build(); //wyrzucic liste w odpowiedzi
    }

//    @POST
//    @Produces(MediaType.TEXT_PLAIN)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response addRepair(Repair repair) {
//        try {
//            repairService.add(repair);
//            return Response.status(201, "Created successfully").build();
//        } catch (RepositoryException e) {
//            return Response.status(400, "Repair already exists").build();
//            //przy obecnej implementacji tego bledu nie powinno wyrzucic, bo repair jest nadpisywany
//        }
//    }

    @PUT
    @Path("id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateRepair(Repair repair, @PathParam("id")String id) {
        try {
            UUID uuid = UUID.fromString(id);
            //repairService.update(uuid, repair);
            //brak metody do updatu hardware
            return Response.ok(repair).build();
        } catch (IllegalArgumentException e) {
            return Response.status(400, "Invalid data in request").build();
        } /*catch (RepositoryException e) {
            return Response.status(404, "Repair does not exist").build();
        }*/
    }

    @DELETE
    @Path("id/{id}")
    public Response deleteRepair(@PathParam("id")String id) {
        try {
            UUID uuid = UUID.fromString(id);
            // tutaj pytanie czy repair archive = false oznacza ze jest przypisany do repair
            if (!repairService.isRepairArchive(uuid)) {
                repairService.archivize(uuid);
                return Response.ok("Deleted Successfully").build();
            } else
                return Response.status(400, "Repair is already deleted").build();
        } catch (RepositoryException e) {
            return Response.status(404, "Repair does not exist").build();
        }
    }
}
