package org.pl.gateway.module.aggegates;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.NoArgsConstructor;
import org.pl.gateway.module.model.ClientRest;
import org.pl.gateway.module.model.ClientTypeRest;
import org.pl.gateway.module.ports.userinterface.client.ReadClientUseCases;
import org.pl.gateway.module.ports.userinterface.client.WriteClientUseCases;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@ApplicationScoped
public class ClientRestAdapter implements ReadClientUseCases, WriteClientUseCases {

    public ClientRest add(ClientRest ClientRest) {
        return null;
    }

    public ClientRest get(UUID id) {
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

    public ClientRest archive(UUID id) {
        return null;
    }

    public List<ClientRest> getAllClients() {
        return null;
    }

    public ClientRest getClientByUsername(String username) {
        return null;
    }

    public int getPresentSize() {
        return 0;
    }

    public int getArchiveSize() {
        return 0;
    }

    public List<ClientRest> getAllClientsFilter(String substr) {
        return null;
    }

    public List<ClientRest> getClientsByUsername(String username) {
        return null;
    }

    public ClientRest updateClient(UUID uuid, ClientRest ClientRest) {
        return null;
    }

    public ClientRest updatePassword(UUID uuid, String newPassword) {
        return null;
    }

    public ClientRest dearchive(UUID uuid) {
        return null;
    }

    public ClientTypeRest getClientTypeById(UUID uuid) {
        return null;
    }
}
