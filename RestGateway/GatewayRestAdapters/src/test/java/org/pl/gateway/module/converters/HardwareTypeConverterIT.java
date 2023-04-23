package org.pl.gateway.module.converters;

import jakarta.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.pl.gateway.module.model.*;

import java.io.File;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@ExtendWith(ArquillianExtension.class)
public class HardwareTypeConverterIT {
//    @Inject
//    private HardwareTypeConverter hardwareTypeConverter;
//
//    @Deployment
//    public static JavaArchive createDeployment() {
//        return ShrinkWrap.create(JavaArchive.class)
//                .addPackages(true, "org.pl")
//                .addPackages(true, "org.hamcrest")
//                .addAsResource(new File("src/main/resources/"),"")
//                .addAsResource(new File("target/classes/META-INF/"), "META-INF/")
//                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
//    }
//
//    @Test
//    void convertHardwareTypeFromDomainToEntModelTest() {
//        HardwareType computer = Computer.builder()
//                .id(UUID.randomUUID())
//                .condition(Condition.AVERAGE)
//                .type("COMPUTER")
//                .build();
//        HardwareType console = Console.builder()
//                .id(UUID.randomUUID())
//                .condition(Condition.AVERAGE)
//                .type("CONSOLE")
//                .build();
//        HardwareType phone = Phone.builder()
//                .id(UUID.randomUUID())
//                .condition(Condition.AVERAGE)
//                .type("PHONE")
//                .build();
//        HardwareType monitor = Monitor.builder()
//                .id(UUID.randomUUID())
//                .condition(Condition.AVERAGE)
//                .type("MONITOR")
//                .build();
//
//        HardwareTypeRest computerRest = hardwareTypeConverter.convert(computer);
//        HardwareTypeRest consoleRest = hardwareTypeConverter.convert(console);
//        HardwareTypeRest phoneRest = hardwareTypeConverter.convert(phone);
//        HardwareTypeRest monitorRest = hardwareTypeConverter.convert(monitor);
//
//        assertInstanceOf(ComputerRest.class, computerRest);
//        assertEquals(computer.getId(), computerRest.getId());
//        assertEquals(computer.getType(), computerRest.getType());
//
//        assertInstanceOf(ConsoleRest.class, consoleRest);
//        assertEquals(console.getId(), consoleRest.getId());
//        assertEquals(console.getType(), consoleRest.getType());
//
//        assertInstanceOf(PhoneRest.class, phoneRest);
//        assertEquals(phone.getId(), phoneRest.getId());
//        assertEquals(phone.getType(), phoneRest.getType());
//
//        assertInstanceOf(MonitorRest.class, monitorRest);
//        assertEquals(monitor.getId(), monitorRest.getId());
//        assertEquals(monitor.getType(), monitorRest.getType());
//    }
//
//    @Test
//    void convertHardwareTypeFromEntToDomainModelTest() {
//        HardwareTypeRest computerRest = ComputerRest.builder()
//                .id(UUID.randomUUID())
//                .condition(ConditionRest.AVERAGE)
//                .type("COMPUTER")
//                .build();
//        HardwareTypeRest consoleRest = ConsoleRest.builder()
//                .id(UUID.randomUUID())
//                .condition(ConditionRest.AVERAGE)
//                .type("CONSOLE")
//                .build();
//        HardwareTypeRest phoneRest = PhoneRest.builder()
//                .id(UUID.randomUUID())
//                .condition(ConditionRest.AVERAGE)
//                .type("PHONE")
//                .build();
//        HardwareTypeRest monitorRest = MonitorRest.builder()
//                .id(UUID.randomUUID())
//                .condition(ConditionRest.AVERAGE)
//                .type("MONITOR")
//                .build();
//
//        HardwareType computer = hardwareTypeConverter.convert(computerRest);
//        HardwareType console = hardwareTypeConverter.convert(consoleRest);
//        HardwareType phone = hardwareTypeConverter.convert(phoneRest);
//        HardwareType monitor = hardwareTypeConverter.convert(monitorRest);
//
//        assertInstanceOf(Computer.class, computer);
//        assertEquals(computerRest.getId(), computerRest.getId());
//        assertEquals(computerRest.getType(), computerRest.getType());
//
//        assertInstanceOf(Console.class, console);
//        assertEquals(consoleRest.getId(), console.getId());
//        assertEquals(consoleRest.getType(), console.getType());
//
//        assertInstanceOf(Phone.class, phone);
//        assertEquals(phoneRest.getId(), phone.getId());
//        assertEquals(phoneRest.getType(), phone.getType());
//
//        assertInstanceOf(Monitor.class, monitor);
//        assertEquals(monitorRest.getId(), monitor.getId());
//        assertEquals(monitorRest.getType(), monitor.getType());
//    }
}
