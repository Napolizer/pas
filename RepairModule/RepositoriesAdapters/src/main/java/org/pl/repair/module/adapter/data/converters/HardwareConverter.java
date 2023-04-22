package org.pl.repair.module.adapter.data.converters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.pl.repair.module.adapter.data.model.HardwareEnt;
import org.pl.repair.module.model.Hardware;

@ApplicationScoped
public class HardwareConverter {
    @Inject
    HardwareTypeConverter hardwareTypeConverter;

    public HardwareEnt convert(Hardware hardware) {
        if (hardware == null) return null;
        HardwareEnt hardwareEnt = new HardwareEnt();
        if (hardware.getId() != null) hardwareEnt.setId(hardware.getId());
        if (hardware.getArchive() != null) hardwareEnt.setArchive(hardware.getArchive());
        if (hardware.getPrice() != null) hardwareEnt.setPrice(hardware.getPrice());
        if (hardware.getHardwareType() != null) hardwareEnt.setHardwareTypeEnt(hardwareTypeConverter.convert(hardware.getHardwareType()));
        return hardwareEnt;
    }

    public Hardware convert(HardwareEnt hardwareEnt) {
        if (hardwareEnt == null) return null;
        Hardware hardware = new Hardware();
        hardware.setId(hardwareEnt.getId());
        hardware.setArchive(hardwareEnt.isArchive());
        hardware.setPrice(hardwareEnt.getPrice());
        hardware.setHardwareType(hardwareTypeConverter.convert(hardwareEnt.getHardwareTypeEnt()));
        return hardware;
    }
}