package org.pl.repair.module.adapters;

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
import org.pl.repair.module.converters.ClientConverter;
import org.pl.repair.module.converters.DateRangeConverter;
import org.pl.repair.module.converters.HardwareConverter;
import org.pl.repair.module.model.Client;
import org.pl.repair.module.model.DateRange;
import org.pl.repair.module.model.Hardware;
import org.pl.repair.module.model.RepairRest;
import org.pl.repair.module.userinterface.client.ReadClientUseCases;
import org.pl.repair.module.userinterface.hardware.ReadHardwareUseCases;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@ApplicationScoped
public class RepairAdapter implements JsonbAdapter<RepairRest, JsonValue> {
    @Inject
    private DateRangeConverter dateRangeConverter;
    @Inject
    private ReadHardwareUseCases readHardwareUseCases;
    @Inject
    private HardwareConverter hardwareConverter;
    @Inject
    private ReadClientUseCases readClientUseCases;
    @Inject
    private ClientConverter clientConverter;
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
            DateRange dateRange = new DateRange();
            if (jsonObject.containsKey("startDate")) {
                dateRange.setStartDate(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(jsonObject.getString("startDate")));
            }
            if (jsonObject.containsKey("endDate")) {
                dateRange.setEndDate(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(jsonObject.getString("endDate")));
            }
            output.setDateRange(dateRangeConverter.convert(dateRange));
        }

        if (jsonObject.containsKey("hardwareId")) {
            String hardwareId = jsonObject.getString("hardwareId");
            Hardware hardware = readHardwareUseCases.get(UUID.fromString(hardwareId));
            if (hardware == null) {
                throw new Exception();
            }
            output.setHardware(hardwareConverter.convert(hardware));
        }
        if (jsonObject.containsKey("clientId")) {
            String clientId = jsonObject.getString("clientId");
            Client client = readClientUseCases.get(UUID.fromString(clientId));
            if (client == null) {
                throw new Exception();
            }
            output.setClient(clientConverter.convert(client));
        }

        return output;
    }
}
