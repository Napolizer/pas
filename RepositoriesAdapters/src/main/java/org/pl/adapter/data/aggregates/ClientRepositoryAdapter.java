package org.pl.adapter.data.aggregates;

import jakarta.inject.Inject;
import org.pl.adapter.data.model.exceptions.RepositoryEntException;
import org.pl.adapter.data.model.ClientEnt;
import org.pl.adapter.data.repositories.ClientEntRepository;
import org.pl.model.exceptions.RepositoryException;
import org.pl.infrastructure.client.*;
import org.pl.model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.apache.commons.beanutils.BeanUtils.copyProperties;

public class ClientRepositoryAdapter implements
        AddClientPort, ChangePasswordPort, DeleteClientPort,
        GetAllClientsFilterPort, GetAllClientsPort, GetClientByUsernamePort,
        GetClientListPort, GetClientPort, GetClientsByUsernamePort,
        RestoreClientPort, UpdateClientPort {
    @Inject
    private ClientEntRepository clientEntRepository;

    private ClientEnt convert(Client client) {
        try {
            ClientEnt clientEnt = new ClientEnt();
            copyProperties(clientEnt, client);
            return clientEnt;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Client convert(ClientEnt clientEnt) {
        try {
            Client client = new Client();
            copyProperties(client, clientEnt);
            return client;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Client createClient(Client client) throws RepositoryException {
        try {
            return convert(clientEntRepository.saveClient(convert(client)));
        } catch (RepositoryEntException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public Client changePassword(UUID uuid, String newPassword) throws RepositoryException {
        try {
            return convert(clientEntRepository.changePassword(uuid, newPassword));
        } catch (RepositoryEntException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public Client deleteClient(UUID uuid) throws RepositoryException {
        try {
            return convert(clientEntRepository.deleteClient(uuid));
        } catch (RepositoryEntException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public List<Client> getAllClientsFilter(String substr) {
        List<Client> clientList = new ArrayList<>();
        for (ClientEnt client : clientEntRepository.getAllClientsFilter(substr)) {
            clientList.add(convert(client));
        }
        return clientList;
    }

    @Override
    public List<Client> getAllClients() {
        List<Client> clientList = new ArrayList<>();
        for (ClientEnt client : clientEntRepository.getAllClients()) {
            clientList.add(convert(client));
        }
        return clientList;
    }

    @Override
    public Client getClientByUsername(String username) throws RepositoryException {
        try {
            return convert(clientEntRepository.getClientByUsername(username));
        } catch (RepositoryEntException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public List<Client> getClientList(boolean condition) {
        List<Client> clientList = new ArrayList<>();
        for (ClientEnt client : clientEntRepository.getClients(condition)) {
            clientList.add(convert(client));
        }
        return clientList;
    }

    @Override
    public Client getClient(UUID uuid) throws RepositoryException {
        try {
            return convert(clientEntRepository.getClientById(uuid));
        } catch (RepositoryEntException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public List<Client> getClientsByUsername(String username) {
        List<Client> clientList = new ArrayList<>();
        for (ClientEnt client : clientEntRepository.getClientsByUsername(username)) {
            clientList.add(convert(client));
        }
        return clientList;
    }

    @Override
    public Client restoreClient(UUID uuid) throws RepositoryException {
        try {
            return convert(clientEntRepository.restoreClient(uuid));
        } catch (RepositoryEntException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public Client updateClient(UUID uuid, Client client) throws RepositoryException {
        try {
            return convert(clientEntRepository.updateClient(uuid, convert(client)));
        } catch (RepositoryEntException e) {
            throw new RepositoryException(e.getMessage());
        }
    }
}
