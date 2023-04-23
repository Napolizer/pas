package org.pl.gateway.module.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.ws.rs.WebApplicationException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.pl.gateway.module.adapters.RepairAdapter;
import org.pl.gateway.module.model.Repair;
import org.pl.gateway.module.userinterface.repair.ReadRepairUseCases;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@ApplicationScoped
public class RepairService implements ReadRepairUseCases {

    @Inject
    private HttpClient httpClient;

    private static final String repairApi = "https://localhost:8181/RestAdapters-1.0-SNAPSHOT/api";
    public Repair add(Repair repair) throws URISyntaxException, IOException, InterruptedException {
        var json = JsonbBuilder
                .create(new JsonbConfig().withAdapters(new RepairAdapter()))
                .toJson(repair);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .uri(new URI(repairApi + "/repair"))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 201) {
            throw new WebApplicationException(response.statusCode());
        }

        var reader = response.body();
        return JsonbBuilder
                .create(new JsonbConfig().withAdapters(new RepairAdapter()))
                .fromJson(reader, Repair.class);
    }

    public Repair get(UUID id) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(repairApi + "/id/" + id))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        var reader = response.body();
        return JsonbBuilder
                .create(new JsonbConfig().withAdapters(new RepairAdapter()))
                .fromJson(reader, Repair.class);
    }

    public String getInfo(UUID id) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(repairApi + "/id/" + id))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        var reader = response.body();
        return JsonbBuilder
                .create(new JsonbConfig().withAdapters(new RepairAdapter()))
                .fromJson(reader, Repair.class).toString();
    }

    public List<Repair> getAllClientRepairs(UUID clientId) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(repairApi + "/repairs"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new WebApplicationException(response.statusCode());
        }

        var reader = response.body();
        List<Repair> allRepairs = JsonbBuilder
                .create(new JsonbConfig().withAdapters(new RepairAdapter()))
                .fromJson(reader, new ArrayList<Repair>(){}.getClass().getGenericSuperclass());

        List<Repair> clientsRepairs = new ArrayList<>();
        for (Repair repair : allRepairs) {
            if (repair.getClient().getId() == clientId) {
                clientsRepairs.add(repair);
            }
        }
        return clientsRepairs;
    }

    public boolean isRepairArchive(UUID id) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(repairApi + "/id/" + id))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        var reader = response.body();
        return JsonbBuilder
                .create(new JsonbConfig().withAdapters(new RepairAdapter()))
                .fromJson(reader, Repair.class).getArchive();
    }
    public Repair archivize(UUID id) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(repairApi + "/id/" + id))
                .DELETE()
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new WebApplicationException(response.statusCode());
        }
        var reader = response.body();
        return JsonbBuilder
                .create(new JsonbConfig().withAdapters(new RepairAdapter()))
                .fromJson(reader, Repair.class);
    }

    public Repair repair(UUID id) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(repairApi + "/id/" + id))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        var reader = response.body();
        Repair repair =  JsonbBuilder
                .create(new JsonbConfig().withAdapters(new RepairAdapter()))
                .fromJson(reader, Repair.class);
        repair.getHardware().setArchive(true);
        repair.getDateRange().setEndDate(new Date());
        repair.setArchive(true);

        var json = JsonbBuilder
                .create(new JsonbConfig().withAdapters(new RepairAdapter()))
                .toJson(repair);

        HttpRequest putHttpRequest = HttpRequest.newBuilder()
                .uri(new URI(repairApi + "/id/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> putResponse = httpClient.send(putHttpRequest, HttpResponse.BodyHandlers.ofString());

        var putReader = putResponse.body();
        return JsonbBuilder
                .create(new JsonbConfig().withAdapters(new RepairAdapter()))
                .fromJson(putReader, Repair.class);
    }

    public int getPresentSize() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(repairApi + "/repairs"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new WebApplicationException(response.statusCode());
        }

        var reader = response.body();
        List<Repair> allRepairs = JsonbBuilder
                .create(new JsonbConfig().withAdapters(new RepairAdapter()))
                .fromJson(reader, new ArrayList<Repair>(){}.getClass().getGenericSuperclass());

        List<Repair> presentRepairs = new ArrayList<>();
        for (Repair repair : allRepairs) {
            if (!repair.getClient().getArchive()) {
                presentRepairs.add(repair);
            }
        }
        return presentRepairs.size();
    }

    public int getArchiveSize() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(repairApi + "/repairs"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new WebApplicationException(response.statusCode());
        }

        var reader = response.body();
        List<Repair> allRepairs = JsonbBuilder
                .create(new JsonbConfig().withAdapters(new RepairAdapter()))
                .fromJson(reader, new ArrayList<Repair>(){}.getClass().getGenericSuperclass());

        List<Repair> archiveRepairs = new ArrayList<>();
        for (Repair repair : allRepairs) {
            if (repair.getClient().getArchive()) {
                archiveRepairs.add(repair);
            }
        }
        return archiveRepairs.size();
    }

    public List<Repair> getClientsPastRepairs(UUID uuid) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(repairApi + "/repairs"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new WebApplicationException(response.statusCode());
        }

        var reader = response.body();
        List<Repair> allRepairs = JsonbBuilder
                .create(new JsonbConfig().withAdapters(new RepairAdapter()))
                .fromJson(reader, new ArrayList<Repair>(){}.getClass().getGenericSuperclass());

        List<Repair> archiveRepairs = new ArrayList<>();
        for (Repair repair : allRepairs) {
            if (repair.getClient().getArchive()) {
                archiveRepairs.add(repair);
            }
        }
        return archiveRepairs;
    }

    public List<Repair> getClientsPresentRepairs(UUID uuid) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(repairApi + "/repairs"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new WebApplicationException(response.statusCode());
        }

        var reader = response.body();
        List<Repair> allRepairs = JsonbBuilder
                .create(new JsonbConfig().withAdapters(new RepairAdapter()))
                .fromJson(reader, new ArrayList<Repair>(){}.getClass().getGenericSuperclass());

        List<Repair> presentRepairs = new ArrayList<>();
        for (Repair repair : allRepairs) {
            if (!repair.getClient().getArchive()) {
                presentRepairs.add(repair);
            }
        }
        return presentRepairs;
    }

    public List<Repair> getAllRepairs() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(repairApi + "/repairs"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new WebApplicationException(response.statusCode());
        }

        var reader = response.body();
        return JsonbBuilder
                .create(new JsonbConfig().withAdapters(new RepairAdapter()))
                .fromJson(reader, new ArrayList<Repair>(){}.getClass().getGenericSuperclass());
    }

    public Repair updateRepair(UUID uuid, Repair repair) throws URISyntaxException, IOException, InterruptedException {
        var json = JsonbBuilder
                .create(new JsonbConfig().withAdapters(new RepairAdapter()))
                .toJson(repair);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(repairApi + "/id/" + uuid))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        var reader = response.body();
        return JsonbBuilder
                .create(new JsonbConfig().withAdapters(new RepairAdapter()))
                .fromJson(reader, Repair.class);
    }
}
