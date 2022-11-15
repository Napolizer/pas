package org.pl.model;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

import static org.pl.model.Condition.FINE;

@Data
@Builder
public class Hardware implements Entity {
    @Id
    private UUID id;
    @NotNull
    private Boolean archive;
    @NotNull
    private Integer price;
    @NotNull
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
}
