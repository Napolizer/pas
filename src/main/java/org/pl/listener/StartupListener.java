package org.pl.listener;

import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.pl.model.*;
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
                .password("password")
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
            Client employee = Client.builder()
                .username("employee")
                .password("password")
                .archive(false)
                .balance(0.0)
                .firstName("James")
                .lastName("Jameson")
                .phoneNumber("123-456-789")
                .clientType(new Basic())
                .address(new Address(
                        "Lodz",
                        "31",
                        "Narutowicza"
                ))
                .clientAccessType(clientAccessType.EMPLOYEES)
                .build();
            Client user = Client.builder()
                .username("user")
                .password("password")
                .archive(false)
                .balance(0.0)
                .firstName("Peter")
                .lastName("Jackson")
                .phoneNumber("123-456-789")
                .clientType(new Basic())
                .address(new Address(
                        "Lodz",
                        "31",
                        "Narutowicza"
                ))
                .clientAccessType(clientAccessType.USERS)
                .build();
        try {
            LOGGER.info("Creating admin client");
            clientService.add(admin);
        } catch (Exception e) {
            LOGGER.warning("Could not create admin account: " + e.getMessage());
        }
        try {
            LOGGER.info("Creating employee account");
            clientService.add(employee);
        } catch (Exception e) {
            LOGGER.warning("Could not create employee account: " + e.getMessage());
        }
        try {
            LOGGER.info("Creating user account");
            clientService.add(user);
        } catch (Exception e) {
            LOGGER.warning("Could not create user account: " + e.getMessage());
        }
    }
}
