package org.pl.gateway.module.aggegates;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.ws.rs.WebApplicationException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.faulttolerance.*;
import org.pl.gateway.module.config.ApiConfig;
import org.pl.gateway.module.jsonb.adapters.RepairJsonbAdapter;
import org.pl.gateway.module.model.RepairRest;
import org.pl.gateway.module.ports.userinterface.repair.ReadRepairUseCases;
import org.pl.gateway.module.ports.userinterface.repair.WriteRepairUseCases;
import org.pl.gateway.module.providers.HttpAuthorizedBuilderProvider;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Stateless
public class RepairRestAdapter implements ReadRepairUseCases, WriteRepairUseCases {

    @Inject
    private HttpClient httpClient;
    @Inject
    private ApiConfig apiConfig;

    @Inject
    private HttpAuthorizedBuilderProvider httpAuthorizedBuilderProvider;

    @Timeout(value = 5000)
    public RepairRest add(RepairRest RepairRest) {
        try {
            var json = JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new RepairJsonbAdapter()))
                    .toJson(RepairRest);

            HttpRequest httpRequest = httpAuthorizedBuilderProvider.builder()
                    .header("Content-Type", "application/json")
                    .uri(new URI(apiConfig.getRepairApi() + "/repair"))
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 201) {
                throw new WebApplicationException(response.statusCode());
            }

            var reader = response.body();
            return JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new RepairJsonbAdapter()))
                    .fromJson(reader, RepairRest.class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Timeout(value = 5000)
    @CircuitBreaker(successThreshold = 5, requestVolumeThreshold = 4, failureRatio = 0.75, delay = 1000)
    public RepairRest get(UUID id) {
        try {
            HttpRequest httpRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI(apiConfig.getRepairApi() + "/repair/id/" + id))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            var reader = response.body();
            return JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new RepairJsonbAdapter()))
                    .fromJson(reader, RepairRest.class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Timeout(value = 5000)
    @CircuitBreaker(successThreshold = 5, requestVolumeThreshold = 4, failureRatio = 0.75, delay = 1000)
    public String getInfo(UUID id) {
        try {
            HttpRequest httpRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI(apiConfig.getRepairApi() + "/repair/id/" + id))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            var reader = response.body();
            return JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new RepairJsonbAdapter()))
                    .fromJson(reader, RepairRest.class).toString();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Timeout(value = 5000)
    @CircuitBreaker(successThreshold = 5, requestVolumeThreshold = 4, failureRatio = 0.75, delay = 1000)
    public List<RepairRest> getAllClientRepairs(UUID clientId) {
        try {
            HttpRequest httpRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI(apiConfig.getRepairApi() + "/repair"))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new WebApplicationException(response.statusCode());
            }

            var reader = response.body();
            List<RepairRest> allRepairRests = JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new RepairJsonbAdapter()))
                    .fromJson(reader, new ArrayList<RepairRest>(){}.getClass().getGenericSuperclass());

            List<RepairRest> clientsRepairRests = new ArrayList<>();
            for (RepairRest RepairRest : allRepairRests) {
                if (RepairRest.getClient().getId() == clientId) {
                    clientsRepairRests.add(RepairRest);
                }
            }
            return clientsRepairRests;
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Timeout(value = 5000)
    @CircuitBreaker(successThreshold = 5, requestVolumeThreshold = 4, failureRatio = 0.75, delay = 1000)
    public Boolean isRepairArchive(UUID id) {
        try {
            HttpRequest httpRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI(apiConfig.getRepairApi() + "/repair/id/" + id))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            var reader = response.body();
            return JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new RepairJsonbAdapter()))
                    .fromJson(reader, RepairRest.class).getArchive();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Timeout(value = 5000)
    @Retry(maxRetries = 4, delay = 500)
    @Bulkhead(1)
    public RepairRest archivize(UUID id) {
        try {
            HttpRequest httpRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI(apiConfig.getRepairApi() + "/repair/id/" + id))
                    .DELETE()
                    .build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new WebApplicationException(response.statusCode());
            }
            var reader = response.body();
            return JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new RepairJsonbAdapter()))
                    .fromJson(reader, RepairRest.class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Timeout(value = 5000)
    @Retry(maxRetries = 4, delay = 500)
    @Bulkhead(1)
    public RepairRest repair(UUID id) {
        try {
            HttpRequest httpRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI(apiConfig.getRepairApi() + "/repair/id/" + id))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            var reader = response.body();
            RepairRest RepairRest =  JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new RepairJsonbAdapter()))
                    .fromJson(reader, RepairRest.class);
            RepairRest.getHardware().setArchive(true);
            RepairRest.getDateRange().setEndDate(new Date());
            RepairRest.setArchive(true);

            var json = JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new RepairJsonbAdapter()))
                    .toJson(RepairRest);

            HttpRequest putHttpRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI(apiConfig.getRepairApi() + "/repair/id/" + id))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> putResponse = httpClient.send(putHttpRequest, HttpResponse.BodyHandlers.ofString());

            var putReader = putResponse.body();
            return JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new RepairJsonbAdapter()))
                    .fromJson(putReader, RepairRest.class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Timeout(value = 5000)
    @CircuitBreaker(successThreshold = 5, requestVolumeThreshold = 4, failureRatio = 0.75, delay = 1000)
    public Integer getPresentSize() {
        try {
            HttpRequest httpRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI(apiConfig.getRepairApi() + "/repairs"))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new WebApplicationException(response.statusCode());
            }

            var reader = response.body();
            List<RepairRest> allRepairRests = JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new RepairJsonbAdapter()))
                    .fromJson(reader, new ArrayList<RepairRest>(){}.getClass().getGenericSuperclass());

            List<RepairRest> presentRepairRests = new ArrayList<>();
            for (RepairRest RepairRest : allRepairRests) {
                if (!RepairRest.getClient().getArchive()) {
                    presentRepairRests.add(RepairRest);
                }
            }
            return presentRepairRests.size();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Timeout(value = 5000)
    @CircuitBreaker(successThreshold = 5, requestVolumeThreshold = 4, failureRatio = 0.75, delay = 1000)
    public Integer getArchiveSize() {
        try {
            HttpRequest httpRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI(apiConfig.getRepairApi() + "/repairs"))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new WebApplicationException(response.statusCode());
            }

            var reader = response.body();
            List<RepairRest> allRepairRests = JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new RepairJsonbAdapter()))
                    .fromJson(reader, new ArrayList<RepairRest>(){}.getClass().getGenericSuperclass());

            List<RepairRest> archiveRepairRests = new ArrayList<>();
            for (RepairRest RepairRest : allRepairRests) {
                if (RepairRest.getClient().getArchive()) {
                    archiveRepairRests.add(RepairRest);
                }
            }
            return archiveRepairRests.size();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Timeout(value = 5000)
    @CircuitBreaker(successThreshold = 5, requestVolumeThreshold = 4, failureRatio = 0.75, delay = 1000)
    public List<RepairRest> getClientsPastRepairs(UUID uuid) {
        try {
            HttpRequest httpRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI(apiConfig.getRepairApi() + "/repairs"))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new WebApplicationException(response.statusCode());
            }

            var reader = response.body();
            List<RepairRest> allRepairRests = JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new RepairJsonbAdapter()))
                    .fromJson(reader, new ArrayList<RepairRest>(){}.getClass().getGenericSuperclass());

            List<RepairRest> archiveRepairRests = new ArrayList<>();
            for (RepairRest RepairRest : allRepairRests) {
                if (RepairRest.getClient().getArchive()) {
                    archiveRepairRests.add(RepairRest);
                }
            }
            return archiveRepairRests;
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Timeout(value = 5000)
    @CircuitBreaker(successThreshold = 5, requestVolumeThreshold = 4, failureRatio = 0.75, delay = 1000)
    public List<RepairRest> getClientsPresentRepairs(UUID uuid) {
        try {
            HttpRequest httpRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI(apiConfig.getRepairApi() + "/repairs"))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new WebApplicationException(response.statusCode());
            }

            var reader = response.body();
            List<RepairRest> allRepairRests = JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new RepairJsonbAdapter()))
                    .fromJson(reader, new ArrayList<RepairRest>(){}.getClass().getGenericSuperclass());

            List<RepairRest> presentRepairRests = new ArrayList<>();
            for (RepairRest RepairRest : allRepairRests) {
                if (!RepairRest.getClient().getArchive()) {
                    presentRepairRests.add(RepairRest);
                }
            }
            return presentRepairRests;
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Timeout(value = 5000)
    @CircuitBreaker(successThreshold = 5, requestVolumeThreshold = 4, failureRatio = 0.75, delay = 1000)
    public List<RepairRest> getAllRepairs() {
        try {
            HttpRequest httpRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI(apiConfig.getRepairApi() + "/repairs"))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new WebApplicationException(response.statusCode());
            }

            var reader = response.body();
            return JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new RepairJsonbAdapter()))
                    .fromJson(reader, new ArrayList<RepairRest>(){}.getClass().getGenericSuperclass());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Timeout(value = 5000)
    @Retry(maxRetries = 4, delay = 500)
    @Bulkhead(1)
    public RepairRest updateRepair(UUID uuid, RepairRest RepairRest) {
        try {
            var json = JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new RepairJsonbAdapter()))
                    .toJson(RepairRest);

            HttpRequest httpRequest = httpAuthorizedBuilderProvider.builder()
                    .uri(new URI(apiConfig.getRepairApi() + "/repair/id/" + uuid))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            var reader = response.body();
            return JsonbBuilder
                    .create(new JsonbConfig().withAdapters(new RepairJsonbAdapter()))
                    .fromJson(reader, RepairRest.class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
