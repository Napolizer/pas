package org.pl.infrastructure.client;

import org.pl.adapter.data.model.ClientEnt;

import java.util.List;

public interface GetAllClientsFilterPort {
    List<ClientEnt> getAllClientsFilter(String substr);
}
