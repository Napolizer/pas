package org.pl.model;

import jakarta.xml.bind.annotation.XmlTransient;
import org.pl.model.exceptions.HardwareSoapException;

import java.io.Serializable;

//@XmlRootElement(name = "console")
@XmlTransient
public class ConsoleSoap extends HardwareTypeSoap implements Serializable {
    public ConsoleSoap(ConditionSoap condition) {
        super(condition);
        setType("CONSOLE");
    }
    public double calculateRepairCost(double price) throws HardwareSoapException {
        if (price < 0) {
            throw new HardwareSoapException(HardwareSoapException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_BELOW_ZERO_EXCEPTION);
        }
        return switch (getCondition()) {
            case UNREPAIRABLE ->
                    throw new HardwareSoapException(HardwareSoapException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_UNREPAIRABLE_EXCEPTION);
            case VERY_BAD -> 0.9 * price;
            case BAD -> 0.8 * price;
            case AVERAGE -> 0.6 * price;
            case DUSTY -> 100;
            case FINE ->
                    throw new HardwareSoapException(HardwareSoapException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_BELOW_FINE_EXCEPTION);
        };
    }
}
