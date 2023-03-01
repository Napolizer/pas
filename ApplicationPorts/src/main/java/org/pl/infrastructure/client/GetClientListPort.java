package org.pl.infrastructure.client;

import org.pl.adapter.data.model.ClientEnt;

import java.util.List;

public interface GetClientListPort {
    List<ClientEnt> getClientList(boolean condition);
}
