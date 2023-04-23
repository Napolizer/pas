package org.pl.repair.module.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.metrics.annotation.Metric;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.pl.repair.module.model.exceptions.ClientException;
import org.pl.repair.module.model.exceptions.HardwareException;
import org.pl.repair.module.model.exceptions.RepositoryException;
import org.pl.repair.module.model.Repair;
import org.pl.repair.module.infrastructure.repair.ReadRepairPort;
import org.pl.repair.module.infrastructure.repair.WriteRepairPort;
import org.pl.repair.module.userinterface.repair.ReadRepairUseCases;
import org.pl.repair.module.userinterface.repair.WriteRepairUseCases;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@ApplicationScoped
@Counted(name = "RepairService_Counter")
public class RepairService implements ReadRepairUseCases, WriteRepairUseCases {
    @Inject
    private ReadRepairPort readRepairPort;

    @Inject
    private WriteRepairPort writeRepairPort;

    @Inject
    @Metric
    private MetricRegistry metricRegistry;

    @Counted(name = "RepairService.add_Counter")
    @Timed(name = "RepairService.add_Timer")
    public Repair add(Repair repair) throws RepositoryException {
        return writeRepairPort.createRepair(repair);
    }

    @Counted(name = "RepairService.get_Counter")
    @Timed(name = "RepairService.get_Timer")
    public Repair get(UUID id) throws RepositoryException {
        return readRepairPort.getRepair(id);
    }

    @Counted(name = "RepairService.getInfo_Counter")
    @Timed(name = "RepairService.getInfo_Timer")
    public String getInfo(UUID id) throws RepositoryException {
        return readRepairPort.getRepair(id).toString();
    }

    @Gauge(unit = "number", name = "RepairService.getAllClientRepairs_Gauge")
    @Counted(name = "RepairService.getAllClientRepairs_Counter")
    @Timed(name = "RepairService.getAllClientRepairs_Timer")
    public List<Repair> getAllClientRepairs(UUID clientId) throws RepositoryException {
        return readRepairPort.getClientRepairs(clientId);
    }

    @Counted(name = "RepairService.isRepairArchive_Counter")
    @Timed(name = "RepairService.isRepairArchive_Timer")
    public boolean isRepairArchive(UUID id) throws RepositoryException {
        return readRepairPort.getRepair(id).getArchive();
    }

    @Counted(name = "RepairService.archive_Counter")
    @Timed(name = "RepairService.archive_Timer")
    public Repair archivize(UUID id) throws RepositoryException {
        return writeRepairPort.deleteRepair(id);
    }

    @Counted(name = "RepairService.repair_Counter")
    @Timed(name = "RepairService.repair_Timer")
    public Repair repair(UUID id) throws HardwareException, RepositoryException, ClientException {
        return writeRepairPort.repair(id);
    }

    @Gauge(unit = "number", name = "RepairService.getPresentSize_Gauge")
    @Counted(name = "RepairService.getPresentSize_Counter")
    @Timed(name = "RepairService.getPresentSize_Timer")
    public int getPresentSize() {
        return readRepairPort.getRepairList(false).size();
    }

    @Gauge(unit = "number", name = "RepairService.getArchiveSize_Gauge")
    @Counted(name = "RepairService.getArchiveSize_Counter")
    @Timed(name = "RepairService.getArchiveSize_Timer")
    public int getArchiveSize() {
        return readRepairPort.getRepairList(true).size();
    }

    @Gauge(unit = "number", name = "RepairService.getClientsPastRepairs_Gauge")
    @Counted(name = "RepairService.getClientsPastRepairs_Counter")
    @Timed(name = "RepairService.getClientsPastRepairs_Timer")
    public List<Repair> getClientsPastRepairs(UUID uuid) throws RepositoryException {
        List<Repair> pastRepairs = new ArrayList<>();
        List<Repair> repairs = readRepairPort.getClientRepairs(uuid);
        for (Repair repair : repairs) {
            if (repair.getArchive()) {
                pastRepairs.add(repair);
            }
        }
        return pastRepairs;
    }

    @Gauge(unit = "number", name = "RepairService.getClientsPresentRepairs_Gauge")
    @Counted(name = "RepairService.getClientsPresentRepairs_Counter")
    @Timed(name = "RepairService.getClientsPresentRepairs_Timer")
    public List<Repair> getClientsPresentRepairs(UUID uuid) throws RepositoryException {
        List<Repair> presentRepairs = new ArrayList<>();
        List<Repair> repairs = readRepairPort.getClientRepairs(uuid);
        for (Repair repair : repairs) {
            if (!repair.getArchive()) {
                presentRepairs.add(repair);
            }
        }
        return presentRepairs;
    }

    @Gauge(unit = "number", name = "RepairService.getAllRepairs_Gauge")
    @Counted(name = "RepairService.getAllRepairs_Counter")
    @Timed(name = "RepairService.getAllRepairs_Timer")
    public List<Repair> getAllRepairs() {
        return readRepairPort.getAllRepairs();
    }


    @Counted(name = "RepairService.updateRepair_Counter")
    @Timed(name = "RepairService.updateRepair_Timer")
    public Repair updateRepair(UUID uuid, Repair repair) throws RepositoryException {
        return writeRepairPort.updateRepair(uuid, repair);
    }
}
