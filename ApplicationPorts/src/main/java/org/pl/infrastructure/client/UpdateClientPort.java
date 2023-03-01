package org.pl.infrastructure.client;

import org.pl.adapter.data.model.ClientEnt;

import java.util.UUID;

public interface UpdateClientPort {
    ClientEnt updateClient(UUID uuid, ClientEnt client);
}
