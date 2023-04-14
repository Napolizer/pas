package org.pl.model;

import jakarta.xml.bind.annotation.XmlTransient;
import org.pl.model.exceptions.HardwareSoapException;

import java.io.Serializable;

@XmlTransient
public class PhoneSoap extends HardwareTypeSoap implements Serializable {
    public PhoneSoap(ConditionSoap condition) {
        super(condition);
        setType("PHONE");
    }
    public double calculateRepairCost(double price) throws HardwareSoapException {
        if (price < 0) {
            throw new HardwareSoapException(HardwareSoapException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_BELOW_ZERO_EXCEPTION);
        }
        return switch (getCondition()) {
            case UNREPAIRABLE ->
                    throw new HardwareSoapException(HardwareSoapException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_UNREPAIRABLE_EXCEPTION);
            case VERY_BAD -> 0.8 * price;
            case BAD -> 0.5 * price;
            case AVERAGE -> 0.2 * price;
            case DUSTY -> 5;
            case FINE ->
                    throw new HardwareSoapException(HardwareSoapException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_BELOW_FINE_EXCEPTION);
        };
    }
}
