package org.pl.infrastructure.client;

import org.pl.adapter.data.model.ClientEnt;

import java.util.UUID;

public interface ChangePasswordPort {
    ClientEnt changePassword(UUID uuid, String newPassword);
}
