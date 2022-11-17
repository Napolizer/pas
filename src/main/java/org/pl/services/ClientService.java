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
        return clientRepository.saveClient(client);
    }

    public Client get(UUID id) throws RepositoryException {
        return clientRepository.getClientById(id);
    }

    public double getClientBalance(UUID id) throws RepositoryException {
        return clientRepository.getClientById(id).getBalance();
    }

    public boolean isClientArchive(UUID id) throws RepositoryException {
        return clientRepository.getClientById(id).isArchive();
    }

    public void archivize(UUID id) throws RepositoryException {
        clientRepository.deleteClient(id);
    }
}
