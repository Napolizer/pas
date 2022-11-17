package org.pl.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Client;
import org.pl.repositories.ClientRepository;

import java.util.UUID;

@ApplicationScoped
public class ClientService {
    @Inject
    private ClientRepository clientRepository;

    public Client add(Client client) throws RepositoryException {
        return clientRepository.create(client);
    }

    public Client get(UUID id) throws RepositoryException {
        return clientRepository.read(id);
    }

    public double getClientBalance(UUID id) throws RepositoryException {
        return clientRepository.read(id).getBalance();
    }

    public boolean isClientArchive(UUID id) throws RepositoryException {
        return clientRepository.read(id).isArchive();
    }

    public void archivize(UUID id) throws RepositoryException {
        clientRepository.delete(id);
    }
}
