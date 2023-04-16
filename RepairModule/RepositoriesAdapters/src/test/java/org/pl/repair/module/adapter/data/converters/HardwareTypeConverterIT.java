package org.pl.repair.module.adapter.data.converters;

import jakarta.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.pl.model.*;
import org.pl.repair.module.adapter.data.model.*;
import org.pl.repair.module.model.*;

import java.io.File;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@ExtendWith(ArquillianExtension.class)
public class HardwareTypeConverterIT {
    @Inject
    private HardwareTypeConverter hardwareTypeConverter;

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
    void convertHardwareTypeFromDomainToEntModelTest() {
        HardwareType computer = Computer.builder()
                .id(UUID.randomUUID())
                .condition(Condition.AVERAGE)
                .type("COMPUTER")
                .build();
        HardwareType console = Console.builder()
                .id(UUID.randomUUID())
                .condition(Condition.AVERAGE)
                .type("CONSOLE")
                .build();
        HardwareType phone = Phone.builder()
                .id(UUID.randomUUID())
                .condition(Condition.AVERAGE)
                .type("PHONE")
                .build();
        HardwareType monitor = Monitor.builder()
                .id(UUID.randomUUID())
                .condition(Condition.AVERAGE)
                .type("MONITOR")
                .build();

        HardwareTypeEnt computerEnt = hardwareTypeConverter.convert(computer);
        HardwareTypeEnt consoleEnt = hardwareTypeConverter.convert(console);
        HardwareTypeEnt phoneEnt = hardwareTypeConverter.convert(phone);
        HardwareTypeEnt monitorEnt = hardwareTypeConverter.convert(monitor);

        assertInstanceOf(ComputerEnt.class, computerEnt);
        assertEquals(computer.getId(), computerEnt.getId());
        assertEquals(computer.getType(), computerEnt.getType());

        assertInstanceOf(ConsoleEnt.class, consoleEnt);
        assertEquals(console.getId(), consoleEnt.getId());
        assertEquals(console.getType(), consoleEnt.getType());

        assertInstanceOf(PhoneEnt.class, phoneEnt);
        assertEquals(phone.getId(), phoneEnt.getId());
        assertEquals(phone.getType(), phoneEnt.getType());

        assertInstanceOf(MonitorEnt.class, monitorEnt);
        assertEquals(monitor.getId(), monitorEnt.getId());
        assertEquals(monitor.getType(), monitorEnt.getType());
    }

    @Test
    void convertHardwareTypeFromEntToDomainModelTest() {
        HardwareTypeEnt computerEnt = ComputerEnt.builder()
                .id(UUID.randomUUID())
                .condition(ConditionEnt.AVERAGE)
                .type("COMPUTER")
                .build();
        HardwareTypeEnt consoleEnt = ConsoleEnt.builder()
                .id(UUID.randomUUID())
                .condition(ConditionEnt.AVERAGE)
                .type("CONSOLE")
                .build();
        HardwareTypeEnt phoneEnt = PhoneEnt.builder()
                .id(UUID.randomUUID())
                .condition(ConditionEnt.AVERAGE)
                .type("PHONE")
                .build();
        HardwareTypeEnt monitorEnt = MonitorEnt.builder()
                .id(UUID.randomUUID())
                .condition(ConditionEnt.AVERAGE)
                .type("MONITOR")
                .build();

        HardwareType computer = hardwareTypeConverter.convert(computerEnt);
        HardwareType console = hardwareTypeConverter.convert(consoleEnt);
        HardwareType phone = hardwareTypeConverter.convert(phoneEnt);
        HardwareType monitor = hardwareTypeConverter.convert(monitorEnt);

        assertInstanceOf(Computer.class, computer);
        assertEquals(computerEnt.getId(), computerEnt.getId());
        assertEquals(computerEnt.getType(), computerEnt.getType());

        assertInstanceOf(Console.class, console);
        assertEquals(consoleEnt.getId(), console.getId());
        assertEquals(consoleEnt.getType(), console.getType());

        assertInstanceOf(Phone.class, phone);
        assertEquals(phoneEnt.getId(), phone.getId());
        assertEquals(phoneEnt.getType(), phone.getType());

        assertInstanceOf(Monitor.class, monitor);
        assertEquals(monitorEnt.getId(), monitor.getId());
        assertEquals(monitorEnt.getType(), monitor.getType());
    }
}
