package org.pl.infrastructure.repair;

import org.pl.adapter.data.model.RepairEnt;

import java.util.UUID;

public interface UpdateRepairPort {
    RepairEnt updateRepair(UUID uuid, RepairEnt repair);
}
