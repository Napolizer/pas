package org.pl.model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.pl.model.exceptions.HardwareRestException;

import java.io.Serializable;

@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class MonitorRest extends HardwareTypeRest implements Serializable {
    public MonitorRest(ConditionRest condition) {
        super(condition);
        setType("MONITOR");
    }
    public double calculateRepairCost(double price) throws HardwareRestException {
        if (price < 0) {
            throw new HardwareRestException(HardwareRestException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_BELOW_ZERO_EXCEPTION);
        }
        return switch (getCondition()) {
            case UNREPAIRABLE ->
                    throw new HardwareRestException(HardwareRestException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_UNREPAIRABLE_EXCEPTION);
            case VERY_BAD -> 0.95 * price;
            case BAD -> 0.9 * price;
            case AVERAGE -> 0.8 * price;
            case DUSTY -> 10;
            case FINE ->
                    throw new HardwareRestException(HardwareRestException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_BELOW_FINE_EXCEPTION);
        };
    }
}
