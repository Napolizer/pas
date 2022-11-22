package org.pl.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.pl.exceptions.HardwareException;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.pl.exceptions.HardwareException;

@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("Console")
public class Console extends HardwareType {
    public double calculateRepairCost(double price) throws HardwareException {
        if (price < 0) {
            throw new HardwareException(HardwareException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_BELOW_ZERO_EXCEPTION);
        }
        return switch (getCondition()) {
            case UNREPAIRABLE ->
                    throw new HardwareException(HardwareException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_UNREPAIRABLE_EXCEPTION);
            case VERY_BAD -> 0.9 * price;
            case BAD -> 0.8 * price;
            case AVERAGE -> 0.6 * price;
            case DUSTY -> 100;
            case FINE ->
                    throw new HardwareException(HardwareException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_BELOW_FINE_EXCEPTION);
        };
    }
}
