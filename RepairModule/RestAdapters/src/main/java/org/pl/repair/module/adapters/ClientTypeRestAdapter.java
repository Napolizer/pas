package org.pl.repair.module.adapters;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import jakarta.json.bind.adapter.JsonbAdapter;
import org.pl.repair.module.model.BasicRest;
import org.pl.repair.module.model.ClientTypeRest;
import org.pl.repair.module.model.PremiumRest;
import org.pl.repair.module.model.VipRest;


public class ClientTypeRestAdapter implements JsonbAdapter<ClientTypeRest, JsonValue> {
    @Override
    public JsonValue adaptToJson(ClientTypeRest clientType) {
        var json = Json.createObjectBuilder();
        if (clientType.getId() != null) {
            json.add("id", clientType.getId().toString());
        }
        if (clientType.getType() == null || clientType.getFactor() == null || clientType.getMaxRepairs() == null) {
            return null;
        }

        json.add("type", clientType.getType());
        json.add("factor",  clientType.getFactor());
        json.add("maxRepairs", clientType.getMaxRepairs());

        return json.build();
    }

    @Override
    public ClientTypeRest adaptFromJson(JsonValue jsonValue) {
        ClientTypeRest output;
        JsonObject jsonObject = jsonValue.asJsonObject();

        try {
            output = switch (jsonObject.getString("type").toUpperCase()) {
                case "BASIC" -> new BasicRest();
                case "PREMIUM" -> new PremiumRest();
                case "VIP" -> new VipRest();
                default -> null;
            };
        } catch (NullPointerException e) {
            output = null;
        }

        return output;
    }
}
