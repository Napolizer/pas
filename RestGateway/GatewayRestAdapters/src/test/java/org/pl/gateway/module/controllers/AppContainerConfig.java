package org.pl.gateway.module.controllers;

import org.microshed.testing.SharedContainerConfiguration;
import org.microshed.testing.testcontainers.ApplicationContainer;
import org.testcontainers.junit.jupiter.Container;

public class AppContainerConfig implements SharedContainerConfiguration {
    @Container
    public static ApplicationContainer container = new ApplicationContainer()
            .withAppContextRoot("/RestAdapters-1.0-SNAPSHOT")
            .withReadinessPath("/RestAdapters-1.0-SNAPSHOT/api/health");
}
