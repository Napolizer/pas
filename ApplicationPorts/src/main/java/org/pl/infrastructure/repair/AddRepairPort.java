package org.pl.infrastructure.repair;

import org.pl.adapter.data.model.RepairEnt;

public interface AddRepairPort {
    RepairEnt createRepair(RepairEnt repair);
}
