package org.pl.infrastructure.client;

import java.util.List;
import org.pl.model.Client;

public interface GetAllClientsFilterPort {
    List<Client> getAllClientsFilter(String substr);
}
