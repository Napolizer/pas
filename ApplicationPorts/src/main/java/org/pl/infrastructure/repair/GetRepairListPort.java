package org.pl.infrastructure.repair;

import org.pl.adapter.data.model.RepairEnt;

import java.util.List;

public interface GetRepairListPort {
    List<RepairEnt> getRepairList(boolean condition);
}
