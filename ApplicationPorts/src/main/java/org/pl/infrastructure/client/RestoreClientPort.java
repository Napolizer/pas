package org.pl.infrastructure.client;

import org.pl.adapter.data.model.ClientEnt;

import java.util.UUID;

public interface RestoreClientPort {
    ClientEnt restoreClient(UUID uuid);
}
