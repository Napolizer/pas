package org.pl.adapters;

import jakarta.inject.Inject;
import jakarta.json.*;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.json.bind.adapter.JsonbAdapter;
import org.pl.model.Client;
import org.pl.model.Hardware;
import org.pl.model.Repair;
import org.pl.repositories.ClientRepository;
import org.pl.repositories.HardwareRepository;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class RepairAdapter implements JsonbAdapter<Repair, JsonValue> {

    @Inject
    HardwareRepository hardwareRepository;
    @Inject
    ClientRepository clientRepository;

    @Override
    public JsonValue adaptToJson(Repair repair) throws Exception {
        var json = Json.createObjectBuilder();
        if (repair.getId() != null) {
            json.add("id", repair.getId().toString());
        }
        json.add("archive", repair.getArchive());
        json.add("startDate", repair.getStartDate().toString());
        json.add("endDate", repair.getEndDate().toString());

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
    public Repair adaptFromJson(JsonValue jsonValue) throws Exception {
        Repair output = new Repair();
        JsonObject jsonObject = jsonValue.asJsonObject();

        if (jsonObject.containsKey("id")) {
            output.setId(UUID.fromString(jsonObject.getString("id")));
        }

        if (jsonObject.containsKey("archive")) {
            output.setArchive(jsonObject.getBoolean("archive"));
        } else {
            output.setArchive(false);
        }

        output.setStartDate(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(jsonObject.getString("startDate")));
        output.setEndDate(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(jsonObject.getString("endDate")));

        String hardwareId = jsonObject.getString("hardwareId");
        String clientId = jsonObject.getString("clientId");

        Hardware hardware = hardwareRepository.getHardwareById(UUID.fromString(hardwareId));
        Client client = clientRepository.getClientById(UUID.fromString(clientId));

        output.setHardware(hardware);
        output.setClient(client);

        return output;
    }
}
