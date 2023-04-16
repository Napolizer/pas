package org.pl.repair.module.model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.pl.repair.module.model.exceptions.HardwareRestException;

import java.io.Serializable;

@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ComputerRest extends HardwareTypeRest implements Serializable {
    public ComputerRest(ConditionRest condition) {
        super(condition);
        setType("COMPUTER");
    }
    public double calculateRepairCost(double price) throws HardwareRestException {
        if (price < 0) {
            throw new HardwareRestException(HardwareRestException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_BELOW_ZERO_EXCEPTION);
        }
        return switch (getCondition()) {
            case UNREPAIRABLE ->
                    throw new HardwareRestException(HardwareRestException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_UNREPAIRABLE_EXCEPTION);
            case VERY_BAD -> 0.7 * price;
            case BAD -> 0.5 * price;
            case AVERAGE -> 0.2 * price;
            case DUSTY -> 5;
            case FINE ->
                    throw new HardwareRestException(HardwareRestException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_BELOW_FINE_EXCEPTION);
        };
    }
}
