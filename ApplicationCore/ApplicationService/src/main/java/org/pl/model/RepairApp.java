package org.pl.model;

import jakarta.json.bind.annotation.JsonbTypeAdapter;
import org.pl.adapters.RepairAppAdapter;

@JsonbTypeAdapter(RepairAppAdapter.class)
public class RepairApp extends Repair {

}
