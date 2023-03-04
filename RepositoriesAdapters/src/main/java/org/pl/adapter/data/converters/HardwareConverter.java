package org.pl.adapter.data.converters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.pl.adapter.data.model.HardwareEnt;
import org.pl.model.Hardware;

@ApplicationScoped
public class HardwareConverter {

    @Inject
    HardwareTypeConverter hardwareTypeConverter;
    public HardwareEnt convert(Hardware hardware) {
        return HardwareEnt.builder()
                .id(hardware.getId())
                .archive(hardware.isArchive())
                .price(hardware.getPrice())
                .hardwareTypeEnt(hardwareTypeConverter.convert(hardware.getHardwareType()))
                .build();
    }

    public Hardware convert(HardwareEnt hardwareEnt) {
        return Hardware.builder()
                .id(hardwareEnt.getId())
                .archive(hardwareEnt.isArchive())
                .price(hardwareEnt.getPrice())
                .hardwareType(hardwareTypeConverter.convert(hardwareEnt.getHardwareTypeEnt()))
                .build();
    }
}
