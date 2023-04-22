package org.pl.model;

import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pl.adapters.RepairAdapter;
import org.pl.annotations.ValidDateRange;
import org.pl.model.exceptions.HardwareRestException;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonbTypeAdapter(RepairAdapter.class)
public class RepairRest implements EntityRest, Serializable {
    private UUID id;
    @NotNull
    private Boolean archive;
    @NotNull
    private ClientRest client;
    @NotNull
    private HardwareRest hardware;
    @NotNull
    @ValidDateRange
    private DateRangeRest dateRange;

    public double calculateRepairCost() throws HardwareRestException {
        return getHardware().getHardwareType().calculateRepairCost(getHardware().getPrice());
    }

    @Override
    public void setArchive(Boolean archive) {
        this.archive = archive;
    }


    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RepairRest repair = (RepairRest) o;

        return Objects.equals(id, repair.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
