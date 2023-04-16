package org.pl.repair.module.adapter.data.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.pl.repair.module.adapter.data.model.exceptions.HardwareEntException;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Access(AccessType.FIELD)
public class RepairEnt implements EntityEnt, Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;
    @Column(nullable = false)
    private Boolean archive;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private ClientEnt clientEnt;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private HardwareEnt hardwareEnt;
    @Column(nullable = false)
    private DateRangeEnt dateRangeEnt;

    public double calculateRepairCost() throws HardwareEntException {
        return getHardwareEnt().getHardwareTypeEnt().calculateRepairCost(getHardwareEnt().getPrice());
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

        RepairEnt repairEnt = (RepairEnt) o;

        return Objects.equals(id, repairEnt.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
