package org.pl.repair.module.converters;

import jakarta.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.pl.repair.module.model.*;

import java.io.File;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@ExtendWith(ArquillianExtension.class)
public class HardwareConverterIT {
    @Inject
    private HardwareConverter hardwareConverter;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "org.pl")
                .addPackages(true, "org.hamcrest")
                .addAsResource(new File("src/main/resources/"),"")
                .addAsResource(new File("target/classes/META-INF/"), "META-INF/")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    void convertHardwareFromDomainToSoapModelTest() {
        HardwareType computer = Computer.builder()
                .condition(Condition.AVERAGE)
                .type("COMPUTER")
                .build();

        Hardware hardware = Hardware.builder()
                .id(UUID.randomUUID())
                .archive(false)
                .price(200)
                .hardwareType(computer)
                .build();

        HardwareSoap hardwareSoap = hardwareConverter.convert(hardware);

        assertInstanceOf(HardwareSoap.class, hardwareSoap);
        assertInstanceOf(ComputerSoap.class, hardwareSoap.getHardwareType());
        assertEquals(hardware.getId(), hardwareSoap.getId());
        assertEquals(hardware.getPrice(), hardwareSoap.getPrice());
        assertEquals(hardware.getArchive(), hardwareSoap.getArchive());
        assertEquals(hardware.getHardwareType().getType(), hardwareSoap.getHardwareType().getType());
        assertEquals(ConditionSoap.AVERAGE, hardwareSoap.getHardwareType().getCondition());
    }

    @Test
    void convertHardwareFromSoapToDomainModelTest() {
        HardwareTypeSoap computerSoap = new ComputerSoap(ConditionSoap.AVERAGE);

        HardwareSoap hardwareSoap = new HardwareSoap(UUID.randomUUID(), false, 200, computerSoap);

        Hardware hardware = hardwareConverter.convert(hardwareSoap);

        assertInstanceOf(Hardware.class, hardware);
        assertInstanceOf(Computer.class, hardware.getHardwareType());
        assertEquals(hardwareSoap.getId(), hardware.getId());
        assertEquals(hardwareSoap.getPrice(), hardware.getPrice());
        assertEquals(hardwareSoap.getArchive(), hardware.getArchive());
        assertEquals(hardwareSoap.getHardwareType().getType(), hardware.getHardwareType().getType());
        assertEquals(Condition.AVERAGE, hardware.getHardwareType().getCondition());
    }
}
