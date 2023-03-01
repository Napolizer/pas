package org.pl.infrastructure;

import org.pl.adapter.data.model.HardwareEnt;

import java.util.UUID;

public interface UpdateHardwarePort {
    HardwareEnt updateHardware(UUID uuid, HardwareEnt hardware);
}
