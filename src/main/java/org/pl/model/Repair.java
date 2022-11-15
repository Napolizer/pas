package org.pl.model;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.pl.exceptions.HardwareException;

import java.util.UUID;

@Data
@Builder
public class Repair implements Entity {
    @Id
    private UUID id;
    @NotNull
    private Boolean archive;
    @NotNull
    private Client client;
    @NotNull
    private Hardware hardware;

    public double calculateRepairCost() throws HardwareException {
        return getHardware().getHardwareType().calculateRepairCost(getHardware().getPrice());
    }

    @Override
    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    @Override
    public boolean isArchive() {
        return getArchive();
    }

    @Override
    public UUID getId() {
        return id;
    }
}
