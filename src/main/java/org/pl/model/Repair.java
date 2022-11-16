package org.pl.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.pl.exceptions.HardwareException;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Data
@Builder
public class Repair implements Entity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private UUID id;
    @NotNull
    private Boolean archive;
    @NotNull
    private Client client;
    @NotNull
    private Hardware hardware;
    @NotNull
    private Date startDate;
    @NotNull
    private Date endDate;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Repair repair = (Repair) o;

        return Objects.equals(id, repair.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
