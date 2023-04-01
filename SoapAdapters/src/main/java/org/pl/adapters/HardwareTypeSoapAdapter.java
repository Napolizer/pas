package org.pl.adapters;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.pl.model.*;
import org.pl.model.dto.HardwareTypeSoapDto;

public class HardwareTypeSoapAdapter extends XmlAdapter<HardwareTypeSoapDto, HardwareTypeSoap> {
    @Override
    public HardwareTypeSoap unmarshal(HardwareTypeSoapDto v) throws Exception {
        return switch (v.getType().toUpperCase()) {
            case "CONSOLE" -> new ConsoleSoap(ConditionSoap.valueOf(v.getCondition()));
            case "COMPUTER" -> new ComputerSoap(ConditionSoap.valueOf(v.getCondition()));
            case "MONITOR" -> new MonitorSoap(ConditionSoap.valueOf(v.getCondition()));
            case "PHONE" -> new PhoneSoap(ConditionSoap.valueOf(v.getCondition()));
            default -> throw new Exception("Unknown hardware type: " + v.getType());
        };
    }

    @Override
    public HardwareTypeSoapDto marshal(HardwareTypeSoap v) {
        HardwareTypeSoapDto hardwareTypeSoapDto = new HardwareTypeSoapDto();
        hardwareTypeSoapDto.setType(v.getType());
        hardwareTypeSoapDto.setCondition(v.getCondition().toString());
        return hardwareTypeSoapDto;
    }
}
