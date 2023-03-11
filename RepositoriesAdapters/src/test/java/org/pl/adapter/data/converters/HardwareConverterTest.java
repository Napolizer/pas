package org.pl.adapter.data.converters;

import jakarta.inject.Inject;
import org.jboss.arquillian.junit5.ArquillianExtension;
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

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@ExtendWith(ArquillianExtension.class)
public class HardwareConverterTest {
    @Inject
    private HardwareConverter hardwareConverter;

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
        assertEquals(hardware.getId(), hardwareEnt.getId());
        assertEquals(hardware.getPrice(), hardwareEnt.getPrice());
        assertEquals(hardware.isArchive(), hardwareEnt.isArchive());
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
        assertEquals(hardwareEnt.getId(), hardware.getId());
        assertEquals(hardwareEnt.getPrice(), hardware.getPrice());
        assertEquals(hardwareEnt.isArchive(), hardware.isArchive());
    }
}
