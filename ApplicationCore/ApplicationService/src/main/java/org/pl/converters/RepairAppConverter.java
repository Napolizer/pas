package org.pl.converters;

import jakarta.enterprise.context.ApplicationScoped;
import org.pl.model.Repair;
import org.pl.model.RepairApp;

@ApplicationScoped
public class RepairAppConverter {
    public RepairApp convert(Repair repair) {
        try {
            RepairApp repairApp = new RepairApp();
            repairApp.setId(repair.getId());
            repairApp.setArchive(repair.getArchive());
            repairApp.setClient(repair.getClient());
            repairApp.setHardware(repair.getHardware());
            repairApp.setDateRange(repair.getDateRange());
            return repairApp;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Repair convert(RepairApp repairApp) {
        try {
            Repair repair = new Repair();
            repairApp.setId(repairApp.getId());
            repairApp.setArchive(repairApp.getArchive());
            repairApp.setClient(repairApp.getClient());
            repairApp.setHardware(repairApp.getHardware());
            repairApp.setDateRange(repairApp.getDateRange());
            return repair;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
