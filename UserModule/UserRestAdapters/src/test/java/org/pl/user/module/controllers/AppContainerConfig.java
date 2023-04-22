package org.pl.user.module.controllers;

import org.microshed.testing.SharedContainerConfiguration;
import org.microshed.testing.testcontainers.ApplicationContainer;
import org.testcontainers.junit.jupiter.Container;

import java.time.Duration;

public class AppContainerConfig implements SharedContainerConfiguration {
    @Container
    public static ApplicationContainer container = new ApplicationContainer()
            .withAppContextRoot("/UserRestAdapters-1.0-SNAPSHOT")
            .withReadinessPath("/UserRestAdapters-1.0-SNAPSHOT/api/health")
            .withStartupTimeout(Duration.ofMinutes(5));
}
