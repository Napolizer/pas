package org.pl.adapter.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.pl.exceptions.HardwareException;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Data
@SuperBuilder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Access(AccessType.FIELD)
public abstract class HardwareTypeEnt implements Serializable {
    @Id
    protected UUID id = UUID.randomUUID();
    @NotNull
    public ConditionEnt condition;
    @NotNull
    @Setter(AccessLevel.PROTECTED)
    protected String type;

    public HardwareTypeEnt(ConditionEnt conditionEnt) {
        setCondition(conditionEnt);
    }

    public abstract double calculateRepairCost(double price) throws HardwareException;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HardwareTypeEnt that = (HardwareTypeEnt) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
