package org.pl.gateway.module.services;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import org.pl.gateway.module.model.Hardware;
import org.pl.gateway.module.model.HardwareType;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@ApplicationScoped
public class HardwareService {

    public Hardware add(Hardware hardware){
        return null;
    }

    public boolean isHardwareArchive(UUID id) {
        return true;
    }

    public Hardware get(UUID id)  {
        return null;
    }

    public String getInfo(UUID id) {
        return null;
    }

    public void archive(UUID id) {

    }

    public int getPresentSize() {
        return 0;
    }

    public int getArchiveSize() {
        return 0;
    }

    public List<Hardware> getAllHardwares() {
        return null;
    }

    public Hardware updateHardware(UUID uuid, Hardware hardware) {
        return null;
    }

    public HardwareType getHardwareTypeById(UUID uuid) {
        return null;
    }

    public List<Hardware> getAllPresentHardware() {
        return null;
    }

    public List<Hardware> getAllPresentHardwareFilter(String substr) {
        return null;
    }
}
