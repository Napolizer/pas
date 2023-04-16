package org.pl.repair.module.adapter.data.aggregates;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.pl.repair.module.adapter.data.converters.ClientConverter;
import org.pl.repair.module.adapter.data.model.exceptions.RepositoryEntException;
import org.pl.repair.module.adapter.data.model.ClientEnt;
import org.pl.repair.module.adapter.data.repositories.ClientEntRepository;
import org.pl.repair.module.model.exceptions.RepositoryException;
import org.pl.repair.module.model.Client;
import org.pl.repair.module.infrastructure.client.ReadClientPort;
import org.pl.repair.module.infrastructure.client.WriteClientPort;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ClientRepositoryAdapter implements ReadClientPort, WriteClientPort {
    @Inject
    private ClientEntRepository clientEntRepository;
    @Inject
    private ClientConverter clientConverter;

    private ClientEnt convert(Client client) {
        return clientConverter.convert(client);
    }

    private Client convert(ClientEnt clientEnt) {
        return clientConverter.convert(clientEnt);
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
