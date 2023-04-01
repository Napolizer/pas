package org.pl.controllers;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.*;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.microshed.testing.SharedContainerConfig;
import org.microshed.testing.jupiter.MicroShedTest;
import org.pl.converters.HardwareConverter;
import org.pl.model.*;
import org.pl.model.exceptions.HardwareException;
import org.pl.model.exceptions.RepositoryException;
import org.pl.userinterface.hardware.ReadHardwareUseCases;
import org.pl.userinterface.hardware.WriteHardwareUseCases;

import java.io.File;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@MicroShedTest
@SharedContainerConfig(AppContainerConfig.class)
@ExtendWith(ArquillianExtension.class)
public class HardwareSoapControllerIT {
    @Inject
    WriteHardwareUseCases writeHardwareUseCases;
    @Inject
    ReadHardwareUseCases readHardwareUseCases;
    @Inject
    HardwareConverter hardwareConverter;

    private HardwareSoapController hardwareSoapController;

    private Hardware existingHardware;

    @BeforeEach
    public void setUp() throws Exception {
        hardwareSoapController = new HardwareSoapController();
        hardwareSoapController.writeHardwareUseCases = writeHardwareUseCases;
        hardwareSoapController.readHardwareUseCases = readHardwareUseCases;
        hardwareSoapController.hardwareConverter = hardwareConverter;
    }

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "org.pl")
                .addPackages(true, "org.hamcrest")
                .addAsResource(new File("src/main/resources/"),"")
                .addAsResource(new File("src/test/resources/"),"")
                .addAsResource(new File("target/classes/META-INF/"), "META-INF/")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    @Order(1)
    public void properlyGetsAllHardwareTest() {
        // Call the method being tested
        List<HardwareSoap> result = hardwareSoapController.getAllHardware();

        // Assert that the correct list of hardware is returned
        assertThat(result.size(), is(equalTo(0)));
//        assertEquals(existingHardwareId, result.get(0).getId());
//        assertEquals("Existing hardware", result.get(0).getName());
//        assertEquals("A description", result.get(0).getDescription());
    }

    @Test
    @Order(2)
    public void properlyCreatesHardwareTest() throws HardwareException, RepositoryException {
        HardwareSoap hardware = new HardwareSoap(
                UUID.randomUUID(),
                false,
                100,
                new ComputerSoap(ConditionSoap.FINE)
        );

        List<HardwareSoap> result = hardwareSoapController.getAllHardware();
        assertThat(result.size(), is(equalTo(0)));

        hardwareSoapController.createHardware(hardware);
        result = hardwareSoapController.getAllHardware();
        assertThat(result.size(), is(equalTo(1)));
    }
}