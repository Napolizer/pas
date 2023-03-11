package org.pl.adapter.data.converters;

import jakarta.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.pl.adapter.data.model.ComputerEnt;
import org.pl.adapter.data.model.ConditionEnt;
import org.pl.adapter.data.model.HardwareEnt;
import org.pl.adapter.data.model.HardwareTypeEnt;
import org.pl.model.Computer;
import org.pl.model.Condition;
import org.pl.model.Hardware;
import org.pl.model.HardwareType;

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

        HardwareEnt hardwareEnt = hardwareConverter.convert(hardware);

        assertInstanceOf(HardwareEnt.class, hardwareEnt);
        assertInstanceOf(ComputerEnt.class, hardwareEnt.getHardwareTypeEnt());
        assertEquals(hardware.getId(), hardwareEnt.getId());
        assertEquals(hardware.getPrice(), hardwareEnt.getPrice());
        assertEquals(hardware.isArchive(), hardwareEnt.isArchive());
        assertEquals(hardware.getHardwareType().getType(), hardwareEnt.getHardwareTypeEnt().getType());
        assertEquals(ConditionEnt.AVERAGE, hardwareEnt.getHardwareTypeEnt().getCondition());
    }

    @Test
    void convertHardwareFromEntToDomainModelTest() {
        HardwareTypeEnt computerEnt = ComputerEnt.builder()
                .condition(ConditionEnt.AVERAGE)
                .type("COMPUTER")
                .build();

        HardwareEnt hardwareEnt = HardwareEnt.builder()
                .id(UUID.randomUUID())
                .archive(false)
                .price(200)
                .hardwareTypeEnt(computerEnt)
                .build();

        Hardware hardware = hardwareConverter.convert(hardwareEnt);

        assertInstanceOf(Hardware.class, hardware);
        assertInstanceOf(Computer.class, hardware.getHardwareType());
        assertEquals(hardwareEnt.getId(), hardware.getId());
        assertEquals(hardwareEnt.getPrice(), hardware.getPrice());
        assertEquals(hardwareEnt.isArchive(), hardware.isArchive());
        assertEquals(hardwareEnt.getHardwareTypeEnt().getType(), hardware.getHardwareType().getType());
        assertEquals(Condition.AVERAGE, hardware.getHardwareType().getCondition());
    }
}
