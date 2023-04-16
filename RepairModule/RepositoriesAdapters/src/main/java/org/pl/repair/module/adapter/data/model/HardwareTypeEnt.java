package org.pl.repair.module.adapter.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.pl.repair.module.adapter.data.model.exceptions.HardwareEntException;

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
    public ConditionEnt condition;
    @Setter(AccessLevel.PROTECTED)
    protected String type;

    public HardwareTypeEnt(ConditionEnt conditionEnt) {
        setCondition(conditionEnt);
    }

    public abstract double calculateRepairCost(double price) throws HardwareEntException;

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
