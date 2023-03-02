package org.pl.infrastructure.client;

import java.util.UUID;

import org.pl.exceptions.RepositoryException;
import org.pl.model.Client;

public interface ChangePasswordPort {
    Client changePassword(UUID uuid, String newPassword) throws RepositoryException;
}
