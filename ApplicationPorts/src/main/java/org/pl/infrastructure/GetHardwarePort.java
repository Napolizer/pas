package org.pl.infrastructure;

import org.pl.adapter.data.model.HardwareEnt;

import java.util.UUID;

public interface GetHardwarePort {
    HardwareEnt getHardware(UUID uuid);
}
