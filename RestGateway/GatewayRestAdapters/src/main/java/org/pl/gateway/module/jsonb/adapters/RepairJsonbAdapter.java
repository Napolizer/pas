package org.pl.gateway.module.jsonb.adapters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.json.bind.adapter.JsonbAdapter;
import org.pl.gateway.module.model.ClientRest;
import org.pl.gateway.module.model.DateRangeRest;
import org.pl.gateway.module.model.HardwareRest;
import org.pl.gateway.module.model.RepairRest;
import org.pl.gateway.module.ports.userinterface.client.ReadClientUseCases;
import org.pl.gateway.module.ports.userinterface.hardware.ReadHardwareUseCases;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@ApplicationScoped
public class RepairJsonbAdapter implements JsonbAdapter<RepairRest, JsonValue> {
    @Inject
    private ReadHardwareUseCases readHardwareUseCases;
    @Inject
    private ReadClientUseCases readClientUseCases;
    @Override
    public JsonValue adaptToJson(RepairRest repair) {
        var json = Json.createObjectBuilder();
        if (repair.getId() != null) {
            json.add("id", repair.getId().toString());
        }
        json.add("archive", repair.getArchive());

        Instant instant = repair.getDateRange().getStartDate().toInstant();
        LocalDateTime ldt = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        json.add("startDate", ldt.format(formatter));
        if (repair.getDateRange().getEndDate() != null) {
            instant = repair.getDateRange().getEndDate().toInstant();
            ldt = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
            json.add("endDate", ldt.format(formatter));
        }

        JsonbConfig config = new JsonbConfig().
                withFormatting(true);
        Jsonb jsonb = JsonbBuilder.newBuilder()
                .withConfig(config)
                .build();
        try (JsonReader jsonClientReader = Json.createReader(new StringReader(jsonb.toJson(repair.getClient())))) {
            JsonObject client = jsonClientReader.readObject();
            json.add("client",  client);
            try (JsonReader jsonHardwareReader = Json.createReader(new StringReader(jsonb.toJson(repair.getHardware())))) {
                JsonObject hardware = jsonHardwareReader.readObject();
                json.add("hardware", hardware);
            }
        }

        return json.build();
    }

    @Override
    public RepairRest adaptFromJson(JsonValue jsonValue) throws Exception {
        RepairRest output = new RepairRest();
        JsonObject jsonObject = jsonValue.asJsonObject();

        if (jsonObject.containsKey("id")) {
            output.setId(UUID.fromString(jsonObject.getString("id")));
        }

        if (jsonObject.containsKey("archive")) {
            output.setArchive(jsonObject.getBoolean("archive"));
        } else {
            output.setArchive(false);
        }

        if (jsonObject.containsKey("startDate") || jsonObject.containsKey("endDate")) {
            DateRangeRest DateRangeRest = new DateRangeRest();
            if (jsonObject.containsKey("startDate")) {
                DateRangeRest.setStartDate(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(jsonObject.getString("startDate")));
            }
            if (jsonObject.containsKey("endDate")) {
                DateRangeRest.setEndDate(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(jsonObject.getString("endDate")));
            }
            output.setDateRange(DateRangeRest);
        }

        if (jsonObject.containsKey("hardwareId")) {
            String hardwareId = jsonObject.getString("hardwareId");
            HardwareRest hardware = readHardwareUseCases.get(UUID.fromString(hardwareId));
            if (hardware == null) {
                throw new Exception();
            }
            output.setHardware(hardware);
        }
        if (jsonObject.containsKey("clientId")) {
            String clientId = jsonObject.getString("clientId");
            ClientRest client = readClientUseCases.get(UUID.fromString(clientId));
            if (client == null) {
                throw new Exception();
            }
            output.setClient(client);
        }

        return output;
    }
}
