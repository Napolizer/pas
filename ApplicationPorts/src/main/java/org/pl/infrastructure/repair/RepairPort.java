package org.pl.infrastructure.repair;

import org.pl.adapter.data.model.RepairEnt;

import java.util.UUID;

public interface RepairPort {
    RepairEnt repair(UUID uuid);
}
