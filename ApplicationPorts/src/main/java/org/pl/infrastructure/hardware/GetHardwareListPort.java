package org.pl.infrastructure.hardware;

import org.pl.model.Hardware;

import java.util.List;

public interface GetHardwareListPort {
    List<Hardware> getHardwareList(boolean condition);
}
