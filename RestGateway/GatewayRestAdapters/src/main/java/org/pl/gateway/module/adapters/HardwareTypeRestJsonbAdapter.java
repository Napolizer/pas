package org.pl.gateway.module.adapters;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import jakarta.json.bind.adapter.JsonbAdapter;
import org.pl.gateway.module.model.*;


public class HardwareTypeRestJsonbAdapter implements JsonbAdapter<HardwareTypeRest, JsonValue> {
    @Override
    public JsonValue adaptToJson(HardwareTypeRest hardwareType) {
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
    public HardwareTypeRest adaptFromJson(JsonValue jsonValue) {
        HardwareTypeRest output;
        JsonObject jsonObject = jsonValue.asJsonObject();

        if (!jsonObject.containsKey("type") || !jsonObject.containsKey("condition")) {
            return null;
        }
        try {
            output = switch (jsonObject.getString("type").toUpperCase()) {
                case "CONSOLE" -> new ConsoleRest(ConditionRest.FINE);
                case "COMPUTER" -> new ComputerRest(ConditionRest.FINE);
                case "MONITOR" -> new MonitorRest(ConditionRest.FINE);
                case "PHONE" -> new PhoneRest(ConditionRest.FINE);
                default -> null;
            };
        } catch (NullPointerException e) {
            return null;
        }

        if (output != null) {
            output.setCondition(ConditionRest.valueOf(jsonObject.getString("condition")));
        }
        return output;
    }
}
