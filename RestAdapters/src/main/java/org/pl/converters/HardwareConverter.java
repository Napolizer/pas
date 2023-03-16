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
        HardwareRest hardwareEnt = new HardwareRest();
        if (hardware.getId() != null) hardwareEnt.setId(hardware.getId());
        if (hardware.getArchive() != null) hardwareEnt.setArchive(hardware.isArchive());
        if (hardware.getPrice() != null) hardwareEnt.setPrice(hardware.getPrice());
        if (hardware.getHardwareType() != null) hardwareEnt.setHardwareType(hardwareTypeConverter.convert(hardware.getHardwareType()));
        return hardwareEnt;
    }

    public Hardware convert(HardwareRest hardwareEnt) {
        if (hardwareEnt == null) return null;
        Hardware hardware = new Hardware();
        hardware.setId(hardwareEnt.getId());
        hardware.setArchive(hardwareEnt.isArchive());
        hardware.setPrice(hardwareEnt.getPrice());
        hardware.setHardwareType(hardwareTypeConverter.convert(hardwareEnt.getHardwareType()));
        return hardware;
    }
}
