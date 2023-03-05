package org.pl.model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.pl.exceptions.HardwareException;

import java.io.Serializable;

@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Phone extends HardwareType implements Serializable {
    public Phone(Condition condition) {
        super(condition);
        setType("PHONE");
    }
    public double calculateRepairCost(double price) throws HardwareException {
        if (price < 0) {
            throw new HardwareException(HardwareException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_BELOW_ZERO_EXCEPTION);
        }
        return switch (getCondition()) {
            case UNREPAIRABLE ->
                    throw new HardwareException(HardwareException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_UNREPAIRABLE_EXCEPTION);
            case VERY_BAD -> 0.8 * price;
            case BAD -> 0.5 * price;
            case AVERAGE -> 0.2 * price;
            case DUSTY -> 5;
            case FINE ->
                    throw new HardwareException(HardwareException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_BELOW_FINE_EXCEPTION);
        };
    }
}
