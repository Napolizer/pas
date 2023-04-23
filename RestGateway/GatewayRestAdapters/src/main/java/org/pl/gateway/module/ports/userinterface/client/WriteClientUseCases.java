package org.pl.gateway.module.ports.userinterface.client;



import org.pl.gateway.module.model.ClientRest;
import org.pl.gateway.module.model.exceptions.ClientRestException;
import org.pl.gateway.module.model.exceptions.RepositoryRestException;

import java.util.UUID;

public interface WriteClientUseCases {
    ClientRest add(ClientRest client) throws RepositoryRestException, ClientRestException;
    ClientRest archive(UUID id) throws RepositoryRestException;
    ClientRest updateClient(UUID id, ClientRest client) throws RepositoryRestException;
    ClientRest updatePassword(UUID id, String newPassword) throws RepositoryRestException;
    ClientRest dearchive(UUID id) throws RepositoryRestException;

}
