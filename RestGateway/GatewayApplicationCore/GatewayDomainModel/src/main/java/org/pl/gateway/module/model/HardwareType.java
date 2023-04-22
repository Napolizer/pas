package org.pl.gateway.module.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.pl.gateway.module.model.exceptions.HardwareException;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class HardwareType implements Serializable {
    protected UUID id = UUID.randomUUID();
    public Condition condition;
    @Setter(AccessLevel.PROTECTED)
    protected String type;

    public HardwareType(Condition condition) {
        setCondition(condition);
    }

    public abstract double calculateRepairCost(double price) throws HardwareException;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HardwareType that = (HardwareType) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
