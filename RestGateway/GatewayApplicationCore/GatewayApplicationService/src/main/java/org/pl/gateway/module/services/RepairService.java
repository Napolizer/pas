package org.pl.gateway.module.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.pl.gateway.module.model.Repair;
import org.pl.gateway.module.userinterface.repair.ReadRepairUseCases;
import org.pl.repair.module.controllers.RepairController;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@ApplicationScoped
public class RepairService implements ReadRepairUseCases {
    @Inject
    private RepairController repairController;

    public Repair add(Repair repair) {
        return null;
    }

    public Repair get(UUID id) {
        return null;
    }

    public String getInfo(UUID id) {
        return "";
    }

    public List<Repair> getAllClientRepairs(UUID clientId) {
        return null;
    }

    public boolean isRepairArchive(UUID id) {
        return true;
    }
    public Repair archivize(UUID id) {
        return null;
    }

    public Repair repair(UUID id) {
        return null;
    }

    public int getPresentSize() {
        return 0;
    }

    public int getArchiveSize() {
        return 0;
    }

    public List<Repair> getClientsPastRepairs(UUID uuid) {
        return null;
    }

    public List<Repair> getClientsPresentRepairs(UUID uuid) {
        return null;
    }

    public List<Repair> getAllRepairs() {
        return null;
    }

    public Repair updateRepair(UUID uuid, Repair repair) {
        return null;
    }
}
