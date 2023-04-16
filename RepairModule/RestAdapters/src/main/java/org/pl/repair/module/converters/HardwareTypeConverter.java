package org.pl.repair.module.converters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.pl.repair.module.model.*;

@ApplicationScoped
public class HardwareTypeConverter {

    @Inject
    private ConditionConverter conditionConverter;
    public HardwareTypeRest convert(HardwareType hardwareType) {
        if (hardwareType == null) return null;
        HardwareTypeRest hardwareTypeRest = switch (hardwareType.getType()) {
            case "COMPUTER" -> new ComputerRest(conditionConverter.convert(hardwareType.getCondition()));
            case "CONSOLE" -> new ConsoleRest(conditionConverter.convert(hardwareType.getCondition()));
            case "PHONE" -> new PhoneRest(conditionConverter.convert(hardwareType.getCondition()));
            case "MONITOR" -> new MonitorRest(conditionConverter.convert(hardwareType.getCondition()));
            default -> throw new RuntimeException("Invalid Hardware Type");
        };
        hardwareTypeRest.setId(hardwareType.getId());
        return hardwareTypeRest;
    }

    public HardwareType convert(HardwareTypeRest hardwareTypeRest) {
        if (hardwareTypeRest == null) return null;
        HardwareType hardwareType = switch (hardwareTypeRest.getType()) {
            case "COMPUTER" -> new Computer(conditionConverter.convert(hardwareTypeRest.getCondition()));
            case "CONSOLE" -> new Console(conditionConverter.convert(hardwareTypeRest.getCondition()));
            case "PHONE" -> new Phone(conditionConverter.convert(hardwareTypeRest.getCondition()));
            case "MONITOR" -> new Monitor(conditionConverter.convert(hardwareTypeRest.getCondition()));
            default -> throw new RuntimeException("Invalid Hardware Type");
        };
        hardwareType.setId(hardwareTypeRest.getId());
        return hardwareType;
    }
}
