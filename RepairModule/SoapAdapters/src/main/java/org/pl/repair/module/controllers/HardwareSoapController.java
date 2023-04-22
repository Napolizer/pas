package org.pl.repair.module.controllers;

import jakarta.inject.Inject;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import org.pl.repair.module.converters.HardwareConverter;
import org.pl.repair.module.model.HardwareSoap;
import org.pl.repair.module.model.exceptions.HardwareException;
import org.pl.repair.module.model.exceptions.RepositoryException;
import org.pl.repair.module.userinterface.hardware.ReadHardwareUseCases;
import org.pl.repair.module.userinterface.hardware.WriteHardwareUseCases;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@WebService(serviceName = "HardwareSoapController")
public class HardwareSoapController {
    @Inject
    WriteHardwareUseCases writeHardwareUseCases;
    @Inject
    ReadHardwareUseCases readHardwareUseCases;
    @Inject
    HardwareConverter hardwareConverter;

    @WebMethod
    public List<HardwareSoap> getAllHardware() {
        return readHardwareUseCases.getAllHardwares()
                .stream()
                .map(hardwareConverter::convert)
                .collect(Collectors.toList());
    }

    @WebMethod
    public HardwareSoap getHardwareById(UUID id) throws RepositoryException {
        return hardwareConverter.convert(readHardwareUseCases.get(id));
    }

    @WebMethod
    public HardwareSoap createHardware(HardwareSoap hardwareSoap) throws RepositoryException, HardwareException {
        return hardwareConverter.convert(writeHardwareUseCases.add(hardwareConverter.convert(hardwareSoap)));
    }

    @WebMethod
    public HardwareSoap updateHardware(HardwareSoap hardwareSoap) throws RepositoryException {
        return hardwareConverter.convert(writeHardwareUseCases.updateHardware(hardwareSoap.getId(), hardwareConverter.convert(hardwareSoap)));
    }

    @WebMethod
    public void deleteHardware(UUID id) throws RepositoryException {
        writeHardwareUseCases.archive(id);
    }
}
