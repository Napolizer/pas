package org.pl.controllers;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import org.pl.converters.HardwareConverter;
import org.pl.model.HardwareSoap;
import org.pl.model.exceptions.HardwareException;
import org.pl.model.exceptions.RepositoryException;
import org.pl.userinterface.hardware.ReadHardwareUseCases;
import org.pl.userinterface.hardware.WriteHardwareUseCases;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@WebService(serviceName = "HardwareSoapController", targetNamespace = "hw")
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
