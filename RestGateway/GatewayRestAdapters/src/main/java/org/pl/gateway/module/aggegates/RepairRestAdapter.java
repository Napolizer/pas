package org.pl.gateway.module.aggegates;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.ws.rs.WebApplicationException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.pl.gateway.module.jsonb.adapters.RepairJsonbAdapter;
import org.pl.gateway.module.model.RepairRest;
import org.pl.gateway.module.ports.userinterface.repair.ReadRepairUseCases;
import org.pl.gateway.module.ports.userinterface.repair.WriteRepairUseCases;

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
@ApplicationScoped
public class RepairRestAdapter implements ReadRepairUseCases, WriteRepairUseCases {

    @Inject
    private HttpClient httpClient;

    private static final String RepairRestApi = "https://localhost:8181/RestAdapters-1.0-SNAPSHOT/api";
    public RepairRest add(RepairRest RepairRest) throws URISyntaxException, IOException, InterruptedException {
        var json = JsonbBuilder
                .create(new JsonbConfig().withAdapters(new RepairJsonbAdapter()))
                .toJson(RepairRest);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .uri(new URI(RepairRestApi + "/RepairRest"))
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
    }

    public RepairRest get(UUID id) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(RepairRestApi + "/RepairRest/id/" + id))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        var reader = response.body();
        return JsonbBuilder
                .create(new JsonbConfig().withAdapters(new RepairJsonbAdapter()))
                .fromJson(reader, RepairRest.class);
    }

    public String getInfo(UUID id) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(RepairRestApi + "/RepairRest/id/" + id))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        var reader = response.body();
        return JsonbBuilder
                .create(new JsonbConfig().withAdapters(new RepairJsonbAdapter()))
                .fromJson(reader, RepairRest.class).toString();
    }

    public List<RepairRest> getAllClientRepairs(UUID clientId) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(RepairRestApi + "/RepairRests"))
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
    }

    public boolean isRepairArchive(UUID id) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(RepairRestApi + "/RepairRest/id/" + id))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        var reader = response.body();
        return JsonbBuilder
                .create(new JsonbConfig().withAdapters(new RepairJsonbAdapter()))
                .fromJson(reader, RepairRest.class).getArchive();
    }
    public RepairRest archivize(UUID id) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(RepairRestApi + "/RepairRest/id/" + id))
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
    }

    public RepairRest repair(UUID id) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(RepairRestApi + "/RepairRest/id/" + id))
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

        HttpRequest putHttpRequest = HttpRequest.newBuilder()
                .uri(new URI(RepairRestApi + "/RepairRest/id/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> putResponse = httpClient.send(putHttpRequest, HttpResponse.BodyHandlers.ofString());

        var putReader = putResponse.body();
        return JsonbBuilder
                .create(new JsonbConfig().withAdapters(new RepairJsonbAdapter()))
                .fromJson(putReader, RepairRest.class);
    }

    public int getPresentSize() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(RepairRestApi + "/RepairRests"))
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
    }

    public int getArchiveSize() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(RepairRestApi + "/RepairRests"))
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
    }

    public List<RepairRest> getClientsPastRepairs(UUID uuid) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(RepairRestApi + "/RepairRests"))
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
    }

    public List<RepairRest> getClientsPresentRepairs(UUID uuid) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(RepairRestApi + "/RepairRests"))
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
    }

    public List<RepairRest> getAllRepairs() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(RepairRestApi + "/RepairRests"))
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
    }

    public RepairRest updateRepair(UUID uuid, RepairRest RepairRest) throws URISyntaxException, IOException, InterruptedException {
        var json = JsonbBuilder
                .create(new JsonbConfig().withAdapters(new RepairJsonbAdapter()))
                .toJson(RepairRest);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(RepairRestApi + "/RepairRest/id/" + uuid))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        var reader = response.body();
        return JsonbBuilder
                .create(new JsonbConfig().withAdapters(new RepairJsonbAdapter()))
                .fromJson(reader, RepairRest.class);
    }
}
