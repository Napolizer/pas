package org.pl.infrastructure.hardware;

import org.pl.model.Hardware;

import java.util.List;

public interface GetAllPresentHardwareFilterPort {
    List<Hardware> getAllPresentHardwareFilter(String substr);
}
