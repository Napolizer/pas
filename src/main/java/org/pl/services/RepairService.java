package org.pl.services;

import org.pl.model.Client;
import org.pl.model.Hardware;
import org.pl.model.Repair;

public class RepairService {
    private RepairRepository repairRepository;

    public Repair add(Client client, Hardware hardware) {
        Repair repair = Repair.builder()
                .id(repairRepository.getSize())
                .client(client)
                .hardware(hardware)
                .build();
        repairRepository.add(repair);
        return repair;
    }

    public Repair get(int ID) {
        return repairRepository.get(ID);
    }

    public int getArchiveSize() {
        return repairRepository.getSize(false);
    }

    public String getInfo(int ID) {
        return repairRepository.get(ID).toString();
    }

    public int getPresentSize() {
        return repairRepository.getSize(true);
    }

    public String getReport() {
        return repairRepository.toString();
    }

    public void repair(int ID) {
        repairRepository.repair(ID);
    }
}
