package org.pl.adapter.data.aggregates;

import jakarta.inject.Inject;
import org.pl.adapter.data.exceptions.RepositoryEntException;
import org.pl.adapter.data.model.ClientEnt;
import org.pl.adapter.data.repositories.ClientEntRepository;
import org.pl.exceptions.RepositoryException;
import org.pl.infrastructure.client.*;
import org.pl.model.Client;

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
    public Client changePassword(UUID uuid, String newPassword) {
        return null;
    }

    @Override
    public Client deleteClient(UUID uuid) {
        return null;
    }

    @Override
    public List<Client> getAllClientsFilter(String substr) {
        return null;
    }

    @Override
    public List<Client> getAllClients() {
        return null;
    }

    @Override
    public Client getClientByUsername(String username) {
        return null;
    }

    @Override
    public List<Client> getClientList(boolean condition) {
        return null;
    }

    @Override
    public Client getClient(UUID uuid) {
        return null;
    }

    @Override
    public List<Client> getClientsByUsername(String username) {
        return null;
    }

    @Override
    public Client restoreClient(UUID uuid) {
        return null;
    }

    @Override
    public Client updateClient(UUID uuid, Client client) {
        return null;
    }
}
