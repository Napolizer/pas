package org.pl.services;

import org.pl.exceptions.RepositoryException;
import org.pl.model.Client;
import org.pl.repositories.ClientRepository;

import java.util.UUID;

public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

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
