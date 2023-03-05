package org.pl.controllers;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.pl.converters.RepairAppConverter;
import org.pl.model.RepairApp;
import org.pl.model.exceptions.RepositoryException;
import org.pl.model.Repair;
import org.pl.providers.ETagProvider;
import org.pl.services.RepairService;

import java.security.Principal;
import java.util.UUID;

@Path("/repair")
public class RepairController {
    @Inject
    private RepairService repairService;
    @Inject
    private Principal principal;
    @Inject
    private ETagProvider eTagProvider;
    @Inject
    private RepairAppConverter repairAppConverter;
    @Context
    private SecurityContext securityContext;
    @Context
    private HttpHeaders httpHeaders;

    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(value={"USER", "EMPLOYEE", "ADMIN"})
    public Response getRepairById(@PathParam("id")String id) {
        var json = Json.createObjectBuilder();
        try {
            UUID uuid = UUID.fromString(id);
            RepairApp repairApp = repairAppConverter.convert(repairService.get(uuid));
            return Response
                    .ok(repairApp)
                    .header("ETag", eTagProvider.generateETag(repairAppConverter.convert(repairApp)))
                    .build();
        } catch (IllegalArgumentException e) {
            json.add("error", "Given id is invalid");
            return Response.status(400).entity(json.build()).build();
        } catch (RepositoryException e) {
            json.add("error", "Repair not found");
            return Response.status(404).entity(json.build()).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed(value={"USER", "EMPLOYEE", "ADMIN"})
    public Response addRepair(@NotNull @Valid RepairApp repairApp) {
        if (securityContext.isUserInRole("USER")) {
            if (!repairApp.getClient().getUsername().equals(principal.getName())) {
                return Response.status(403).build();
            }
        }
        var json = Json.createObjectBuilder();
        try {
            RepairApp createdRepair = repairAppConverter.convert(repairService.add(repairAppConverter.convert(repairApp)));
            return Response.status(201).entity(createdRepair).build();
        } catch (RepositoryException e) {
            json.add("error", "Repair already exists");
            return Response.status(400).build();
        }
    }

    @PUT
    @Path("id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateRepair(@NotNull RepairApp repairApp, @PathParam("id")String id) {
        var json = Json.createObjectBuilder();
        try {
            String etag = httpHeaders.getHeaderString("If-Match");
            if (etag == null) {
                json.add("error", "Request is missing If-Match header");
                return Response.status(400).entity(json.build()).build();
            }
            UUID uuid = UUID.fromString(id);

            Repair existingRepair = repairService.get(uuid);
            if (!eTagProvider.generateETag(repairAppConverter.convert(existingRepair)).equals(etag)) {
                json.add("error", "Invalid If-Match signature");
                return Response.status(412).entity(json.build()).build();
            }

            RepairApp updatedRepair = repairAppConverter.convert(repairService.updateRepair(uuid, repairAppConverter.convert(repairApp)));
            return Response.ok(updatedRepair).build();
        } catch (IllegalArgumentException e) {
            json.add("error", "Invalid data in request");
            return Response.status(400).entity(json.build()).build();
        } catch (RepositoryException e) {
            json.add("error", "Repair does not exist");
            return Response.status(404).entity(json.build()).build();
        }
    }

    @DELETE
    @Path("id/{id}")
    @RolesAllowed(value={"USER", "EMPLOYEE", "ADMIN"})
    public Response deleteRepair(@PathParam("id")String id) {
        var json = Json.createObjectBuilder();
        try {
            UUID uuid = UUID.fromString(id);
            if (!repairService.isRepairArchive(uuid)) {
                RepairApp deletedRepair = repairAppConverter.convert(repairService.repair(uuid));
                return Response.ok(deletedRepair).build();
            } else {
                json.add("error", "Repair is already archive");
                return Response.status(400).entity(json.build()).build();
            }
        } catch (RepositoryException e) {
            json.add("error", "Given request is invalid");
            return Response.status(400).entity(json.build()).build();
        } catch (Exception e) {
            json.add("error", "Repair does not exist");
            return Response.status(404).entity(json.build()).build();
        }
    }
}
