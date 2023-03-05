package org.pl.adapter.data.converters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.pl.adapter.data.model.*;
import org.pl.model.*;

@ApplicationScoped
public class HardwareTypeConverter {

    @Inject
    private ConditionConverter conditionConverter;
    public HardwareTypeEnt convert(HardwareType hardwareType) {
        HardwareTypeEnt hardwareTypeEnt = switch (hardwareType.getType()) {
            case "COMPUTER" -> new ComputerEnt(conditionConverter.convert(hardwareType.getCondition()));
            case "CONSOLE" -> new ConsoleEnt(conditionConverter.convert(hardwareType.getCondition()));
            case "PHONE" -> new PhoneEnt(conditionConverter.convert(hardwareType.getCondition()));
            case "MONITOR" -> new MonitorEnt(conditionConverter.convert(hardwareType.getCondition()));
            default -> throw new RuntimeException("Invalid Hardware Type");
        };
        hardwareTypeEnt.setId(hardwareType.getId());
        return hardwareTypeEnt;
    }

    public HardwareType convert(HardwareTypeEnt hardwareTypeEnt) {
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
