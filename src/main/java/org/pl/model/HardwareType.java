package org.pl.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;
import org.pl.exceptions.HardwareException;

import java.util.Objects;
import java.util.UUID;

@Data
@SuperBuilder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Access(AccessType.FIELD)
@DiscriminatorColumn(name = "discriminator_type")
public abstract class HardwareType {
    @Id
    protected UUID id = UUID.randomUUID();
    @NotNull
    public Condition condition;
    @NotNull
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
