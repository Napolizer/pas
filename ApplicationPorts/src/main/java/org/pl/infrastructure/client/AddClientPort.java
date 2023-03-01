package org.pl.infrastructure.client;

import org.pl.adapter.data.model.ClientEnt;

public interface AddClientPort {
    ClientEnt createClient(ClientEnt client);
}
