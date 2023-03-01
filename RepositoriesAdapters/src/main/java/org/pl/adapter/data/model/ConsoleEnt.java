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
public class ConsoleEnt extends HardwareTypeEnt implements Serializable {
    public ConsoleEnt(ConditionEnt conditionEnt) {
        super(conditionEnt);
        setType("CONSOLE");
    }
    public double calculateRepairCost(double price) throws HardwareEntException {
        if (price < 0) {
            throw new HardwareEntException(HardwareEntException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_BELOW_ZERO_EXCEPTION);
        }
        return switch (getCondition()) {
            case UNREPAIRABLE ->
                    throw new HardwareEntException(HardwareEntException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_UNREPAIRABLE_EXCEPTION);
            case VERY_BAD -> 0.9 * price;
            case BAD -> 0.8 * price;
            case AVERAGE -> 0.6 * price;
            case DUSTY -> 100;
            case FINE ->
                    throw new HardwareEntException(HardwareEntException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_BELOW_FINE_EXCEPTION);
        };
    }
}
