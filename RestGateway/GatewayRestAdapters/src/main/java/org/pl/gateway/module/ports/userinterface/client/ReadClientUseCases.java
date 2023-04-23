package org.pl.gateway.module.ports.userinterface.client;


import org.pl.gateway.module.model.ClientRest;
import org.pl.gateway.module.model.ClientTypeRest;
import org.pl.gateway.module.model.exceptions.RepositoryRestException;

import java.util.List;
import java.util.UUID;

public interface ReadClientUseCases {
    ClientRest get(UUID id) throws RepositoryRestException;
    String getInfo(UUID id) throws RepositoryRestException;
    double getClientBalance(UUID id) throws RepositoryRestException;
    boolean isClientArchive(UUID id) throws RepositoryRestException;
    List<ClientRest> getAllClients();
    int getPresentSize();
    int getArchiveSize();
    List<ClientRest> getAllClientsFilter(String substr);
    List<ClientRest> getClientsByUsername(String username);
    ClientTypeRest getClientTypeById(UUID id) throws RepositoryRestException;
    ClientRest getClientByUsername(String username) throws RepositoryRestException;
}
