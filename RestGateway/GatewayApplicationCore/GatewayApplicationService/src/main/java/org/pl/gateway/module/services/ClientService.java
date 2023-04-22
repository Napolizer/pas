package org.pl.gateway.module.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.pl.gateway.module.model.exceptions.ClientException;
import org.pl.gateway.module.model.exceptions.RepositoryException;
import org.pl.infrastructure.client.*;
import org.pl.model.Client;
import org.pl.model.ClientType;
import org.pl.userinterface.client.ReadClientUseCases;
import org.pl.userinterface.client.WriteHardwareUseCase;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@ApplicationScoped
public class ClientService implements WriteHardwareUseCase, ReadClientUseCases {
    @Inject
    private ReadClientPort readClientPort;

    @Inject
    private WriteClientPort writeClientPort;

    public Client add(Client client) throws RepositoryException, ClientException {
        if (Objects.equals(client.getFirstName(), ""))
            throw new ClientException(ClientException.CLIENT_FIRST_NAME_EXCEPTION);
        if (Objects.equals(client.getLastName(), ""))
            throw new ClientException(ClientException.CLIENT_LAST_NAME_EXCEPTION);
        if (Objects.equals(client.getPhoneNumber(), ""))
            throw new ClientException(ClientException.CLIENT_PHONE_NUMBER_EXCEPTION);
        if (Objects.equals(client.getAddress(), null))
            throw new ClientException(ClientException.CLIENT_ADDRESS_EXCEPTION);
        return writeClientPort.createClient(client);
    }

    public Client get(UUID id) throws RepositoryException {
        return readClientPort.getClient(id);
    }

    public String getInfo(UUID id) throws RepositoryException {
        return readClientPort.getClient(id).toString();
    }

    public double getClientBalance(UUID id) throws RepositoryException {
        return readClientPort.getClient(id).getBalance();
    }

    public boolean isClientArchive(UUID id) throws RepositoryException {
        return readClientPort.getClient(id).getArchive();
    }

    public Client archive(UUID id) throws RepositoryException {
        return writeClientPort.deleteClient(id);
    }

    public List<Client> getAllClients() {
        return readClientPort.getAllClients();
    }

    public Client getClientByUsername(String username) throws RepositoryException {
        return readClientPort.getClientByUsername(username);
    }

    public int getPresentSize() {
        return readClientPort.getClientList(false).size();
    }

    public int getArchiveSize() {
        return readClientPort.getClientList(true).size();
    }

    public List<Client> getAllClientsFilter(String substr) {
        return readClientPort.getAllClientsFilter(substr);
    }

    public List<Client> getClientsByUsername(String username) {
        return readClientPort.getClientsByUsername(username);
    }

    public Client updateClient(UUID uuid, Client client) throws RepositoryException {
        return writeClientPort.updateClient(uuid, client);
    }

    public Client updatePassword(UUID uuid, String newPassword) throws RepositoryException {
        return writeClientPort.changePassword(uuid, newPassword);
    }

    public Client dearchive(UUID uuid) throws RepositoryException {
        return writeClientPort.restoreClient(uuid);
    }

    public ClientType getClientTypeById(UUID uuid) throws RepositoryException {
        List<Client> clients = getAllClients();
        for (Client client : clients) {
            if (client.getClientType().getId().equals(uuid)) {
                return client.getClientType();
            }
        }
        throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
    }
}
