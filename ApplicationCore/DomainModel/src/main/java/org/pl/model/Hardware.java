package org.pl.model;

import lombok.*;

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
    private Boolean archive;
    private Integer price;
    private HardwareType hardwareType;

    public void repair() {
        hardwareType.setCondition(FINE);
    }

    @Override
    public void setArchive(Boolean archive) {
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
