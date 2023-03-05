package org.pl.model;

import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pl.annotations.ValidDateRange;
import org.pl.exceptions.HardwareException;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@JsonbTypeAdapter(RepairAdapter.class)
@jakarta.persistence.Entity
public class Repair implements Entity, Serializable {
    @Id
    private UUID id;
    @NotNull
    private Boolean archive;
    @NotNull
    private Client client;
    @NotNull
    private Hardware hardware;
    @NotNull
    @ValidDateRange
    private DateRange dateRange;

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
