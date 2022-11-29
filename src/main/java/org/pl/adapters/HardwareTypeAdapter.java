package org.pl.adapters;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import jakarta.json.bind.adapter.JsonbAdapter;
import org.pl.model.*;

import java.util.UUID;

public class HardwareTypeAdapter implements JsonbAdapter<HardwareType, JsonValue> {
    @Override
    public JsonValue adaptToJson(HardwareType hardwareType) throws Exception {
        var json = Json.createObjectBuilder();
        if (hardwareType.getId() != null) {
            json.add("id", hardwareType.getId().toString());
        }

        if (hardwareType.getType() == null || hardwareType.getCondition() == null) {
            return null;
        }

        json.add("condition", hardwareType.getCondition().toString());
        json.add("type", hardwareType.getType());

        return json.build();
    }

    @Override
    public HardwareType adaptFromJson(JsonValue jsonValue) {
        HardwareType output;
        JsonObject jsonObject = jsonValue.asJsonObject();

        if (!jsonObject.containsKey("type") || !jsonObject.containsKey("condition")) {
            return null;
        }
        try {
            output = switch (jsonObject.getString("type").toUpperCase()) {
                case "CONSOLE" -> new Console(Condition.FINE);
                case "COMPUTER" -> new Computer(Condition.FINE);
                case "MONITOR" -> new Monitor(Condition.FINE);
                case "PHONE" -> new Phone(Condition.FINE);
                default -> null;
            };
        } catch (NullPointerException e) {
            return null;
        }

        if (output != null) {
            output.setCondition(Condition.valueOf(jsonObject.getString("condition")));
        }
        return output;
    }
}
