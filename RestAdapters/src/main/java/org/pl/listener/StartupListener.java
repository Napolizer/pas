package org.pl.listener;

import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import org.pl.converters.ClientConverter;
import org.pl.model.*;
import org.pl.userinterface.client.WriteClientQueries;

import java.util.logging.Logger;

@WebListener
public class StartupListener implements ServletContextListener {
    private static final Logger LOGGER = Logger.getLogger(StartupListener.class.getName());
    @Inject
    private WriteClientQueries writeClientQueries;
    @Inject
    private ClientConverter clientConverter;
    @Override
    public void contextInitialized(ServletContextEvent event) {
        ClientRest admin = ClientRest.builder()
                .username("admin")
                .password("password")
                .archive(false)
                .balance(0.0)
                .firstName("Admin")
                .lastName("Admin")
                .phoneNumber("123456789")
                .clientType(new PremiumRest())
                .address(new AddressRest(
                        "Lodz",
                        "31",
                        "Narutowicza"
                ))
                .clientAccessType(ClientAccessTypeRest.ADMINISTRATORS)
                .build();
            ClientRest employee = ClientRest.builder()
                .username("employee")
                .password("password")
                .archive(false)
                .balance(0.0)
                .firstName("James")
                .lastName("Jameson")
                .phoneNumber("123456789")
                .clientType(new BasicRest())
                .address(new AddressRest(
                        "Lodz",
                        "31",
                        "Narutowicza"
                ))
                .clientAccessType(ClientAccessTypeRest.EMPLOYEES)
                .build();
            ClientRest user = ClientRest.builder()
                .username("user")
                .password("password")
                .archive(false)
                .balance(0.0)
                .firstName("Peter")
                .lastName("Jackson")
                .phoneNumber("123456789")
                .clientType(new BasicRest())
                .address(new AddressRest(
                        "Lodz",
                        "31",
                        "Narutowicza"
                ))
                .clientAccessType(ClientAccessTypeRest.USERS)
                .build();
        try {
            LOGGER.info("Creating admin client");
            writeClientQueries.add(clientConverter.convert(admin));
        } catch (Exception e) {
            LOGGER.warning("Could not create admin account: " + e.getMessage());
        }
        try {
            LOGGER.info("Creating employee account");
            writeClientQueries.add(clientConverter.convert(employee));
        } catch (Exception e) {
            LOGGER.warning("Could not create employee account: " + e.getMessage());
        }
        try {
            LOGGER.info("Creating user account");
            writeClientQueries.add(clientConverter.convert(user));
        } catch (Exception e) {
            LOGGER.warning("Could not create user account: " + e.getMessage());
        }
    }
}