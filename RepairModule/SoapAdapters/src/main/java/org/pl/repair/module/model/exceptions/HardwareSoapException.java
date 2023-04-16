package org.pl.repair.module.model.exceptions;

import jakarta.xml.bind.annotation.XmlTransient;
import org.apache.commons.lang3.builder.ToStringBuilder;

@XmlTransient
public class HardwareSoapException extends Exception {
    public static final String HARDWARE_PRICE_EXCEPTION = "Price cannot be less or equal to zero.";
    public static final String HARDWARE_TYPE_EXCEPTION = "Unknown HardwareType was given.";
    public static final String HARDWARE_TYPE_CONDITION_EXCEPTION = "Unknown Condition was given.";
    public static final String HARDWARE_TYPE_CALCULATE_REPAIR_COST_EXCEPTION = "Invalid price was given.";
    public static final String HARDWARE_TYPE_CALCULATE_REPAIR_COST_BELOW_ZERO_EXCEPTION = "Cannot calculate repair cost for negative number.";
    public static final String HARDWARE_TYPE_CALCULATE_REPAIR_COST_BELOW_FINE_EXCEPTION = "Cannot calculate repair cost for working hardware.";
    public static final String HARDWARE_TYPE_CALCULATE_REPAIR_COST_UNREPAIRABLE_EXCEPTION = "Cannot calculate repair cost for destroyed hardware.";

    public HardwareSoapException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .toString();
    }
}
