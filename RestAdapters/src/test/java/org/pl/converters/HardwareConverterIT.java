package org.pl.converters;

import jakarta.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.pl.model.*;

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
    void convertHardwareFromDomainToEntModelTest() {
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

        HardwareRest hardwareRest = hardwareConverter.convert(hardware);

        assertInstanceOf(HardwareRest.class, hardwareRest);
        assertInstanceOf(ComputerRest.class, hardwareRest.getHardwareType());
        assertEquals(hardware.getId(), hardwareRest.getId());
        assertEquals(hardware.getPrice(), hardwareRest.getPrice());
        assertEquals(hardware.isArchive(), hardwareRest.isArchive());
        assertEquals(hardware.getHardwareType().getType(), hardwareRest.getHardwareType().getType());
        assertEquals(ConditionRest.AVERAGE, hardwareRest.getHardwareType().getCondition());
    }

    @Test
    void convertHardwareFromEntToDomainModelTest() {
        HardwareTypeRest computerEnt = ComputerRest.builder()
                .condition(ConditionRest.AVERAGE)
                .type("COMPUTER")
                .build();

        HardwareRest hardwareEnt = HardwareRest.builder()
                .id(UUID.randomUUID())
                .archive(false)
                .price(200)
                .hardwareType(computerEnt)
                .build();

        Hardware hardware = hardwareConverter.convert(hardwareEnt);

        assertInstanceOf(Hardware.class, hardware);
        assertInstanceOf(Computer.class, hardware.getHardwareType());
        assertEquals(hardwareEnt.getId(), hardware.getId());
        assertEquals(hardwareEnt.getPrice(), hardware.getPrice());
        assertEquals(hardwareEnt.isArchive(), hardware.isArchive());
        assertEquals(hardwareEnt.getHardwareType().getType(), hardware.getHardwareType().getType());
        assertEquals(Condition.AVERAGE, hardware.getHardwareType().getCondition());
    }
}
