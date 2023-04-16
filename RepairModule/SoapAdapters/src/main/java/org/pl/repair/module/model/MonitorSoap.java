package org.pl.repair.module.model;

import jakarta.xml.bind.annotation.XmlTransient;
import org.pl.repair.module.model.exceptions.HardwareSoapException;

import java.io.Serializable;

@XmlTransient
public class MonitorSoap extends HardwareTypeSoap implements Serializable {
    public MonitorSoap(ConditionSoap condition) {
        super(condition);
        setType("MONITOR");
    }
    public double calculateRepairCost(double price) throws HardwareSoapException {
        if (price < 0) {
            throw new HardwareSoapException(HardwareSoapException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_BELOW_ZERO_EXCEPTION);
        }
        return switch (getCondition()) {
            case UNREPAIRABLE ->
                    throw new HardwareSoapException(HardwareSoapException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_UNREPAIRABLE_EXCEPTION);
            case VERY_BAD -> 0.95 * price;
            case BAD -> 0.9 * price;
            case AVERAGE -> 0.8 * price;
            case DUSTY -> 10;
            case FINE ->
                    throw new HardwareSoapException(HardwareSoapException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_BELOW_FINE_EXCEPTION);
        };
    }
}
