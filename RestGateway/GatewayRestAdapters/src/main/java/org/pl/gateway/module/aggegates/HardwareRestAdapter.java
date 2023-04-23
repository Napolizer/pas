package org.pl.gateway.module.aggegates;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.ws.rs.WebApplicationException;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.faulttolerance.*;
import org.pl.gateway.module.config.ApiConfig;
import org.pl.gateway.module.jsonb.adapters.HardwareTypeRestJsonbAdapter;
import org.pl.gateway.module.model.HardwareRest;
import org.pl.gateway.module.model.HardwareTypeRest;
import org.pl.gateway.module.ports.userinterface.hardware.ReadHardwareUseCases;
import org.pl.gateway.module.ports.userinterface.hardware.WriteHardwareUseCases;
import org.pl.gateway.module.providers.HttpAuthorizedBuilderProvider;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Stateless
public class HardwareRestAdapter implements ReadHardwareUseCases, WriteHardwareUseCases {
    @Inject
    private HttpClient httpClient;
    @Inject
    private HttpAuthorizedBuilderProvider httpAuthorizedBuilderProvider;
    @Inject
    private ApiConfig apiConfig;

    @Timeout(value = 5000)
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

    @Timeout(value = 5000)
    @CircuitBreaker(successThreshold = 5, requestVolumeThreshold = 4, failureRatio = 0.75, delay = 1000)
    public Boolean isHardwareArchive(UUID id) {
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

    @Timeout(value = 5000)
    @CircuitBreaker(successThreshold = 5, requestVolumeThreshold = 4, failureRatio = 0.75, delay = 1000)
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

    @Timeout(value = 5000)
    @CircuitBreaker(successThreshold = 5, requestVolumeThreshold = 4, failureRatio = 0.75, delay = 1000)
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

    @Timeout(value = 5000)
    @Retry(maxRetries = 4, delay = 500)
    @Bulkhead(1)
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

    @Timeout(value = 5000)
    @CircuitBreaker(successThreshold = 5, requestVolumeThreshold = 4, failureRatio = 0.75, delay = 1000)
    public Integer getPresentSize() {
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

    @Timeout(value = 5000)
    @CircuitBreaker(successThreshold = 5, requestVolumeThreshold = 4, failureRatio = 0.75, delay = 1000)
    public Integer getArchiveSize() {
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

    @Timeout(value = 5000)
    @CircuitBreaker(successThreshold = 5, requestVolumeThreshold = 4, failureRatio = 0.75, delay = 1000)
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

    @Timeout(value = 5000)
    @Retry(maxRetries = 4, delay = 500)
    @Bulkhead(1)
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

    @Timeout(value = 5000)
    @CircuitBreaker(successThreshold = 5, requestVolumeThreshold = 4, failureRatio = 0.75, delay = 1000)
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

    @Timeout(value = 5000)
    @CircuitBreaker(successThreshold = 5, requestVolumeThreshold = 4, failureRatio = 0.75, delay = 1000)
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

    @Timeout(value = 5000)
    @CircuitBreaker(successThreshold = 5, requestVolumeThreshold = 4, failureRatio = 0.75, delay = 1000)
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
