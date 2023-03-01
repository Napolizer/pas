package org.pl.infrastructure;

import org.pl.adapter.data.model.ClientEnt;

public interface AddClientPort {
    ClientEnt createClient(ClientEnt client);
}
