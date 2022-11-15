package org.pl.repositories;


import lombok.AllArgsConstructor;
import org.pl.exceptions.ClientException;
import org.pl.exceptions.HardwareException;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Client;
import org.pl.model.Repair;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class RepairRepository extends Repository<Repair>{

    public RepairRepository(ArrayList<Repair> elements) {
        super(elements);
    }

    public List<Repair> getClientRepairs(Client client) {
        List<Repair> repairs = new ArrayList<>();
        for (int i = 0; i < getElements().size(); i++) {
            if (elements.get(i).getClient().equals(client)) {
                repairs.add(elements.get(i));
            }
        }
        return repairs;
    }
}
