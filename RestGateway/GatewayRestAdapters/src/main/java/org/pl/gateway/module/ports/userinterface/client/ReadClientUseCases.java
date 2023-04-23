package org.pl.gateway.module.ports.userinterface.client;


import org.pl.gateway.module.model.ClientRest;
import org.pl.gateway.module.model.ClientTypeRest;
import org.pl.gateway.module.model.UserRestCredentials;
import org.pl.gateway.module.model.exceptions.RepositoryRestException;
import org.pl.gateway.module.model.exceptions.authentication.InvalidCredentialsException;

import java.util.List;
import java.util.UUID;

public interface ReadClientUseCases {
    ClientRest get(UUID id) throws RepositoryRestException;
    String getInfo(UUID id) throws RepositoryRestException;
    Double getClientBalance(UUID id) throws RepositoryRestException;
    Boolean isClientArchive(UUID id) throws RepositoryRestException;
    List<ClientRest> getAllClients();
    Integer getPresentSize();
    Integer getArchiveSize();
    List<ClientRest> getAllClientsFilter(String substr);
    ClientTypeRest getClientTypeById(UUID id) throws RepositoryRestException;
    ClientRest getClientByUsername(String username) throws RepositoryRestException;
    String login(UserRestCredentials userRestCredentials) throws InvalidCredentialsException;
}
