package org.pl.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import org.pl.model.Client;

import java.util.ArrayList;

@ApplicationScoped
@AllArgsConstructor
public class ClientRepository extends Repository<Client> {
    public ClientRepository(ArrayList<Client> elements) {
        super(elements);
    }
}