package org.pl.adapters;

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
import org.pl.converters.ClientConverter;
import org.pl.converters.DateRangeConverter;
import org.pl.converters.HardwareConverter;
import org.pl.model.Client;
import org.pl.model.DateRange;
import org.pl.model.Hardware;
import org.pl.model.RepairRest;
import org.pl.userinterface.client.ReadClientQueries;
import org.pl.userinterface.hardware.ReadHardwareQueries;

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
    private ReadHardwareQueries readHardwareQueries;
    @Inject
    private HardwareConverter hardwareConverter;
    @Inject
    private ReadClientQueries readClientQueries;
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
            Hardware hardware = readHardwareQueries.get(UUID.fromString(hardwareId));
            if (hardware == null) {
                throw new Exception();
            }
            output.setHardware(hardwareConverter.convert(hardware));
        }
        if (jsonObject.containsKey("clientId")) {
            String clientId = jsonObject.getString("clientId");
            Client client = readClientQueries.get(UUID.fromString(clientId));
            if (client == null) {
                throw new Exception();
            }
            output.setClient(clientConverter.convert(client));
        }

        return output;
    }
}
