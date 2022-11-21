package org.pl.controllers;

import jakarta.json.Json;
import jakarta.json.JsonValue;
import jakarta.json.bind.adapter.JsonbAdapter;
import org.pl.model.Condition;
import org.pl.model.Console;
import org.pl.model.HardwareType;

public class HardwareTypeAdapters implements JsonbAdapter<HardwareType, JsonValue> {

    @Override
    public JsonValue adaptToJson(HardwareType obj) throws Exception {
        return Json.createValue(obj.toString());
    }

    @Override
    public HardwareType adaptFromJson(JsonValue obj) throws Exception {
        return Console.builder()
                .condition(Condition.FINE)
                .build();
    }
}
