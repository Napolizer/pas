package org.pl.adapters;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import org.pl.interfaces.ClientTypeAdapterInterface;
import org.pl.model.Basic;
import org.pl.model.ClientType;
import org.pl.model.Premium;
import org.pl.model.Vip;

public class ClientTypeAdapter implements ClientTypeAdapterInterface {
    @Override
    public JsonValue adaptToJson(ClientType clientType) throws Exception {
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
    public ClientType adaptFromJson(JsonValue jsonValue) throws Exception {
        ClientType output;
        JsonObject jsonObject = jsonValue.asJsonObject();

        try {
            output = switch (jsonObject.getString("type").toUpperCase()) {
                case "BASIC" -> new Basic();
                case "PREMIUM" -> new Premium();
                case "VIP" -> new Vip();
                default -> null;
            };
        } catch (NullPointerException e) {
            output = null;
        }

        return output;
    }
}
