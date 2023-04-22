package org.pl.gateway.module.services;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.pl.gateway.module.model.Client;
import org.pl.gateway.module.model.ClientType;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@ApplicationScoped
public class ClientService {

    public Client add(Client client) {
        return null;
    }

    public Client get(UUID id) {
        return null;
    }

    public String getInfo(UUID id) {
        return null;
    }

    public double getClientBalance(UUID id) {
        return 0;
    }

    public boolean isClientArchive(UUID id) {
        return true;
    }

    public Client archive(UUID id) {
        return null;
    }

    public List<Client> getAllClients() {
        return null;
    }

    public Client getClientByUsername(String username) {
        return null;
    }

    public int getPresentSize() {
        return 0;
    }

    public int getArchiveSize() {
        return 0;
    }

    public List<Client> getAllClientsFilter(String substr) {
        return null;
    }

    public List<Client> getClientsByUsername(String username) {
        return null;
    }

    public Client updateClient(UUID uuid, Client client) {
        return null;
    }

    public Client updatePassword(UUID uuid, String newPassword) {
        return null;
    }

    public Client dearchive(UUID uuid) {
        return null;
    }

    public ClientType getClientTypeById(UUID uuid) {
        return null;
    }
}
