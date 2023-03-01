package org.pl.infrastructure.hardware;

import org.pl.adapter.data.model.HardwareEnt;

import java.util.List;

public interface GetAllPresentHardwaresPort {
    List<HardwareEnt> getAllPresentHardwares();
}
