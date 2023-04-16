package org.pl.repair.module.model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.pl.repair.module.model.exceptions.HardwareRestException;

import java.io.Serializable;

@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ConsoleRest extends HardwareTypeRest implements Serializable {
    public ConsoleRest(ConditionRest condition) {
        super(condition);
        setType("CONSOLE");
    }
    public double calculateRepairCost(double price) throws HardwareRestException {
        if (price < 0) {
            throw new HardwareRestException(HardwareRestException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_BELOW_ZERO_EXCEPTION);
        }
        return switch (getCondition()) {
            case UNREPAIRABLE ->
                    throw new HardwareRestException(HardwareRestException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_UNREPAIRABLE_EXCEPTION);
            case VERY_BAD -> 0.9 * price;
            case BAD -> 0.8 * price;
            case AVERAGE -> 0.6 * price;
            case DUSTY -> 100;
            case FINE ->
                    throw new HardwareRestException(HardwareRestException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_BELOW_FINE_EXCEPTION);
        };
    }
}
