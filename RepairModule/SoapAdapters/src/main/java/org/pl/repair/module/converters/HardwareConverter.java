package org.pl.repair.module.converters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.pl.repair.module.model.Hardware;
import org.pl.repair.module.model.HardwareSoap;

@ApplicationScoped
public class HardwareConverter {
    @Inject
    HardwareTypeConverter hardwareTypeConverter;

    public HardwareSoap convert(Hardware hardware) {
        if (hardware == null) return null;
        HardwareSoap hardwareRest = new HardwareSoap();
        if (hardware.getId() != null) hardwareRest.setId(hardware.getId());
        if (hardware.getArchive() != null) hardwareRest.setArchive(hardware.getArchive());
        if (hardware.getPrice() != null) hardwareRest.setPrice(hardware.getPrice());
        if (hardware.getHardwareType() != null) hardwareRest.setHardwareType(hardwareTypeConverter.convert(hardware.getHardwareType()));
        return hardwareRest;
    }

    public Hardware convert(HardwareSoap hardwareSoap) {
        if (hardwareSoap == null) return null;
        Hardware hardware = new Hardware();
        hardware.setId(hardwareSoap.getId());
        hardware.setArchive(hardwareSoap.getArchive());
        hardware.setPrice(hardwareSoap.getPrice());
        hardware.setHardwareType(hardwareTypeConverter.convert(hardwareSoap.getHardwareType()));
        return hardware;
    }
}
