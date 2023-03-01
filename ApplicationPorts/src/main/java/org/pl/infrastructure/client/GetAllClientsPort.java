package org.pl.infrastructure.client;

import org.pl.adapter.data.model.ClientEnt;

import java.util.List;

public interface GetAllClientsPort {
    List<ClientEnt> getAllClients();
}
