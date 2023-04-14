package org.pl.converters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.pl.model.Hardware;
import org.pl.model.HardwareRest;

@ApplicationScoped
public class HardwareConverter {
    @Inject
    HardwareTypeConverter hardwareTypeConverter;

    public HardwareRest convert(Hardware hardware) {
        if (hardware == null) return null;
        HardwareRest hardwareRest = new HardwareRest();
        if (hardware.getId() != null) hardwareRest.setId(hardware.getId());
        if (hardware.getArchive() != null) hardwareRest.setArchive(hardware.getArchive());
        if (hardware.getPrice() != null) hardwareRest.setPrice(hardware.getPrice());
        if (hardware.getHardwareType() != null) hardwareRest.setHardwareType(hardwareTypeConverter.convert(hardware.getHardwareType()));
        return hardwareRest;
    }

    public Hardware convert(HardwareRest hardwareRest) {
        if (hardwareRest == null) return null;
        Hardware hardware = new Hardware();
        hardware.setId(hardwareRest.getId());
        hardware.setArchive(hardwareRest.getArchive());
        hardware.setPrice(hardwareRest.getPrice());
        hardware.setHardwareType(hardwareTypeConverter.convert(hardwareRest.getHardwareType()));
        return hardware;
    }
}
