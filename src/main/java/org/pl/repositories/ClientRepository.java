package org.pl.repositories;

import lombok.AllArgsConstructor;
import org.pl.model.Client;

import java.util.ArrayList;

@AllArgsConstructor
public class ClientRepository extends Repository<Client> {
    public ClientRepository(ArrayList<Client> elements) {
        super(elements);
    }
}