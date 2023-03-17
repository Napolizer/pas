package org.pl.model;

import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pl.adapters.HardwareTypeRestAdapter;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import static org.pl.model.ConditionRest.FINE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HardwareRest implements EntityRest, Serializable {
    private UUID id;
    @NotNull
    private Boolean archive;
    @NotNull
    private Integer price;
    @NotNull
    @JsonbTypeAdapter(HardwareTypeRestAdapter.class)
    private HardwareTypeRest hardwareType;

    public void repair() {
        hardwareType.setCondition(FINE);
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

        HardwareRest hardware = (HardwareRest) o;

        return Objects.equals(id, hardware.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
