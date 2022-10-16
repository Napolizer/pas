package org.pl.model;

import lombok.Builder;
import lombok.Data;

import static org.pl.model.Condition.FINE;

@Data
@Builder
public class Hardware implements Entity {
    private int id;
    private boolean archive;
    private int price;
    private HardwareType hardwareType;

    public void repair() {
        hardwareType.setCondition(FINE);
    }

    @Override
    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    @Override
    public int getID() {
        return id;
    }
}
