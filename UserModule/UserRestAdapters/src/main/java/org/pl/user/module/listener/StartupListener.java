package org.pl.user.module.listener;

import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.pl.user.module.converters.UserConverter;
import org.pl.user.module.model.AddressRest;
import org.pl.user.module.model.UserAccessTypeRest;
import org.pl.user.module.model.UserRest;
import org.pl.user.module.userinterface.user.WriteUserUseCases;

import java.util.logging.Logger;

@WebListener
public class StartupListener implements ServletContextListener {
    private static final Logger LOGGER = Logger.getLogger(StartupListener.class.getName());
    @Inject
    private WriteUserUseCases writeUserUseCases;
    @Inject
    private UserConverter userConverter;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        UserRest admin = UserRest.builder()
                .username("admin")
                .archive(false)
                .firstName("Admin")
                .lastName("Admin")
                .phoneNumber("123456789")
                .address(new AddressRest(
                        "Lodz",
                        "31",
                        "Narutowicza"
                ))
                .userAccessType(UserAccessTypeRest.ADMINISTRATORS)
                .build();
            UserRest employee = UserRest.builder()
                .username("employee")
                .archive(false)
                .firstName("James")
                .lastName("Jameson")
                .phoneNumber("123456789")
                .address(new AddressRest(
                        "Lodz",
                        "31",
                        "Narutowicza"
                ))
                .userAccessType(UserAccessTypeRest.EMPLOYEES)
                .build();
            UserRest user = UserRest.builder()
                .username("user")
                .archive(false)
                .firstName("Peter")
                .lastName("Jackson")
                .phoneNumber("123456789")
                .address(new AddressRest(
                        "Lodz",
                        "31",
                        "Narutowicza"
                ))
                .userAccessType(UserAccessTypeRest.USERS)
                .build();
        try {
            LOGGER.info("Creating admin client");
            writeUserUseCases.add(userConverter.convert(admin));
        } catch (Exception e) {
            LOGGER.warning("Could not create admin account: " + e.getMessage());
        }
        try {
            LOGGER.info("Creating employee account");
            writeUserUseCases.add(userConverter.convert(employee));
        } catch (Exception e) {
            LOGGER.warning("Could not create employee account: " + e.getMessage());
        }
        try {
            LOGGER.info("Creating user account");
            writeUserUseCases.add(userConverter.convert(user));
        } catch (Exception e) {
            LOGGER.warning("Could not create user account: " + e.getMessage());
        }
    }
}
