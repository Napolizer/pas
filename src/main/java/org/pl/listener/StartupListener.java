package org.pl.listener;

import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.pl.model.Address;
import org.pl.model.Client;
import org.pl.model.Premium;
import org.pl.model.clientAccessType;
import org.pl.services.ClientService;

import java.util.logging.Logger;

@WebListener
public class StartupListener implements ServletContextListener {
    private static final Logger LOGGER = Logger.getLogger(StartupListener.class.getName());
    @Inject
    private ClientService clientService;
    @Override
    public void contextInitialized(ServletContextEvent event) {
        Client admin = Client.builder()
                .username("admin")
                .password("admin")
                .archive(false)
                .balance(0.0)
                .firstName("Admin")
                .lastName("Admin")
                .phoneNumber("123-456-789")
                .clientType(new Premium())
                .address(new Address(
                        "Lodz",
                        "31",
                        "Narutowicza"
                ))
                .clientAccessType(clientAccessType.ADMINISTRATORS)
                .build();
        try {
            LOGGER.info("Creating admin client");
            clientService.add(admin);
        } catch (Exception e) {
            LOGGER.warning("Could not create admin user: " + e.getMessage());
        }
    }
}
