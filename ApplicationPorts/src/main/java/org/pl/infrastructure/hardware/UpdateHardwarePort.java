package org.pl.infrastructure.hardware;

import org.pl.adapter.data.model.HardwareEnt;

import java.util.UUID;

public interface UpdateHardwarePort {
    HardwareEnt updateHardware(UUID uuid, HardwareEnt hardware);
}
