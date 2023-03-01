package org.pl.adapter.data.model;

import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.pl.adapter.data.exceptions.HardwareEntException;

import java.io.Serializable;

@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class ComputerEnt extends HardwareTypeEnt implements Serializable {
    public ComputerEnt(ConditionEnt conditionEnt) {
        super(conditionEnt);
        setType("COMPUTER");
    }
    public double calculateRepairCost(double price) throws HardwareEntException {
        if (price < 0) {
            throw new HardwareEntException(HardwareEntException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_BELOW_ZERO_EXCEPTION);
        }
        return switch (getCondition()) {
            case UNREPAIRABLE ->
                    throw new HardwareEntException(HardwareEntException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_UNREPAIRABLE_EXCEPTION);
            case VERY_BAD -> 0.7 * price;
            case BAD -> 0.5 * price;
            case AVERAGE -> 0.2 * price;
            case DUSTY -> 5;
            case FINE ->
                    throw new HardwareEntException(HardwareEntException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_BELOW_FINE_EXCEPTION);
        };
    }
}
