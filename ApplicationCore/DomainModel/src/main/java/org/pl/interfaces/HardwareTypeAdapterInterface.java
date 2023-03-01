package org.pl.interfaces;

import jakarta.json.JsonValue;
import jakarta.json.bind.adapter.JsonbAdapter;
import org.pl.model.HardwareType;

public interface HardwareTypeAdapterInterface extends JsonbAdapter<HardwareType, JsonValue> {
    JsonValue adaptToJson(HardwareType hardwareType) throws Exception;
    HardwareType adaptFromJson(JsonValue jsonValue);
}
