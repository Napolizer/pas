package org.pl.gateway.module.aggegates;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.ws.rs.WebApplicationException;
import lombok.RequiredArgsConstructor;
import org.pl.gateway.module.config.ApiConfig;
import org.pl.gateway.module.jsonb.adapters.HardwareTypeRestJsonbAdapter;
import org.pl.gateway.module.model.HardwareRest;
import org.pl.gateway.module.model.HardwareTypeRest;
import org.pl.gateway.module.ports.userinterface.hardware.ReadHardwareUseCases;
import org.pl.gateway.module.ports.userinterface.hardware.WriteHardwareUseCases;
import org.pl.gateway.module.providers.HttpAuthorizedBuilderProvider;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@SessionScoped
public class HardwareRestAdapter implements ReadHardwareUseCases, WriteHardwareUseCases, Serializable {
    @Inject
    private HttpClient httpClient;
    @Inject
    private HttpAuthorizedBuilderProvider httpAuthorizedBuilderProvider;
    @Inject
    private ApiConfig apiConfig;

    public HardwareRest add(HardwareRest HardwareRest) {
        try {
            var json = JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new HardwareTypeRestJsonbAdapter())).
                    toJson(HardwareRest);


            HttpRequest httpRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI(apiConfig.getRepairApi() + "/hardware"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 201) {
                throw new WebApplicationException(response.statusCode());
            }

            var reader = response.body();
            return JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new HardwareTypeRestJsonbAdapter())).
                    fromJson(reader, HardwareRest.class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public boolean isHardwareArchive(UUID id) {
        try {
            HttpRequest httpRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI(apiConfig.getRepairApi() +"/hardware/id/" + id))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            var reader = response.body();
            return JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new HardwareTypeRestJsonbAdapter())).
                    fromJson(reader, HardwareRest.class).getArchive();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public HardwareRest get(UUID id) {
        try {
            HttpRequest httpRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI(apiConfig.getRepairApi() +"/hardware/id/" + id))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            var reader = response.body();
            return JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new HardwareTypeRestJsonbAdapter())).
                    fromJson(reader, HardwareRest.class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String getInfo(UUID id) {
        try {
            HttpRequest httpRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI(apiConfig.getRepairApi() +"/hardware/id/" + id))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            var reader = response.body();
            return JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new HardwareTypeRestJsonbAdapter())).
                    fromJson(reader, HardwareRest.class).toString();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void archive(UUID id) {
        try {
            HttpRequest httpRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI(apiConfig.getRepairApi() + "/hardware/id/" + id))
                    .DELETE()
                    .build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new WebApplicationException(response.statusCode());
            }
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    public int getPresentSize() {
        try {
            HttpRequest httpRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI(apiConfig.getRepairApi() + "/hardware/present"))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            var reader = response.body();
            List<HardwareRest> presentHardwareRest = JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new HardwareTypeRestJsonbAdapter()))
                    .fromJson(reader, new ArrayList<HardwareRest>(){}.getClass().getGenericSuperclass());
            return presentHardwareRest.size();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public int getArchiveSize() {
        try {
            HttpRequest httpRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI(apiConfig.getRepairApi() + "/hardwares"))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            var reader = response.body();
            List<HardwareRest> allHardwareRests = JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new HardwareTypeRestJsonbAdapter()))
                    .fromJson(reader, new ArrayList<HardwareRest>(){}.getClass().getGenericSuperclass());
            List<HardwareRest> archiveHardwareRests = new ArrayList<>();
            for (HardwareRest HardwareRest : allHardwareRests) {
                if (HardwareRest.getArchive()) {
                    archiveHardwareRests.add(HardwareRest);
                }
            }
            return archiveHardwareRests.size();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<HardwareRest> getAllHardwares() {
        try {
            HttpRequest httpRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI(apiConfig.getRepairApi() + "/hardwares"))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            var reader = response.body();
            System.out.println("Reader: " + reader);
            return JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new HardwareTypeRestJsonbAdapter()))
                    .fromJson(reader, new ArrayList<HardwareRest>(){}.getClass().getGenericSuperclass());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public HardwareRest updateHardware(UUID uuid, HardwareRest HardwareRest) {
        try {
            var json = JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new HardwareTypeRestJsonbAdapter())).
                    toJson(HardwareRest);

            HttpRequest httpRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI(apiConfig.getRepairApi() + "/hardware/id/" + uuid))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new WebApplicationException(response.statusCode());
            }

            var reader = response.body();
            return JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new HardwareTypeRestJsonbAdapter())).
                    fromJson(reader, HardwareRest.class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public HardwareTypeRest getHardwareTypeById(UUID uuid) {
        try {
            HttpRequest httpRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI(apiConfig.getRepairApi() + "/hardwares"))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            var reader = response.body();
            List<HardwareRest> allHardwareRests = JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new HardwareTypeRestJsonbAdapter()))
                    .fromJson(reader, new ArrayList<HardwareRest>(){}.getClass().getGenericSuperclass());
            for (HardwareRest hardwareRest : allHardwareRests) {
                if (hardwareRest.getHardwareType().getId() == uuid) {
                    return hardwareRest.getHardwareType();
                }
            }
            return null;
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<HardwareRest> getAllPresentHardware() {
        try {
            HttpRequest httpRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI(apiConfig.getRepairApi() + "/hardware/present"))
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

    public List<HardwareRest> getAllPresentHardwareFilter(String substr) {
        try {
            HttpRequest httpRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI(apiConfig.getRepairApi() + "/hardware/present/filter/" + substr))
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
}
