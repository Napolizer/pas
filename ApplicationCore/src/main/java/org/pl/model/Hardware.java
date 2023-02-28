package org.pl.model;

import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.pl.adapters.HardwareTypeAdapter;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import static org.pl.model.Condition.FINE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Hardware implements Entity, Serializable {
    private UUID id;
    @NotNull
    private Boolean archive;
    @NotNull
    private Integer price;
    @NotNull
    @JsonbTypeAdapter(HardwareTypeAdapter.class)
    private HardwareType hardwareType;

    public void repair() {
        hardwareType.setCondition(FINE);
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

        Hardware hardware = (Hardware) o;

        return Objects.equals(id, hardware.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
