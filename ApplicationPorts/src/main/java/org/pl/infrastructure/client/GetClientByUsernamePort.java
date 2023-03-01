package org.pl.infrastructure.client;

import org.pl.adapter.data.model.ClientEnt;

public interface GetClientByUsernamePort {
    ClientEnt getClientByUsername(String username);
}
