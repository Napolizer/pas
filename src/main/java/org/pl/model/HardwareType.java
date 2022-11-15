package org.pl.model;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.pl.exceptions.HardwareException;

import java.util.UUID;

@Data
public abstract class HardwareType {
    @Id
    private UUID id;
    @NotNull
    private Condition condition;

    public abstract double calculateRepairCost(int price) throws HardwareException;
}
