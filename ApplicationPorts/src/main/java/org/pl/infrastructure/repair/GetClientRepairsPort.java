package org.pl.infrastructure.repair;

import org.pl.adapter.data.model.RepairEnt;

import java.util.List;
import java.util.UUID;

public interface GetClientRepairsPort {
    List<RepairEnt> getClientRepairs(UUID clientId);
}
