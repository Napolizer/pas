package org.pl.gateway.module.aggegates;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.NoArgsConstructor;
import org.pl.gateway.module.model.ClientRest;
import org.pl.gateway.module.model.ClientTypeRest;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@ApplicationScoped
public class ClientRestAdapter {

    public ClientRest add(ClientRest ClientRest) {
        return null;
    }

    public ClientRest get(UUID id) {
        return null;
    }

    public String getInfo(UUID id) {
        return null;
    }

    public double getClientRestBalance(UUID id) {
        return 0;
    }

    public boolean isClientRestArchive(UUID id) {
        return true;
    }

    public ClientRest archive(UUID id) {
        return null;
    }

    public List<ClientRest> getAllClientRests() {
        return null;
    }

    public ClientRest getClientRestByUsername(String username) {
        return null;
    }

    public int getPresentSize() {
        return 0;
    }

    public int getArchiveSize() {
        return 0;
    }

    public List<ClientRest> getAllClientRestsFilter(String substr) {
        return null;
    }

    public List<ClientRest> getClientRestsByUsername(String username) {
        return null;
    }

    public ClientRest updateClientRest(UUID uuid, ClientRest ClientRest) {
        return null;
    }

    public ClientRest updatePassword(UUID uuid, String newPassword) {
        return null;
    }

    public ClientRest dearchive(UUID uuid) {
        return null;
    }

    public ClientTypeRest getClientRestTypeById(UUID uuid) {
        return null;
    }
}
