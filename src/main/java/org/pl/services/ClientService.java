package org.pl.services;

import org.pl.exceptions.ClientException;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Client;
import org.pl.repositories.ClientRepository;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client add(Client client) throws RepositoryException, ClientException {
        if (Objects.equals(client.getFirstName(), ""))
            throw new ClientException(ClientException.CLIENT_FIRST_NAME_EXCEPTION);
        if (Objects.equals(client.getLastName(), ""))
            throw new ClientException(ClientException.CLIENT_LAST_NAME_EXCEPTION);
        if (Objects.equals(client.getPhoneNumber(), ""))
            throw new ClientException(ClientException.CLIENT_PHONE_NUMBER_EXCEPTION);
        if (Objects.equals(client.getAddress(), null))
            throw new ClientException(ClientException.CLIENT_ADDRESS_EXCEPTION);
        return clientRepository.saveClient(client);
    }

    public Client get(UUID id) throws RepositoryException {
        return clientRepository.getClientById(id);
    }

    public String getInfo(UUID id) throws RepositoryException {
        return clientRepository.getClientById(id).toString();
    }

    public double getClientBalance(UUID id) throws RepositoryException {
        return clientRepository.getClientById(id).getBalance();
    }

    public boolean isClientArchive(UUID id) throws RepositoryException {
        return clientRepository.getClientById(id).isArchive();
    }

    public void archive(UUID id) throws RepositoryException {
        clientRepository.deleteClient(id);
    }

    public int getPresentSize() {
        return clientRepository.getClients(false).size();
    }

    public int getArchiveSize() {
        return clientRepository.getClients(true).size();
    }
}
