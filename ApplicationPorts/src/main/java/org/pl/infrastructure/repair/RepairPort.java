package org.pl.infrastructure.repair;

import org.pl.model.Repair;

import java.util.UUID;

public interface RepairPort {
    Repair repair(UUID uuid);
}
