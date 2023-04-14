package org.pl.adapter.data.model;

import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.pl.adapter.data.model.exceptions.HardwareEntException;

import java.io.Serializable;

@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
public class MonitorEnt extends HardwareTypeEnt implements Serializable {
    public MonitorEnt(ConditionEnt conditionEnt) {
        super(conditionEnt);
        setType("MONITOR");
    }
    public double calculateRepairCost(double price) throws HardwareEntException {
        if (price < 0) {
            throw new HardwareEntException(HardwareEntException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_BELOW_ZERO_EXCEPTION);
        }
        return switch (getCondition()) {
            case UNREPAIRABLE ->
                    throw new HardwareEntException(HardwareEntException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_UNREPAIRABLE_EXCEPTION);
            case VERY_BAD -> 0.95 * price;
            case BAD -> 0.9 * price;
            case AVERAGE -> 0.8 * price;
            case DUSTY -> 10;
            case FINE ->
                    throw new HardwareEntException(HardwareEntException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_BELOW_FINE_EXCEPTION);
        };
    }
}
