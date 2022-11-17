package org.pl.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Client;
import org.pl.services.ClientService;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

@Path("/client")
public class ClientController {
    @Inject
    private ClientService clientService;

    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClientById(@PathParam("id")String id) {
        try {
            UUID uuid = UUID.fromString(id);
            Client client = clientService.get(uuid);
            return Response.ok(client).build();
        } catch (IllegalArgumentException e) {
            return Response.status(400, "Given id is invalid").build();
        } catch (RepositoryException e) {
            return Response.status(404, "Client not found").build();
        }
    }

//    @GET
//    @Path("/username/{username}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getClientByUsername(@PathParam("username")String username, @QueryParam("strict")String strict) {
//        try {
//            if (Objects.equals(strict, "true")) {
//                Client client = null; //TODO dodac metode zwracajaca jednego uzytkownika i liste po username
//                return Response.ok(client).build();
//            } else {
//                ArrayList<Client> clients = null;
//                return Response.ok(clients).build();
//            }
//        } catch (RepositoryException e) {
//            return Response.status(404, "Client not found").build();
//        }
//    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllClients() {
        ArrayList<Client> clients = null; //TODO dodac metode zwracającą wszystkich klientów
        return Response.ok(clients).build();
    }

//    @POST
//    @Produces(MediaType.TEXT_PLAIN)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response addClient(Client client) {
//        try {
//            clientService.add(client);
//            return Response.status(201, "Created successfully").build();
//        } catch (RepositoryException e) {
//            return Response.status(400, "Client already exists").build();
//            //przy obecnej implementacji tego bledu nie powinno wyrzucic, klient jest nadpisywany z archive false
//        }
//    }

    @PUT
    @Path("id/{id}") //odrozni put od get?
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateClient(Client client, @PathParam("id")String id) {
        try {
            UUID uuid = UUID.fromString(id);
            //clientService.update(uuid, client);
            //brak metody do updatu klienta
            return Response.ok(client).build();
        } catch (IllegalArgumentException e) {
            return Response.status(400, "Invalid data in request").build();
       } /*catch (RepositoryException e) {
            return Response.status(404, "Client does not exist").build();
        }*/
    }

    @PUT
    @Path("id/{id}/deactivate") //odrozni put od get?
    @Produces(MediaType.APPLICATION_JSON)
    public Response deactivateClient(@PathParam("id")String id) {
        try {
            UUID uuid = UUID.fromString(id);
            clientService.archivize(uuid);
            return Response.ok(clientService.get(uuid)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(400, "Invalid data in request").build();
        } catch (RepositoryException e) {
            return Response.status(404, "Client does not exist").build();
        }
    }

    @PUT
    @Path("id/{id}/activate") //odrozni put od get?
    @Produces(MediaType.APPLICATION_JSON)
    public Response activateClient(@PathParam("id")String id) {
        try {
            UUID uuid = UUID.fromString(id);
            //clientService.dearchivize(uuid); brak metody
            return Response.ok(clientService.get(uuid)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(400, "Invalid data in request").build();
        } catch (RepositoryException e) {
            return Response.status(404, "Client does not exist").build();
        }
    }
}
