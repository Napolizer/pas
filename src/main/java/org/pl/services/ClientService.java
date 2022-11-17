package org.pl.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Client;
import org.pl.repositories.ClientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ClientService {
    @Inject
    private ClientRepository clientRepository;

    public Client add(Client client) {
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

    public List<Client> getAllCLients() {
        return clientRepository.getAllClients();
    }

    public Client getClientByUsername(String username) {
        return clientRepository.getCLientByUsername(username);
    }
}
