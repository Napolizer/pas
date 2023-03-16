package org.pl.converters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.pl.model.*;

@ApplicationScoped
public class HardwareTypeConverter {

    @Inject
    private ConditionConverter conditionConverter;
    public HardwareTypeRest convert(HardwareType hardwareType) {
        if (hardwareType == null) return null;
        HardwareTypeRest hardwareTypeEnt = switch (hardwareType.getType()) {
            case "COMPUTER" -> new ComputerRest(conditionConverter.convert(hardwareType.getCondition()));
            case "CONSOLE" -> new ConsoleRest(conditionConverter.convert(hardwareType.getCondition()));
            case "PHONE" -> new PhoneRest(conditionConverter.convert(hardwareType.getCondition()));
            case "MONITOR" -> new MonitorRest(conditionConverter.convert(hardwareType.getCondition()));
            default -> throw new RuntimeException("Invalid Hardware Type");
        };
        hardwareTypeEnt.setId(hardwareType.getId());
        return hardwareTypeEnt;
    }

    public HardwareType convert(HardwareTypeRest hardwareTypeEnt) {
        if (hardwareTypeEnt == null) return null;
        HardwareType hardwareType = switch (hardwareTypeEnt.getType()) {
            case "COMPUTER" -> new Computer(conditionConverter.convert(hardwareTypeEnt.getCondition()));
            case "CONSOLE" -> new Console(conditionConverter.convert(hardwareTypeEnt.getCondition()));
            case "PHONE" -> new Phone(conditionConverter.convert(hardwareTypeEnt.getCondition()));
            case "MONITOR" -> new Monitor(conditionConverter.convert(hardwareTypeEnt.getCondition()));
            default -> throw new RuntimeException("Invalid Hardware Type");
        };
        hardwareType.setId(hardwareTypeEnt.getId());
        return hardwareType;
    }
}
