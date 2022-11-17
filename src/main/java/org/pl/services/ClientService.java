package org.pl.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.pl.exceptions.ClientException;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Client;
import org.pl.model.ClientType;
import org.pl.repositories.ClientRepository;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@ApplicationScoped
public class ClientService {
    private ClientRepository clientRepository;

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

    public Client archive(UUID id) throws RepositoryException {
        return clientRepository.deleteClient(id);
    }

    public List<Client> getAllCLients() {
        return clientRepository.getAllClients();
    }

    public Client getClientByUsername(String username) throws RepositoryException {
        return clientRepository.getClientByUsername(username);
    }

    public int getPresentSize() {
        return clientRepository.getClients(false).size();
    }

    public int getArchiveSize() {
        return clientRepository.getClients(true).size();
    }

    public List<Client> getClientsByUsername(String username) { return clientRepository.getClientsByUsername(username); }

    public Client updateClient(UUID uuid, Client client) throws RepositoryException { return clientRepository.updateClient(uuid, client);}

    public Client dearchivize(UUID uuid) throws RepositoryException {
        return clientRepository.restoreClient(uuid);
    }

    public ClientType getClientTypeById(UUID uuid) throws RepositoryException {
        List<Client> clients = clientRepository.getAllClients();
        for (Client client : clients) {
            if (client.getClientType().getId() == uuid) {
                return client.getClientType();
            }
        }
        throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
    }
}
