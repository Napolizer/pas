package org.pl.repair.module.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.metrics.annotation.Metric;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.pl.repair.module.model.exceptions.ClientException;
import org.pl.repair.module.model.exceptions.RepositoryException;
import org.pl.repair.module.model.Client;
import org.pl.repair.module.model.ClientType;
import org.pl.repair.module.infrastructure.client.ReadClientPort;
import org.pl.repair.module.infrastructure.client.WriteClientPort;
import org.pl.repair.module.userinterface.client.ReadClientUseCases;
import org.pl.repair.module.userinterface.client.WriteHardwareUseCase;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@ApplicationScoped
@Counted(name = "ClientService_Counter")
public class ClientService implements WriteHardwareUseCase, ReadClientUseCases {
    @Inject
    private ReadClientPort readClientPort;

    @Inject
    private WriteClientPort writeClientPort;

    @Inject
    @Metric
    private MetricRegistry metricRegistry;

    @Counted(name = "ClientService.add_Counter")
    @Timed(name = "ClientService.add_Timer")
    public Client add(Client client) throws RepositoryException, ClientException {
        if (Objects.equals(client.getFirstName(), "")) {
            metricRegistry.counter("ClientService.add.ClientFirstNameException_Counter").inc();
            throw new ClientException(ClientException.CLIENT_FIRST_NAME_EXCEPTION);
        }
        if (Objects.equals(client.getLastName(), "")) {
            metricRegistry.counter("ClientService.add.ClientLastNameException_Counter").inc();
            throw new ClientException(ClientException.CLIENT_LAST_NAME_EXCEPTION);
        }
        if (Objects.equals(client.getPhoneNumber(), "")) {
            metricRegistry.counter("ClientService.add.ClientPhoneNumberException_Counter").inc();
            throw new ClientException(ClientException.CLIENT_PHONE_NUMBER_EXCEPTION);
        }
        if (Objects.equals(client.getAddress(), null)) {
            metricRegistry.counter("ClientService.add.ClientAddressException_Counter").inc();
            throw new ClientException(ClientException.CLIENT_ADDRESS_EXCEPTION);
        }
        return writeClientPort.createClient(client);
    }

    @Counted(name = "Client.get_Counter")
    @Timed(name = "Client.get_Timer")
    public Client get(UUID id) throws RepositoryException {
        return readClientPort.getClient(id);
    }

    @Timed(name = "Client.getInfo_Timer")
    public String getInfo(UUID id) throws RepositoryException {
        return readClientPort.getClient(id).toString();
    }

    @Counted(name = "ClientService.getClientBalance_Counter")
    @Timed(name = "ClientService.getClientBalance_Timer")
    public double getClientBalance(UUID id) throws RepositoryException {
        return readClientPort.getClient(id).getBalance();
    }

    @Counted(name = "ClientService.isClientArchive_Counter")
    @Timed(name = "ClientService.isClientArchive_Timer")
    public boolean isClientArchive(UUID id) throws RepositoryException {
        return readClientPort.getClient(id).getArchive();
    }

    @Counted(name = "ClientService.archive_Counter")
    @Timed(name = "ClientService.archive_Timer")
    public Client archive(UUID id) throws RepositoryException {
        return writeClientPort.deleteClient(id);
    }

    @Gauge(unit = "number", name = "ClientService.getAllClients_Gauge")
    @Counted(name = "ClientService.getAllClients_Counter")
    @Timed(name = "ClientService.getAllClients_Timer")
    public List<Client> getAllClients() {
        return readClientPort.getAllClients();
    }

    @Counted(name = "ClientService.getClientByUsername_Counter")
    @Timed(name = "ClientService.getClientByUsername_Timer")
    public Client getClientByUsername(String username) throws RepositoryException {
        return readClientPort.getClientByUsername(username);
    }

    @Gauge(unit = "number", name = "ClientService.getPresentSize_Gauge")
    @Counted(name = "ClientService.getPresentSize_Counter")
    @Timed(name = "ClientService.getPresentSize_Timer")
    public int getPresentSize() {
        return readClientPort.getClientList(false).size();
    }

    @Gauge(unit = "number", name = "ClientService.getArchiveSize_Gauge")
    @Counted(name = "ClientService.getArchiveSize_Counter")
    @Timed(name = "ClientService.getArchiveSize_Timer")
    public int getArchiveSize() {
        return readClientPort.getClientList(true).size();
    }

    @Gauge(unit = "number", name = "ClientService.getAllClientsFilter_Gauge")
    @Counted(name = "ClientService.getAllClientsFilter_Counter")
    @Timed(name = "ClientService.getAllClientsFilter_Timer")
    public List<Client> getAllClientsFilter(String substr) {
        return readClientPort.getAllClientsFilter(substr);
    }

    @Gauge(unit = "number", name = "ClientService.getClientsByUsername_Gauge")
    @Counted(name = "ClientService.getClientsByUsername_Counter")
    @Timed(name = "ClientService.getClientsByUsername_Timer")
    public List<Client> getClientsByUsername(String username) {
        return readClientPort.getClientsByUsername(username);
    }

    @Counted(name = "ClientService.updateClient_Counter")
    @Timed(name = "ClientService.updateClient_Timer")
    public Client updateClient(UUID uuid, Client client) throws RepositoryException {
        return writeClientPort.updateClient(uuid, client);
    }

    @Counted(name = "ClientService.unarchive_Counter")
    @Timed(name = "ClientService.unarchive_Timer")
    public Client dearchive(UUID uuid) throws RepositoryException {
        return writeClientPort.restoreClient(uuid);
    }

    @Counted(name = "ClientService.getClientTypeById_Counter")
    @Timed(name = "ClientService.getClientTypeById_Timer")
    public ClientType getClientTypeById(UUID uuid) throws RepositoryException {
        List<Client> clients = getAllClients();
        for (Client client : clients) {
            if (client.getClientType().getId().equals(uuid)) {
                return client.getClientType();
            }
        }
        metricRegistry.counter("ClientService.getClientTypeById.RepositoryGetException_Counter").inc();
        throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
    }
}
