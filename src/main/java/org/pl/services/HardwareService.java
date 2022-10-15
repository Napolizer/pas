package org.pl.services;

import org.pl.exceptions.HardwareException;
import org.pl.exceptions.RepositoryException;
import org.pl.exceptions.ServiceException;
import org.pl.model.*;
import org.pl.repositories.HardwareRepository;

public class HardwareService {
    private final HardwareRepository hardwareRepository;

    public HardwareService(HardwareRepository hardwareRepository) {
        this.hardwareRepository = hardwareRepository;
    }

    public Hardware add(int price, HardwareType hardwareType) throws RepositoryException, HardwareException {
        if (price <= 0)
            throw new HardwareException(HardwareException.HARDWARE_PRICE_EXCEPTION);

        Hardware hardware = Hardware.builder()
                .id(hardwareRepository.getElements().size())
                .price(price)
                .hardwareType(hardwareType)
                .build();
        hardwareRepository.add(hardware);
        return hardware;
    }

    public Hardware add(int price, String type, Condition condition) throws ServiceException, RepositoryException, HardwareException {
        if (price <= 0)
            throw new HardwareException(HardwareException.HARDWARE_PRICE_EXCEPTION);

        HardwareType hardwareType;
        type = type.toLowerCase();

        switch (type) {
            case "computer" -> hardwareType = Computer.builder()
                    .condition(condition)
                    .build();
            case "console" -> hardwareType = Console.builder()
                    .condition(condition)
                    .build();
            case "monitor" -> hardwareType = Monitor.builder()
                    .condition(condition)
                    .build();
            case "phone" -> hardwareType = Phone.builder()
                    .condition(condition)
                    .build();
            default -> throw new ServiceException(ServiceException.HARDWARE_SERVICE_INVALID_HARDWARE_EXCEPTION);
        }

        if (hardwareType == null)
            throw new ServiceException(ServiceException.HARDWARE_SERVICE_INVALID_HARDWARE_EXCEPTION);

        Hardware hardware = Hardware.builder()
                .id(hardwareRepository.getElements().size())
                .price(price)
                .hardwareType(hardwareType)
                .build();
        hardwareRepository.add(hardware);
        return hardware;
    }

    public Hardware add(int price, String type, String condition) throws ServiceException, RepositoryException, HardwareException {
        condition = condition.toLowerCase();


        Condition c = switch (condition) {
            case "unrepairable" -> Condition.UNREPAIRABLE;
            case "very_bad" -> Condition.VERY_BAD;
            case "bad" -> Condition.BAD;
            case "average" -> Condition.AVERAGE;
            case "dusty" -> Condition.DUSTY;
            case "fine" -> Condition.FINE;
            default -> throw new ServiceException(ServiceException.HARDWARE_SERVICE_INVALID_CONDITION_EXCEPTION);
        };
        return add(price, type, c);
    }

    public Hardware get(int id) throws RepositoryException {
        return hardwareRepository.get(id);
    }

    public int getArchiveSize() throws RepositoryException {
        return hardwareRepository.getSize(false);
    }

    public String getInfo(int id) throws RepositoryException {
        return hardwareRepository.get(id).toString();
    }

    public int getPresentSize() throws RepositoryException {
        return hardwareRepository.getSize(true);
    }

    public void remove(int id) throws RepositoryException {
        hardwareRepository.archivise(id);
    }
}
