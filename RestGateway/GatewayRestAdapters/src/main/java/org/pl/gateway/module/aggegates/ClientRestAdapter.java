package org.pl.gateway.module.aggegates;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import lombok.NoArgsConstructor;
import org.pl.gateway.module.jsonb.adapters.ClientTypeRestJsonbAdapter;
import org.pl.gateway.module.jsonb.adapters.HardwareTypeRestJsonbAdapter;
import org.pl.gateway.module.model.ClientRest;
import org.pl.gateway.module.model.ClientRestWithPassword;
import org.pl.gateway.module.model.ClientTypeRest;
import org.pl.gateway.module.model.UserRestCredentials;
import org.pl.gateway.module.model.exceptions.RepositoryRestException;
import org.pl.gateway.module.jsonb.adapters.RepairJsonbAdapter;
import org.pl.gateway.module.model.*;
import org.pl.gateway.module.model.exceptions.authentication.InvalidCredentialsException;
import org.pl.gateway.module.ports.userinterface.client.ReadClientUseCases;
import org.pl.gateway.module.ports.userinterface.client.WriteClientUseCases;
import org.pl.gateway.module.providers.HttpAuthorizedBuilderProvider;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@SessionScoped
public class ClientRestAdapter implements ReadClientUseCases, WriteClientUseCases, Serializable {
    @Inject
    private HttpAuthorizedBuilderProvider httpAuthorizedBuilderProvider;
    @Inject
    private HttpClient httpClient;

    @Context
    private HttpHeaders httpHeaders;
    private static final String userApi = "https://localhost:8181/UserRestAdapters-1.0-SNAPSHOT/api";
    private static final String clientApi = "https://localhost:8181/RestAdapters-1.0-SNAPSHOT/api";

    public ClientRest add(ClientRest ClientRest) throws WebApplicationException {
        try {
            var json = JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new ClientTypeRestJsonbAdapter()))
                    .toJson(new ClientRestWithPassword(ClientRest));

            HttpRequest createClientRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI(clientApi + "/client"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> createClientResponse = httpClient.send(createClientRequest, HttpResponse.BodyHandlers.ofString());

            HttpRequest createUserRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI(userApi + "/user"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> createUserResponse = httpClient.send(createUserRequest, HttpResponse.BodyHandlers.ofString());

            if (createClientResponse.statusCode() != 201 || createUserResponse.statusCode() != 201) {
                throw new WebApplicationException(createClientResponse.statusCode());
            }

            var reader = createClientResponse.body();
            return JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new ClientTypeRestJsonbAdapter()))
                    .fromJson(reader, ClientRest.class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new WebApplicationException(400);
        }
    }

    public ClientRest get(UUID id) {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI(repairApi +"/client/id/" + id))
                    .header("Authorization", httpHeaders.getHeaderString("Authorization"))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            var reader = response.body();
            return JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new HardwareTypeRestJsonbAdapter())).
                    fromJson(reader, ClientRest.class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String getInfo(UUID id) {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI(repairApi +"/client/id/" + id))
                    .header("Authorization", httpHeaders.getHeaderString("Authorization"))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            var reader = response.body();
            return JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new HardwareTypeRestJsonbAdapter())).
                    fromJson(reader, ClientRest.class).toString();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public double getClientBalance(UUID id) {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI(repairApi +"/client/id/" + id))
                    .header("Authorization", httpHeaders.getHeaderString("Authorization"))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            var reader = response.body();
            return JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new HardwareTypeRestJsonbAdapter())).
                    fromJson(reader, ClientRest.class).getBalance();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public boolean isClientArchive(UUID id) {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI(repairApi +"/client/id/" + id))
                    .header("Authorization", httpHeaders.getHeaderString("Authorization"))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            var reader = response.body();
            return JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new HardwareTypeRestJsonbAdapter())).
                    fromJson(reader, ClientRest.class).getArchive();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public ClientRest archive(UUID id) {
        return null;
    }

    public List<ClientRest> getAllClients() {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI( repairApi + "/clients"))
                    .header("Authorization", httpHeaders.getHeaderString("Authorization"))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            var reader = response.body();
            return JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new HardwareTypeRestJsonbAdapter()))
                    .fromJson(reader, new ArrayList<HardwareRest>(){}.getClass().getGenericSuperclass());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public ClientRest getClientByUsername(String username) {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI(repairApi +"/client/username/" + username))
                    .header("Authorization", httpHeaders.getHeaderString("Authorization"))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            var reader = response.body();
            return JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new HardwareTypeRestJsonbAdapter())).
                    fromJson(reader, ClientRest.class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public int getPresentSize() {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI(repairApi + "/clients"))
                    .header("Authorization", httpHeaders.getHeaderString("Authorization"))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new WebApplicationException(response.statusCode());
            }

            var reader = response.body();
            List<ClientRest> allClients = JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new RepairJsonbAdapter()))
                    .fromJson(reader, new ArrayList<RepairRest>(){}.getClass().getGenericSuperclass());

            List<ClientRest> presentClients = new ArrayList<>();
            for (ClientRest clientRest : allClients) {
                if (!clientRest.getArchive()) {
                    presentClients.add(clientRest);
                }
            }
            return presentClients.size();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public int getArchiveSize() {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI(repairApi + "/clients"))
                    .header("Authorization", httpHeaders.getHeaderString("Authorization"))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new WebApplicationException(response.statusCode());
            }

            var reader = response.body();
            List<ClientRest> allClients = JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new RepairJsonbAdapter()))
                    .fromJson(reader, new ArrayList<RepairRest>(){}.getClass().getGenericSuperclass());

            List<ClientRest> presentClients = new ArrayList<>();
            for (ClientRest clientRest : allClients) {
                if (clientRest.getArchive()) {
                    presentClients.add(clientRest);
                }
            }
            return presentClients.size();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<ClientRest> getAllClientsFilter(String substr) {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI( repairApi + "/client/filter/" + substr))
                    .header("Authorization", httpHeaders.getHeaderString("Authorization"))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            var reader = response.body();
            return JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new HardwareTypeRestJsonbAdapter()))
                    .fromJson(reader, new ArrayList<HardwareRest>(){}.getClass().getGenericSuperclass());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public ClientRest updateClient(UUID uuid, ClientRest ClientRest) {
        return null;
    }

    public ClientRest updatePassword(UUID uuid, String newPassword) {
        return null;
    }

    public ClientRest dearchive(UUID uuid) {
        return null;
    }

    public ClientTypeRest getClientTypeById(UUID uuid) {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI(repairApi + "/clients"))
                    .header("Authorization", httpHeaders.getHeaderString("Authorization"))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            var reader = response.body();
            List<ClientRest> allClients = JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new HardwareTypeRestJsonbAdapter()))
                    .fromJson(reader, new ArrayList<HardwareRest>(){}.getClass().getGenericSuperclass());
            for (ClientRest clientRest : allClients) {
                if (clientRest.getClientType().getId() == uuid) {
                    return clientRest.getClientType();
                }
            }
            return null;
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String login(UserRestCredentials userRestCredentials) throws InvalidCredentialsException {
        try {
            var json = JsonbBuilder
                    .create(new JsonbConfig())
                    .toJson(userRestCredentials);

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI(userApi + "/user/login"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new WebApplicationException(response.statusCode());
            }

            var reader = response.body();
            JsonObject jsonObject = Json.createReader(new StringReader(reader)).readObject();
            return jsonObject.getString("token");
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new InvalidCredentialsException();
        }
    }
}
