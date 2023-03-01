package org.pl.interfaces;

import jakarta.json.JsonValue;
import jakarta.json.bind.adapter.JsonbAdapter;
import org.pl.model.ClientType;

public interface ClientTypeAdapterInterface extends JsonbAdapter<ClientType, JsonValue> {
    JsonValue adaptToJson(ClientType clientType) throws Exception;
    ClientType adaptFromJson(JsonValue jsonValue) throws Exception;
}
