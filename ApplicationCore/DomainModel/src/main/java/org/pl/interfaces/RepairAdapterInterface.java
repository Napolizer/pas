package org.pl.interfaces;

import jakarta.json.JsonValue;
import jakarta.json.bind.adapter.JsonbAdapter;
import org.pl.model.Repair;

public interface RepairAdapterInterface extends JsonbAdapter<Repair, JsonValue> {
    JsonValue adaptToJson(Repair repair) throws Exception;
    Repair adaptFromJson(JsonValue jsonValue) throws Exception;
}
