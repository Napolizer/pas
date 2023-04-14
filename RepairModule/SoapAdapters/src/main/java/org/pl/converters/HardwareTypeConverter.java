package org.pl.converters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.pl.model.*;

@ApplicationScoped
public class HardwareTypeConverter {

    @Inject
    private ConditionConverter conditionConverter;
    public HardwareTypeSoap convert(HardwareType hardwareType) {
        if (hardwareType == null) return null;
        HardwareTypeSoap hardwareTypeSoap = switch (hardwareType.getType()) {
            case "COMPUTER" -> new ComputerSoap(conditionConverter.convert(hardwareType.getCondition()));
            case "CONSOLE" -> new ConsoleSoap(conditionConverter.convert(hardwareType.getCondition()));
            case "PHONE" -> new PhoneSoap(conditionConverter.convert(hardwareType.getCondition()));
            case "MONITOR" -> new MonitorSoap(conditionConverter.convert(hardwareType.getCondition()));
            default -> throw new RuntimeException("Invalid Hardware Type");
        };
        hardwareTypeSoap.setId(hardwareType.getId());
        return hardwareTypeSoap;
    }

    public HardwareType convert(HardwareTypeSoap hardwareTypeSoap) {
        if (hardwareTypeSoap == null) return null;
        HardwareType hardwareType = switch (hardwareTypeSoap.getType()) {
            case "COMPUTER" -> new Computer(conditionConverter.convert(hardwareTypeSoap.getCondition()));
            case "CONSOLE" -> new Console(conditionConverter.convert(hardwareTypeSoap.getCondition()));
            case "PHONE" -> new Phone(conditionConverter.convert(hardwareTypeSoap.getCondition()));
            case "MONITOR" -> new Monitor(conditionConverter.convert(hardwareTypeSoap.getCondition()));
            default -> throw new RuntimeException("Invalid Hardware Type");
        };
        hardwareType.setId(hardwareTypeSoap.getId());
        return hardwareType;
    }
}
