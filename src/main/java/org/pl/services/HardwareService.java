package org.pl.services;

import org.pl.exceptions.ServiceException;
import org.pl.model.Condition;
import org.pl.model.Hardware;
import org.pl.model.HardwareType;

public class HardwareService {
    private HardwareRepository hardwareRepository;

    public Hardware add(int price, HardwareType hardwareType) {
        Hardware hardware = Hardware.builder()
                .id(hardwareRepository.getSize())
                .price(price)
                .hardwareType(hardwareType)
                .build();
        hardwareRepository.add(hardware);
        return hardware;
    }

    public Hardware add(int price, String type, Condition condition) throws ServiceException {
        HardwareType hardwareType = null;
        type = type.toLowerCase();

        if (type.equals("computer"))
            hardwareType = HardwareType.builder()
                    .condition(condition)
                    .build();
        else if (type.equals("console"))
            hardwareType = HardwareType.builder()
                    .condition(condition)
                    .build();
        else if (type.equals("monitor"))
            hardwareType = HardwareType.builder()
                    .condition(condition)
                    .build();
        else if (type.equals("phone"))
            hardwareType = HardwareType.builder()
                    .condition(condition)
                    .build();

        if (hardwareType == null)
            throw new ServiceException(ServiceException.HARDWARE_SERVICE_INVALID_HARDWARE_EXCEPTION);

        Hardware hardware = Hardware.builder()
                .id(hardwareRepository.getSize())
                .price(price)
                .hardwareType(hardwareType)
                .build();
        hardwareRepository.add(hardware);
        return hardware;
    }

    public Hardware add(int price, String type, String condition) throws ServiceException {
        HardwareType hardwareType = null;
        Condition conditionEnum = null;
        condition = condition.toLowerCase();

        if (condition.equals("unrepariable"))
            conditionEnum = Condition.UNREPAIRABLE;
        else if (condition.equals("verybad"))
            conditionEnum = Condition.VERY_BAD;
        else if (condition.equals("bad"))
            conditionEnum = Condition.BAD;
        else if (condition.equals("average"))
            conditionEnum = Condition.AVERAGE;
        else if (condition.equals("dusty"))
            conditionEnum = Condition.DUSTY;
        else if (condition.equals("fine"))
            conditionEnum = Condition.FINE;

        if (conditionEnum == null)
            throw new ServiceException(ServiceException.HARDWARE_SERVICE_INVALID_CONDITION_EXCEPTION);

        type = type.toLowerCase();

        if (type.equals("computer"))
            hardwareType = HardwareType.builder()
                    .condition(conditionEnum)
                    .build();
        else if (type.equals("console"))
            hardwareType = HardwareType.builder()
                    .condition(conditionEnum)
                    .build();
        else if (type.equals("monitor"))
            hardwareType = HardwareType.builder()
                    .condition(conditionEnum)
                    .build();
        else if (type.equals("phone"))
            hardwareType = HardwareType.builder()
                    .condition(conditionEnum)
                    .build();

        if (hardwareType == null)
            throw new ServiceException(ServiceException.HARDWARE_SERVICE_INVALID_HARDWARE_EXCEPTION);

        Hardware hardware = Hardware.builder()
                .id(hardwareRepository.getSize())
                .price(price)
                .hardwareType(hardwareType)
                .build();
        hardwareRepository.add(hardware);
        return hardware;
    }

    public Hardware get(int ID) {
        return hardwareRepository.get(ID);
    }

    public int getArchiveSize() {
        return hardwareRepository.getSize(false);
    }

    public String getInfo(int ID) {
        return hardwareRepository.get(ID).toString();
    }

    public int getPresentSize() {
        return hardwareRepository.getSize(true);
    }

    public String getReport() {
        return hardwareRepository.toString();
    }

    public void remove(int ID) {
        hardwareRepository.archivise(ID);
    }
}
