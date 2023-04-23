package org.pl.gateway.module.aggegates;

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
import org.pl.gateway.module.config.ApiConfig;
import org.pl.gateway.module.jsonb.adapters.ClientTypeRestJsonbAdapter;
import org.pl.gateway.module.jsonb.adapters.HardwareTypeRestJsonbAdapter;
import org.pl.gateway.module.model.ClientRest;
import org.pl.gateway.module.model.ClientRestWithPassword;
import org.pl.gateway.module.model.ClientTypeRest;
import org.pl.gateway.module.model.UserRestCredentials;
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
    @Inject
    private ApiConfig apiConfig;

    public ClientRest add(ClientRest ClientRest) throws WebApplicationException {
        try {
            var json = JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new ClientTypeRestJsonbAdapter()))
                    .toJson(new ClientRestWithPassword(ClientRest));

            HttpRequest createClientRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI(apiConfig.getClientApi() + "/client"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> createClientResponse = httpClient.send(createClientRequest, HttpResponse.BodyHandlers.ofString());

            // Make sure that UserModule and RepairModule entities has same id
            var reader = createClientResponse.body();
            JsonObject jsonObject = Json.createReader(new StringReader(reader)).readObject();
            String createdClientId = jsonObject.getString("id");
            ClientRest.setId(UUID.fromString((createdClientId)));
            json = JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new ClientTypeRestJsonbAdapter()))
                    .toJson(new ClientRestWithPassword(ClientRest));

            HttpRequest createUserRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI(apiConfig.getUserApi() + "/user"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> createUserResponse = httpClient.send(createUserRequest, HttpResponse.BodyHandlers.ofString());

            if (createClientResponse.statusCode() != 201 || createUserResponse.statusCode() != 201) {
                throw new WebApplicationException(createClientResponse.statusCode());
            }

            reader = createClientResponse.body();
            return JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new ClientTypeRestJsonbAdapter()))
                    .fromJson(reader, ClientRest.class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new WebApplicationException(400);
        }
    }

    public ClientRest get(UUID id) {
        try {
            HttpRequest httpRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI(apiConfig.getClientApi() +"/client/id/" + id))
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
            HttpRequest httpRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI(apiConfig.getClientApi() +"/client/id/" + id))
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
            HttpRequest httpRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI(apiConfig.getClientApi() +"/client/id/" + id))
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
            HttpRequest httpRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI(apiConfig.getClientApi() +"/client/id/" + id))
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
        try {
            HttpRequest archiveClientRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI(apiConfig.getClientApi() + "/client/id/" + id + "/deactivate"))
                    .PUT(HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> archiveClientResponse = httpClient.send(archiveClientRequest, HttpResponse.BodyHandlers.ofString());

            HttpRequest archiveUserRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI(apiConfig.getUserApi() + "/user/id/" + id + "/deactivate"))
                    .PUT(HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> archiveUserResponse = httpClient.send(archiveUserRequest, HttpResponse.BodyHandlers.ofString());

            if (archiveClientResponse.statusCode() != 200 || archiveUserResponse.statusCode() != 200) {
                throw new WebApplicationException(archiveClientResponse.statusCode());
            }

            var reader = archiveClientResponse.body();
            return JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new ClientTypeRestJsonbAdapter()))
                    .fromJson(reader, ClientRest.class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new WebApplicationException(400);
        }
    }

    public List<ClientRest> getAllClients() {
        try {
            HttpRequest httpRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI( apiConfig.getClientApi() + "/clients"))
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
            HttpRequest httpRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI(apiConfig.getClientApi() +"/client/username/" + username))
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
            HttpRequest httpRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI(apiConfig.getClientApi() + "/clients"))
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
            HttpRequest httpRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI(apiConfig.getClientApi() + "/clients"))
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
            HttpRequest httpRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI( apiConfig.getClientApi() + "/client/filter/" + substr))
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

    public ClientRest updateClient(UUID uuid, ClientRest clientRest) {
        try {
            var json = JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new ClientTypeRestJsonbAdapter())).
                    toJson(new ClientRestWithPassword(clientRest));

            HttpRequest updatedClientRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI(apiConfig.getClientApi() + "/client/id/" + uuid))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> updatedClientResponse = httpClient.send(updatedClientRequest, HttpResponse.BodyHandlers.ofString());

            HttpRequest updatedUserRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI(apiConfig.getUserApi() + "/user/id/" + uuid))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> updatedUserResponse = httpClient.send(updatedUserRequest, HttpResponse.BodyHandlers.ofString());


            if (updatedClientResponse.statusCode() != 200 || updatedUserResponse.statusCode() != 200) {
                throw new WebApplicationException(updatedClientResponse.statusCode());
            }

            var reader = updatedClientResponse.body();
            return JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new ClientTypeRestJsonbAdapter())).
                    fromJson(reader, ClientRest.class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new WebApplicationException(400);
        }
    }

    public ClientRest updatePassword(UUID uuid, String newPassword) {
        try {
            var json = Json.createObjectBuilder();
            json.add("newPassword", newPassword);

            HttpRequest updatedUserRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI(apiConfig.getUserApi() + "/user/id/" + uuid + "/change_password"))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json.build().toString()))
                    .build();
            HttpResponse<String> updatedUserResponse = httpClient.send(updatedUserRequest, HttpResponse.BodyHandlers.ofString());

            if ( updatedUserResponse.statusCode() != 200) {
                throw new WebApplicationException(updatedUserResponse.statusCode());
            }

            var reader = updatedUserResponse.body();
            return JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new ClientTypeRestJsonbAdapter())).
                    fromJson(reader, ClientRest.class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new WebApplicationException(400);
        }
    }

    public ClientRest dearchive(UUID uuid) {
        try {
            HttpRequest dearchiveClientRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI(apiConfig.getClientApi() + "/client/id/" + uuid + "/activate"))
                    .PUT(HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> dearchiveClientResponse = httpClient.send(dearchiveClientRequest, HttpResponse.BodyHandlers.ofString());

            HttpRequest dearchiveUserRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI(apiConfig.getUserApi() + "/user/id/" + uuid + "/activate"))
                    .PUT(HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> dearchiveUserResponse = httpClient.send(dearchiveUserRequest, HttpResponse.BodyHandlers.ofString());

            if (dearchiveClientResponse.statusCode() != 200 || dearchiveUserResponse.statusCode() != 200) {
                throw new WebApplicationException(dearchiveClientResponse.statusCode());
            }

            var reader = dearchiveClientResponse.body();
            return JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new ClientTypeRestJsonbAdapter()))
                    .fromJson(reader, ClientRest.class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new WebApplicationException(400);
        }
    }

    public ClientTypeRest getClientTypeById(UUID uuid) {
        try {
            HttpRequest httpRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI(apiConfig.getClientApi() + "/clients"))
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
                    .uri(new URI(apiConfig.getUserApi() + "/user/login"))
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
