package org.pl.infrastructure.client;

import org.pl.model.Client;

import java.util.List;

public interface GetClientsByUsernamePort {
    List<Client> getClientsByUsername(String username);
}
