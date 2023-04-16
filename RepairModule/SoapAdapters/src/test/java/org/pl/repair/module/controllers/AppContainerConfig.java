package org.pl.repair.module.controllers;

import org.microshed.testing.SharedContainerConfiguration;
import org.microshed.testing.testcontainers.ApplicationContainer;
import org.testcontainers.junit.jupiter.Container;

public class AppContainerConfig implements SharedContainerConfiguration {
    @Container
    public static ApplicationContainer container = new ApplicationContainer()
            .withAppContextRoot("/SoapAdapters-1.0-SNAPSHOT")
            .withReadinessPath("/SoapAdapters-1.0-SNAPSHOT/HardwareSoapController?wsdl");
}
