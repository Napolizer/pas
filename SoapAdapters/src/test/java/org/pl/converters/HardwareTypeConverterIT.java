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

import static org.junit.jupiter.api.Assertions.*;

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
    void convertHardwareTypeFromDomainToSoapModelTest() {
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

        HardwareTypeSoap computerSoap = hardwareTypeConverter.convert(computer);
        HardwareTypeSoap consoleSoap = hardwareTypeConverter.convert(console);
        HardwareTypeSoap phoneSoap = hardwareTypeConverter.convert(phone);
        HardwareTypeSoap monitorSoap = hardwareTypeConverter.convert(monitor);

        assertInstanceOf(ComputerSoap.class, computerSoap);
        assertEquals(computer.getId(), computerSoap.getId());
        assertEquals(computer.getType(), computerSoap.getType());

        assertInstanceOf(ConsoleSoap.class, consoleSoap);
        assertEquals(console.getId(), consoleSoap.getId());
        assertEquals(console.getType(), consoleSoap.getType());

        assertInstanceOf(PhoneSoap.class, phoneSoap);
        assertEquals(phone.getId(), phoneSoap.getId());
        assertEquals(phone.getType(), phoneSoap.getType());

        assertInstanceOf(MonitorSoap.class, monitorSoap);
        assertEquals(monitor.getId(), monitorSoap.getId());
        assertEquals(monitor.getType(), monitorSoap.getType());
    }

    @Test
    void convertHardwareTypeFromEntToDomainModelTest() {
        HardwareTypeSoap computerSoap = new ComputerSoap(ConditionSoap.AVERAGE);
        HardwareTypeSoap consoleSoap = new ConsoleSoap(ConditionSoap.AVERAGE);
        HardwareTypeSoap phoneSoap = new PhoneSoap(ConditionSoap.AVERAGE);
        HardwareTypeSoap monitorSoap = new MonitorSoap(ConditionSoap.AVERAGE);

        HardwareType computer = hardwareTypeConverter.convert(computerSoap);
        HardwareType console = hardwareTypeConverter.convert(consoleSoap);
        HardwareType phone = hardwareTypeConverter.convert(phoneSoap);
        HardwareType monitor = hardwareTypeConverter.convert(monitorSoap);

        assertInstanceOf(Computer.class, computer);
        assertEquals(computerSoap.getId(), computerSoap.getId());
        assertEquals(computerSoap.getType(), computerSoap.getType());

        assertInstanceOf(Console.class, console);
        assertEquals(consoleSoap.getId(), console.getId());
        assertEquals(consoleSoap.getType(), console.getType());

        assertInstanceOf(Phone.class, phone);
        assertEquals(phoneSoap.getId(), phone.getId());
        assertEquals(phoneSoap.getType(), phone.getType());

        assertInstanceOf(Monitor.class, monitor);
        assertEquals(monitorSoap.getId(), monitor.getId());
        assertEquals(monitorSoap.getType(), monitor.getType());
    }
}
