package org.pl.repair.module.controllers;

import jakarta.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.pl.repair.module.converters.HardwareConverter;
import org.pl.repair.module.model.exceptions.HardwareException;
import org.pl.repair.module.model.exceptions.RepositoryException;
import org.pl.repair.module.model.ComputerSoap;
import org.pl.repair.module.model.ConditionSoap;
import org.pl.repair.module.model.HardwareSoap;
import org.pl.repair.module.model.MonitorSoap;
import org.pl.repair.module.userinterface.hardware.ReadHardwareUseCases;
import org.pl.repair.module.userinterface.hardware.WriteHardwareUseCases;

import java.io.File;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(ArquillianExtension.class)
public class HardwareSoapControllerIT {
    @Inject
    WriteHardwareUseCases writeHardwareUseCases;
    @Inject
    ReadHardwareUseCases readHardwareUseCases;
    @Inject
    HardwareConverter hardwareConverter;
    private HardwareSoapController hardwareSoapController;
    private HardwareSoap hardware;

    @BeforeEach
    public void setUp() {
        hardware = new HardwareSoap(
                UUID.randomUUID(),
                false,
                100,
                new ComputerSoap(ConditionSoap.FINE)
        );

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
    public void createHardwarePositiveTest() throws HardwareException, RepositoryException {
        HardwareSoap createdHardware = hardwareSoapController.createHardware(hardware);
        assertThat(hardwareSoapController.getHardwareById(createdHardware.getId()), is(notNullValue()));
    }

    @Test
    @Order(2)
    public void createHardwareNegativeTestPriceIsNegative() {
        hardware.setPrice(-100);
        assertThrows(HardwareException.class, () -> hardwareSoapController.createHardware(hardware));
    }

    @Test
    @Order(3)
    void getHardwareByIdPositiveTest() throws RepositoryException, HardwareException {
        HardwareSoap createdHardware = hardwareSoapController.createHardware(hardware);
        assertThat(hardwareSoapController.getHardwareById(createdHardware.getId()), is(equalTo(createdHardware)));
    }

    @Test
    @Order(4)
    void deleteHardwarePositiveTest() throws HardwareException, RepositoryException {
        HardwareSoap createdHardware = hardwareSoapController.createHardware(hardware);
        assertThat(hardwareSoapController.getHardwareById(createdHardware.getId()), is(notNullValue()));
        hardwareSoapController.deleteHardware(createdHardware.getId());
        assertThat(hardwareSoapController.getHardwareById(createdHardware.getId()), is(nullValue()));
    }

    @Test
    @Order(5)
    void deleteHardwareNegativeTest() {
        assertThrows(RepositoryException.class, () -> hardwareSoapController.deleteHardware(hardware.getId()));
    }

    @Test
    @Order(6)
    void updateHardwarePricePositiveTest() throws HardwareException, RepositoryException {
        HardwareSoap createdHardware = hardwareSoapController.createHardware(hardware);
        assertThat(hardwareSoapController.getHardwareById(createdHardware.getId()).getPrice(), is(equalTo(100)));
        createdHardware.setPrice(200);
        hardwareSoapController.updateHardware(createdHardware);
        assertThat(hardwareSoapController.getHardwareById(createdHardware.getId()).getPrice(), is(equalTo(200)));
    }

    @Test
    @Order(7)
    void updateHardwareTypePositiveTest() throws HardwareException, RepositoryException {
        ComputerSoap computerSoap = new ComputerSoap(ConditionSoap.FINE);
        HardwareSoap createdHardware = hardwareSoapController.createHardware(hardware);
        assertThat(hardwareSoapController.getHardwareById(createdHardware.getId()).getHardwareType().getType(), is(equalTo(computerSoap.getType())));
        MonitorSoap monitorSoap = new MonitorSoap(ConditionSoap.FINE);
        createdHardware.setHardwareType(monitorSoap);
        hardwareSoapController.updateHardware(createdHardware);
        assertThat(hardwareSoapController.getHardwareById(createdHardware.getId()).getHardwareType().getType(), is(equalTo(monitorSoap.getType())));
    }

    @Test
    @Order(8)
    void getAllHardwareTest() throws HardwareException, RepositoryException {
        HardwareSoap createdHardware1 = hardwareSoapController.createHardware(hardware);
        assertThat(hardwareSoapController.getAllHardware(), is(notNullValue()));
        assertThat(hardwareSoapController.getAllHardware().contains(createdHardware1), is(true));

        HardwareSoap newHardware = new HardwareSoap(
                UUID.randomUUID(),
                false,
                100,
                new ComputerSoap(ConditionSoap.FINE)
        );

        HardwareSoap createdHardware2 = hardwareSoapController.createHardware(newHardware);
        assertThat(hardwareSoapController.getAllHardware().contains(createdHardware1) &&
                hardwareSoapController.getAllHardware().contains(createdHardware2), is(true));
    }
}