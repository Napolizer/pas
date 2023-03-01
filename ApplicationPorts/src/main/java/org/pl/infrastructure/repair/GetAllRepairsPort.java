package org.pl.infrastructure.repair;

import org.pl.adapter.data.model.RepairEnt;

import java.util.List;

public interface GetAllRepairsPort {
    List<RepairEnt> getAllRepairs();
}
