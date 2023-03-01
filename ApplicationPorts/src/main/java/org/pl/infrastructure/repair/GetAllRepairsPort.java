package org.pl.infrastructure.repair;

import org.pl.model.Repair;

import java.util.List;

public interface GetAllRepairsPort {
    List<Repair> getAllRepairs();
}
