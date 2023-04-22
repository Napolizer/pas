package org.pl.user.module.controllers;

import org.microshed.testing.SharedContainerConfiguration;
import org.microshed.testing.testcontainers.ApplicationContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.time.Duration;

public class AppContainerConfig implements SharedContainerConfiguration {
    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15.2")
            .withNetworkAliases("testpostgres")
            .withDatabaseName("tksUserModule")
            .withUsername("docker")
            .withPassword("docker");

    @Container
    public static ApplicationContainer container = new ApplicationContainer()
            .withAppContextRoot("/UserRestAdapters-1.0-SNAPSHOT")
            .withEnv("DB_HOST", "testpostgres")
            .withReadinessPath("/UserRestAdapters-1.0-SNAPSHOT/api/health")
            .dependsOn(postgres)
            .withStartupTimeout(Duration.ofMinutes(5));
}
