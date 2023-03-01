package org.pl.infrastructure.client;

import org.pl.model.Client;

import java.util.UUID;

public interface RestoreClientPort {
    Client restoreClient(UUID uuid);
}
