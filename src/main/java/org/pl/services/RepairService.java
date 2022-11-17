package org.pl.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.pl.exceptions.ClientException;
import org.pl.exceptions.HardwareException;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Client;
import org.pl.model.Hardware;
import org.pl.model.Repair;
import org.pl.repositories.RepairRepository;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class RepairService {
    @Inject
    private RepairRepository repairRepository;

    public Repair create(Client client, Hardware hardware) throws RepositoryException {
        return repairRepository.saveRepair(
                Repair.builder()
                        .client(client)
                        .hardware(hardware)
                        .build());
    }

    public Repair create(Repair repair) throws RepositoryException {
        return repairRepository.saveRepair(repair);
    }

    public Repair get(UUID id) throws RepositoryException {
        return repairRepository.getRepairById(id);
    }

    public List<Repair> getAllClientRepairs(UUID clientId) {
        return repairRepository.getClientRepairs(clientId);
    }

    public Repair archivize(UUID id) throws RepositoryException {
        return repairRepository.deleteRepair(id);
    }

    public void repair(UUID id) throws HardwareException, RepositoryException, ClientException {
        Repair repair = repairRepository.getRepairById(id);
        if (repair.isArchive() || repair.getClient().isArchive() || repair.getHardware().isArchive()) {
            throw new RepositoryException(RepositoryException.REPOSITORY_ARCHIVE_EXCEPTION);
        }

        repair.getHardware().setArchive(true);
        repair.setArchive(true);

        double price = repair.getHardware().getHardwareType().calculateRepairCost(repair.getHardware().getPrice());
        repair.getClient().setBalance(
                repair.getClient().getBalance() - price
        );
    }
}
