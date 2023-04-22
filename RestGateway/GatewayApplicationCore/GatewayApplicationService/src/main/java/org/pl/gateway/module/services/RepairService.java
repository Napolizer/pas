package org.pl.gateway.module.services;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import org.pl.gateway.module.model.Repair;
import org.pl.gateway.module.userinterface.repair.ReadRepairUseCases;

import java.util.*;

@AllArgsConstructor
@ApplicationScoped
public class RepairService implements ReadRepairUseCases {
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
