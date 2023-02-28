package org.pl.adapter.data.model;

import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pl.adapters.HardwareTypeAdapter;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import static org.pl.adapter.data.model.ConditionEnt.FINE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Access(AccessType.FIELD)
public class HardwareEnt implements EntityEnt, Serializable {
    @Id
    private UUID id;
    private Boolean archive;
    private Integer price;
    @ManyToOne(cascade = CascadeType.ALL)
    private HardwareTypeEnt hardwareTypeEnt;

    public void repair() {
        hardwareTypeEnt.setCondition(FINE);
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

        HardwareEnt hardwareEnt = (HardwareEnt) o;

        return Objects.equals(id, hardwareEnt.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
