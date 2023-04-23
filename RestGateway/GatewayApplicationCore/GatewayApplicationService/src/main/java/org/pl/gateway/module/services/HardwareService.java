package org.pl.gateway.module.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.ws.rs.WebApplicationException;
import lombok.RequiredArgsConstructor;
import org.pl.gateway.module.adapters.HardwareTypeAdapter;
import org.pl.gateway.module.model.Hardware;
import org.pl.gateway.module.model.HardwareType;

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
@ApplicationScoped
public class HardwareService {
    @Inject
    private HttpClient httpClient;

    private static final String repairApi = "https://localhost:8181/RestAdapters-1.0-SNAPSHOT/api";

    public Hardware add(Hardware hardware) throws URISyntaxException, IOException, InterruptedException {
        var json = JsonbBuilder
                .create(new JsonbConfig().withAdapters(new HardwareTypeAdapter())).
                toJson(hardware);


        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(repairApi + "/hardware"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 201) {
            throw new WebApplicationException(response.statusCode());
        }

        var reader = response.body();
        return JsonbBuilder
                .create(new JsonbConfig().withAdapters(new HardwareTypeAdapter())).
                fromJson(reader, Hardware.class);
    }

    public boolean isHardwareArchive(UUID id) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(repairApi +"/hardware/id/" + id))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        var reader = response.body();
        return JsonbBuilder
                .create(new JsonbConfig().withAdapters(new HardwareTypeAdapter())).
                fromJson(reader, Hardware.class).getArchive();
    }

    public Hardware get(UUID id) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(repairApi +"/hardware/id/" + id))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        var reader = response.body();
        return JsonbBuilder
                .create(new JsonbConfig().withAdapters(new HardwareTypeAdapter())).
                fromJson(reader, Hardware.class);
    }

    public String getInfo(UUID id) throws IOException, InterruptedException, URISyntaxException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(repairApi +"/hardware/id/" + id))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        var reader = response.body();
        return JsonbBuilder
                .create(new JsonbConfig().withAdapters(new HardwareTypeAdapter())).
                fromJson(reader, Hardware.class).toString();
    }

    public void archive(UUID id) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(repairApi + "/hardware/id/" + id))
                .DELETE()
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new WebApplicationException(response.statusCode());
        }
    }
    public int getPresentSize() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(repairApi + "/hardware/present"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        var reader = response.body();
        List<Hardware> presentHardware = JsonbBuilder
                .create(new JsonbConfig().withAdapters(new HardwareTypeAdapter()))
                .fromJson(reader, new ArrayList<Hardware>(){}.getClass().getGenericSuperclass());
        return presentHardware.size();
    }

    public int getArchiveSize() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(repairApi + "/hardwares"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        var reader = response.body();
        List<Hardware> allHardwares = JsonbBuilder
                .create(new JsonbConfig().withAdapters(new HardwareTypeAdapter()))
                .fromJson(reader, new ArrayList<Hardware>(){}.getClass().getGenericSuperclass());
        List<Hardware> archiveHardwares = new ArrayList<>();
        for (Hardware hardware : allHardwares) {
            if (hardware.getArchive()) {
                archiveHardwares.add(hardware);
            }
        }
        return archiveHardwares.size();
    }

    public List<Hardware> getAllHardwares() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(repairApi + "/hardwares"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        var reader = response.body();
        return JsonbBuilder
                .create(new JsonbConfig().withAdapters(new HardwareTypeAdapter()))
                .fromJson(reader, new ArrayList<Hardware>(){}.getClass().getGenericSuperclass());
    }

    public Hardware updateHardware(UUID uuid, Hardware hardware) throws URISyntaxException, IOException, InterruptedException {
        var json = JsonbBuilder
                .create(new JsonbConfig().withAdapters(new HardwareTypeAdapter())).
                toJson(hardware);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(repairApi + "/hardware/id/" + uuid))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new WebApplicationException(response.statusCode());
        }

        var reader = response.body();
        return JsonbBuilder
                .create(new JsonbConfig().withAdapters(new HardwareTypeAdapter())).
                fromJson(reader, Hardware.class);
    }

    public HardwareType getHardwareTypeById(UUID uuid) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(repairApi + "/hardwares"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        var reader = response.body();
        List<Hardware> allHardwares = JsonbBuilder
                .create(new JsonbConfig().withAdapters(new HardwareTypeAdapter()))
                .fromJson(reader, new ArrayList<Hardware>(){}.getClass().getGenericSuperclass());
        for (Hardware hardware : allHardwares) {
            if (hardware.getHardwareType().getId() == uuid) {
                return hardware.getHardwareType();
            }
        }
        return null;
    }

    public List<Hardware> getAllPresentHardware() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(repairApi + "/hardware/present"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        var reader = response.body();
        return JsonbBuilder
                .create(new JsonbConfig().withAdapters(new HardwareTypeAdapter()))
                .fromJson(reader, new ArrayList<Hardware>(){}.getClass().getGenericSuperclass());
    }

    public List<Hardware> getAllPresentHardwareFilter(String substr) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(repairApi + "/hardware/present/filter/" + substr))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        var reader = response.body();
        return JsonbBuilder
                .create(new JsonbConfig().withAdapters(new HardwareTypeAdapter()))
                .fromJson(reader, new ArrayList<Hardware>(){}.getClass().getGenericSuperclass());
    }
}
