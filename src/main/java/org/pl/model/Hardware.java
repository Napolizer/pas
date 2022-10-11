package org.pl.model;

import lombok.Builder;
import lombok.Data;

import static org.pl.model.Condition.FINE;

@Data
@Builder
public class Hardware {
    private int id;
    private boolean archive;
    private int price;
    private HardwareType hardwareType;

    public void repair() {
        hardwareType.setCondition(FINE);
    }
}
