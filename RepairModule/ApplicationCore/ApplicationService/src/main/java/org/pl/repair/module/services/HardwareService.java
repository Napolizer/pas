package org.pl.repair.module.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.metrics.annotation.Metric;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.pl.repair.module.model.Hardware;
import org.pl.repair.module.model.HardwareType;
import org.pl.repair.module.model.exceptions.HardwareException;
import org.pl.repair.module.model.exceptions.RepositoryException;
import org.pl.repair.module.infrastructure.hardware.ReadHardwarePort;
import org.pl.repair.module.infrastructure.hardware.WriteHardwarePort;
import org.pl.repair.module.userinterface.hardware.ReadHardwareUseCases;
import org.pl.repair.module.userinterface.hardware.WriteHardwareUseCases;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

//@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@ApplicationScoped
@Counted(name = "HardwareService_Counter")
public class HardwareService implements ReadHardwareUseCases, WriteHardwareUseCases {
    @Inject
    private ReadHardwarePort readHardwarePort;

    @Inject
    private WriteHardwarePort writeHardwarePort;

    @Inject
    @Metric
    private MetricRegistry metricRegistry;

    @Counted(name = "HardwareService.add_Counter")
    @Timed(name = "HardwareService.add_Timer")
    public Hardware add(Hardware hardware) throws RepositoryException, HardwareException {
        if (hardware.getPrice() < 0) {
            metricRegistry.counter("HardwareService.add.HardwarePriceException_Counter").inc();
            throw new HardwareException(HardwareException.HARDWARE_PRICE_EXCEPTION);
        }
        if (Objects.equals(hardware.getHardwareType(), null)) {
            metricRegistry.counter("HardwareService.add.HardwareTypeException_Counter").inc();
            throw new HardwareException(HardwareException.HARDWARE_TYPE_EXCEPTION);
        }
        return writeHardwarePort.createHardware(hardware);
    }

    @Counted(name = "HardwareService.isHardwareArchive_Counter")
    @Timed(name = "HardwareService.isHardwareArchive_Timer")
    public boolean isHardwareArchive(UUID id) throws RepositoryException {
        Hardware hardware = readHardwarePort.getHardware(id);
        if (hardware == null) {
            throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
        }
        return hardware.getArchive();
    }

    @Counted(name = "HardwareService.get_Counter")
    @Timed(name = "HardwareService.get_Timer")
    public Hardware get(UUID id) throws RepositoryException {
        return readHardwarePort.getHardware(id);
    }

    @Counted(name = "HardwareService.getInfo_Counter")
    @Timed(name = "HardwareService.getInfo_Timer")
    public String getInfo(UUID id) throws RepositoryException {
        return readHardwarePort.getHardware(id).toString();
    }

    @Counted(name = "HardwareService.archive_Counter")
    @Timed(name = "HardwareService.archive_Timer")
    public void archive(UUID id) throws RepositoryException {
        writeHardwarePort.deleteHardware(id);
    }

    @Gauge(unit = "number", name = "HardwareService.getPresentSize_Gauge")
    @Counted(name = "HardwareService.getPresentSize_Counter")
    @Timed(name = "HardwareService.getPresentSize_Timer")
    public int getPresentSize() {
        return readHardwarePort.getHardwareList(false).size();
    }

    @Gauge(unit = "number", name = "HardwareService.getArchiveSize_Gauge")
    @Counted(name = "HardwareService.getArchiveSize_Counter")
    @Timed(name = "HardwareService.getArchiveSize_Timer")
    public int getArchiveSize() {
        return readHardwarePort.getHardwareList(true).size();
    }

    @Gauge(unit = "number", name = "HardwareService.getAllHardware_Gauge")
    @Counted(name = "HardwareService.getAllHardware_Counter")
    @Timed(name = "HardwareService.getAllHardware_Timer")
    public List<Hardware> getAllHardwares() {
        return readHardwarePort.getAllHardwares();
    }

    @Counted(name = "HardwareService.updateHardware_Counter")
    @Timed(name = "HardwareService.updateHardware_Timer")
    public Hardware updateHardware(UUID uuid, Hardware hardware) throws RepositoryException {
        return writeHardwarePort.updateHardware(uuid, hardware);
    }

    @Counted(name = "HardwareService.getHardwareTypeById_Counter")
    @Timed(name = "HardwareService.getHardwareTypeById_Timer")
    public HardwareType getHardwareTypeById(UUID uuid) throws RepositoryException {
        List<Hardware> hardwares = readHardwarePort.getAllHardwares();
        for (Hardware hardware : hardwares) {
            if (hardware.getHardwareType().getId().equals(uuid)) {
                return hardware.getHardwareType();
            }
        }
        throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
    }

    @Gauge(unit = "number", name = "HardwareService.getAllPresentHardware_Gauge")
    @Counted(name = "HardwareService.getAllPresentHardware_Counter")
    @Timed(name = "HardwareService.getAllPresentHardware_Timer")
    public List<Hardware> getAllPresentHardware() {
        return readHardwarePort.getAllPresentHardwares();
    }

    @Gauge(unit = "number", name = "HardwareService.getAllPresentHardwareFilter_Gauge")
    @Counted(name = "HardwareService.getAllPresentHardwareFilter_Counter")
    @Timed(name = "HardwareService.getAllPresentHardwareFilter_Timer")
    public List<Hardware> getAllPresentHardwareFilter(String substr) {
        return readHardwarePort.getAllPresentHardwareFilter(substr);
    }
}
