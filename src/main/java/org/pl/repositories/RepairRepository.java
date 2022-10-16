package org.pl.repositories;


import lombok.AllArgsConstructor;
import org.pl.exceptions.ClientException;
import org.pl.exceptions.HardwareException;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Client;
import org.pl.model.Repair;

import java.util.ArrayList;

@AllArgsConstructor
public class RepairRepository extends Repository<Repair>{

    public RepairRepository(ArrayList<Repair> elements) {
        super(elements);
    }

    public int getClientRepairs(Client client) {
        int counter = 0;
        for (int i = 0; i < getElements().size(); i++) {
            if (elements.get(i).getClient() == client) {
                counter++;
            }
        }
        return counter;
    }

    public void repair(int ID) throws RepositoryException, HardwareException, ClientException {
        if (get(ID).getClient().isArchive()) {
            throw new RepositoryException(RepositoryException.REPOSITORY_CLIENT_IS_ARCHIVE_EXCEPTION);
        }
        if (get(ID).getHardware().isArchive()) {
            throw new RepositoryException(RepositoryException.REPOSITORY_HARDWARE_IS_ARCHIVE_EXCEPTION);
        }
        if (get(ID).getClient().getClientType().getMaxRepairs() <= getClientRepairs(get(ID).getClient())) {
            throw new RepositoryException(RepositoryException.REPOSITORY_MAX_REPAIRS_EXCEED);
        }

        get(ID).getClient().setArchive(true);
        get(ID).getHardware().setArchive(true);
        get(ID).setArchive(true);

        for (int i = 0; i < getElements().size(); i++) {
            if (getElements().get(i).getClient() == get(ID).getClient() && getElements().get(i).getID() != ID &&
                    !getElements().get(i).isArchive()) {
                get(ID).getClient().setArchive(false);
                break;
            }
        }
        float price = (float) get(ID).getHardware().getHardwareType().calculateRepairCost(get(ID).getHardware().getPrice());
        get(ID).getClient().changeBalance(-(price - get(ID).getClient().calculateDiscount((int) price)));
    }
}
