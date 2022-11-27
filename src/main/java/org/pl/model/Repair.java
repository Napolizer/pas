package org.pl.model;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.pl.adapters.RepairAdapter;
import org.pl.exceptions.HardwareException;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@jakarta.persistence.Entity
@Access(AccessType.FIELD)
@JsonbTypeAdapter(RepairAdapter.class)
public class Repair implements Entity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;
    @NotNull
    private Boolean archive;
    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "client_id")
    @NotNull
    private Client client;
    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "hardware_id")
    @NotNull
    private Hardware hardware;
    @NotNull
    @JsonbDateFormat(value = "dd-MM-yyyy HH:mm:ss")
    private Date startDate;
    @NotNull
    @JsonbDateFormat(value = "dd-MM-yyyy HH:mm:ss")
    private Date endDate;

    public double calculateRepairCost() throws HardwareException {
        return getHardware().getHardwareType().calculateRepairCost(getHardware().getPrice());
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

        Repair repair = (Repair) o;

        return Objects.equals(id, repair.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
