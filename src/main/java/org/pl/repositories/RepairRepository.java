package org.pl.repositories;


import lombok.Builder;
import lombok.Data;
import org.pl.exceptions.ClientException;
import org.pl.exceptions.HardwareException;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Client;
import org.pl.model.Repair;

import java.util.ArrayList;

@Data
@Builder
public class RepairRepository {

    private final ArrayList<Repair> elements;

    public void add(Repair element) throws RepositoryException {
        if (element == null) {
            throw new RepositoryException(RepositoryException.REPOSITORY_ADD_INVALID_EXCEPTION);
        }
        for (Repair t : elements) {
            if (t == element) {
                t.setArchive(false);
                return;
            }
        }
        elements.add(element);
    }
    public void archivise(int id) throws RepositoryException {
        for (Repair element : elements) {
            if (element.getID() == id && !element.isArchive()) {
                element.setArchive(true);
                return;
            }
        }
        throw new RepositoryException(RepositoryException.REPOSITORY_ARCHIVE_EXCEPTION);
    }

    public Repair get(int id) throws RepositoryException {
        if (id < 0) {
            throw new RepositoryException(RepositoryException.REPOSITORY_GET_INVALID_INPUT_EXCEPTION);
        }
        if (elements.isEmpty()) {
            throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
        }
        for (Repair element : elements) {
            if (element.getID() == id) {
                return element;
            }
        }
        throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
    }

    public int getSize(boolean present) throws RepositoryException {
        int output = 0;
        if (present) {
            for (int i = 0; i < elements.size(); i++) {
                if (!get(i).isArchive())
                    output++;
            }
        } else {
            for (int i = 0; i < elements.size(); i++) {
                if (get(i).isArchive())
                    output++;
            }
        }
        return output;
    }

    public boolean isArchive(int id) throws RepositoryException {
        for (Repair element : elements) {
            if (element.getID() == id) {
                return element.isArchive();
            }
        }
        throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
    }

    public void unarchivise(int id) throws RepositoryException {
        for (Repair element : elements) {
            if (element.getID() == id) {
                element.setArchive(false);
                return;
            }
        }
        throw new RepositoryException(RepositoryException.REPOSITORY_ARCHIVE_EXCEPTION);
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
