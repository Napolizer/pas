package org.pl.gateway.module.aggegates;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.ws.rs.WebApplicationException;
import lombok.RequiredArgsConstructor;
import org.pl.gateway.module.jsonb.adapters.HardwareTypeRestJsonbAdapter;
import org.pl.gateway.module.model.HardwareRest;
import org.pl.gateway.module.model.HardwareTypeRest;
import org.pl.gateway.module.ports.userinterface.hardware.ReadHardwareUseCases;
import org.pl.gateway.module.ports.userinterface.hardware.WriteHardwareUseCases;

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
public class HardwareRestAdapter implements ReadHardwareUseCases, WriteHardwareUseCases {
    @Inject
    private HttpClient httpClient;

    private static final String repairApi = "https://localhost:8181/RestAdapters-1.0-SNAPSHOT/api";

    public HardwareRest add(HardwareRest HardwareRest) throws URISyntaxException, IOException, InterruptedException {
        var json = JsonbBuilder
                .create(new JsonbConfig().withAdapters(new HardwareTypeRestJsonbAdapter())).
                toJson(HardwareRest);


        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(repairApi + "/HardwareRest"))
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
    }

    public boolean isHardwareArchive(UUID id) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(repairApi +"/HardwareRest/id/" + id))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        var reader = response.body();
        return JsonbBuilder
                .create(new JsonbConfig().withAdapters(new HardwareTypeRestJsonbAdapter())).
                fromJson(reader, HardwareRest.class).getArchive();
    }

    public HardwareRest get(UUID id) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(repairApi +"/HardwareRest/id/" + id))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        var reader = response.body();
        return JsonbBuilder
                .create(new JsonbConfig().withAdapters(new HardwareTypeRestJsonbAdapter())).
                fromJson(reader, HardwareRest.class);
    }

    public String getInfo(UUID id) throws IOException, InterruptedException, URISyntaxException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(repairApi +"/HardwareRest/id/" + id))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        var reader = response.body();
        return JsonbBuilder
                .create(new JsonbConfig().withAdapters(new HardwareTypeRestJsonbAdapter())).
                fromJson(reader, HardwareRest.class).toString();
    }

    public void archive(UUID id) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(repairApi + "/HardwareRest/id/" + id))
                .DELETE()
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new WebApplicationException(response.statusCode());
        }
    }
    public int getPresentSize() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(repairApi + "/HardwareRest/present"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        var reader = response.body();
        List<HardwareRest> presentHardwareRest = JsonbBuilder
                .create(new JsonbConfig().withAdapters(new HardwareTypeRestJsonbAdapter()))
                .fromJson(reader, new ArrayList<HardwareRest>(){}.getClass().getGenericSuperclass());
        return presentHardwareRest.size();
    }

    public int getArchiveSize() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(repairApi + "/HardwareRests"))
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
    }

    public List<HardwareRest> getAllHardwares() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(repairApi + "/HardwareRests"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        var reader = response.body();
        return JsonbBuilder
                .create(new JsonbConfig().withAdapters(new HardwareTypeRestJsonbAdapter()))
                .fromJson(reader, new ArrayList<HardwareRest>(){}.getClass().getGenericSuperclass());
    }

    public HardwareRest updateHardware(UUID uuid, HardwareRest HardwareRest) throws URISyntaxException, IOException, InterruptedException {
        var json = JsonbBuilder
                .create(new JsonbConfig().withAdapters(new HardwareTypeRestJsonbAdapter())).
                toJson(HardwareRest);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(repairApi + "/HardwareRest/id/" + uuid))
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
    }

    public HardwareTypeRest getHardwareTypeById(UUID uuid) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(repairApi + "/HardwareRests"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        var reader = response.body();
        List<HardwareRest> allHardwareRests = JsonbBuilder
                .create(new JsonbConfig().withAdapters(new HardwareTypeRestJsonbAdapter()))
                .fromJson(reader, new ArrayList<HardwareRest>(){}.getClass().getGenericSuperclass());
        for (HardwareRest HardwareRest : allHardwareRests) {
            if (HardwareRest.getHardwareType().getId() == uuid) {
                return HardwareRest.getHardwareType();
            }
        }
        return null;
    }

    public List<HardwareRest> getAllPresentHardware() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(repairApi + "/HardwareRest/present"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        var reader = response.body();
        return JsonbBuilder
                .create(new JsonbConfig().withAdapters(new HardwareTypeRestJsonbAdapter()))
                .fromJson(reader, new ArrayList<HardwareRest>(){}.getClass().getGenericSuperclass());
    }

    public List<HardwareRest> getAllPresentHardwareFilter(String substr) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(repairApi + "/HardwareRest/present/filter/" + substr))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        var reader = response.body();
        return JsonbBuilder
                .create(new JsonbConfig().withAdapters(new HardwareTypeRestJsonbAdapter()))
                .fromJson(reader, new ArrayList<HardwareRest>(){}.getClass().getGenericSuperclass());
    }
}
