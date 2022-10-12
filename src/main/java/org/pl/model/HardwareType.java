package org.pl.model;

import lombok.Data;
import org.pl.exceptions.HardwareException;

@Data
public abstract class HardwareType {
    private Condition condition;

    public abstract double calculateRepairCost(int price) throws HardwareException;
}
