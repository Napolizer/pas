package org.pl.infrastructure;

import org.pl.adapter.data.model.HardwareEnt;

import java.util.List;

public interface GetHardwareListPort {
    List<HardwareEnt> getHardwareList(boolean condition);
}
