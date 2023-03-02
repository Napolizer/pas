package org.pl.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.pl.model.exceptions.ClientException;
import org.pl.model.exceptions.RepositoryException;
import org.pl.infrastructure.client.*;
import org.pl.model.Client;
import org.pl.model.ClientType;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@ApplicationScoped
public class ClientService {
    @Inject
    private AddClientPort addClientPort;
    @Inject
    private ChangePasswordPort changePasswordPort;
    @Inject
    private DeleteClientPort deleteClientPort;
    @Inject
    private GetAllClientsFilterPort getAllClientsFilterPort;
    @Inject
    private GetAllClientsPort getAllClientsPort;
    @Inject
    private GetClientByUsernamePort getClientByUsernamePort;
    @Inject
    private GetClientListPort getClientListPort;
    @Inject
    private GetClientPort getClientPort;
    @Inject
    private GetClientsByUsernamePort getClientsByUsernamePort;
    @Inject
    private RestoreClientPort restoreClientPort;
    @Inject
    private UpdateClientPort updateClientPort;

    public Client add(Client client) throws RepositoryException, ClientException {
        if (Objects.equals(client.getFirstName(), ""))
            throw new ClientException(ClientException.CLIENT_FIRST_NAME_EXCEPTION);
        if (Objects.equals(client.getLastName(), ""))
            throw new ClientException(ClientException.CLIENT_LAST_NAME_EXCEPTION);
        if (Objects.equals(client.getPhoneNumber(), ""))
            throw new ClientException(ClientException.CLIENT_PHONE_NUMBER_EXCEPTION);
        if (Objects.equals(client.getAddress(), null))
            throw new ClientException(ClientException.CLIENT_ADDRESS_EXCEPTION);
        return addClientPort.createClient(client);
    }

    public Client get(UUID id) throws RepositoryException {
        return getClientPort.getClient(id);
    }

    public String getInfo(UUID id) throws RepositoryException {
        return getClientPort.getClient(id).toString();
    }

    public double getClientBalance(UUID id) throws RepositoryException {
        return getClientPort.getClient(id).getBalance();
    }

    public boolean isClientArchive(UUID id) throws RepositoryException {
        return getClientPort.getClient(id).isArchive();
    }

    public Client archive(UUID id) throws RepositoryException {
        return deleteClientPort.deleteClient(id);
    }

    public List<Client> getAllClients() {
        return getAllClientsPort.getAllClients();
    }

    public Client getClientByUsername(String username) throws RepositoryException {
        return getClientByUsernamePort.getClientByUsername(username);
    }

    public int getPresentSize() {
        return getClientListPort.getClientList(false).size();
    }

    public int getArchiveSize() {
        return getClientListPort.getClientList(true).size();
    }

    public List<Client> getAllClientsFilter(String substr) {
        return getAllClientsFilterPort.getAllClientsFilter(substr);
    }

    public List<Client> getClientsByUsername(String username) {
        return getClientsByUsernamePort.getClientsByUsername(username);
    }

    public Client updateClient(UUID uuid, Client client) throws RepositoryException {
        return updateClientPort.updateClient(uuid, client);
    }

    public Client updatePassword(UUID uuid, String newPassword) throws RepositoryException {
        return changePasswordPort.changePassword(uuid, newPassword);
    }

    public Client dearchive(UUID uuid) throws RepositoryException {
        return restoreClientPort.restoreClient(uuid);
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
