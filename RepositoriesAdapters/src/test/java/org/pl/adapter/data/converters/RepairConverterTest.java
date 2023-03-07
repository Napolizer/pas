package org.pl.adapter.data.converters;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pl.adapter.data.model.*;
import org.pl.model.*;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@ExtendWith(MockitoExtension.class)
public class RepairConverterTest {
    @Mock
    private ClientConverter clientConverter;
    @Mock
    private HardwareConverter hardwareConverter;
    @Mock
    private DateRangeConverter dateRangeConverter;
    @InjectMocks
    private RepairConverter repairConverter;

    @Test
    void convertRepairFromDomainToEntModelTest() {
        ClientType basicClientType = new Basic();

        Address address = Address.builder()
                .city("c")
                .number("2")
                .street("c")
                .build();

        Client client = Client.builder()
                .id(UUID.randomUUID())
                .firstName("a")
                .lastName("b")
                .username("s")
                .password("s")
                .archive(false)
                .balance(200.5)
                .phoneNumber("33")
                .clientType(basicClientType)
                .address(address)
                .clientAccessType(ClientAccessType.ADMINISTRATORS)
                .build();

        HardwareType computer = Computer.builder()
                .condition(Condition.AVERAGE)
                .type("COMPUTER")
                .build();

        Hardware hardware = Hardware.builder()
                .id(UUID.randomUUID())
                .archive(false)
                .price(200)
                .hardwareType(computer)
                .build();

        DateRange dateRange = new DateRange(new Date(), new Date());

        Repair repair = Repair.builder()
                .id(UUID.randomUUID())
                .archive(false)
                .client(client)
                .hardware(hardware)
                .dateRange(dateRange)
                .build();

        RepairEnt repairEnt = repairConverter.convert(repair);

        assertInstanceOf(RepairEnt.class, repairEnt);
        assertEquals(repair.getId(), repairEnt.getId());
        assertEquals(repair.isArchive(), repairEnt.isArchive());
    }

    @Test
    void convertRepairFromEntToDomainModelTest() {
        ClientTypeEnt basicClientTypeEnt = new BasicEnt();

        AddressEnt addressEnt = AddressEnt.builder()
                .city("c")
                .number("2")
                .street("c")
                .build();

        ClientEnt clientEnt = ClientEnt.builder()
                .id(UUID.randomUUID())
                .firstName("a")
                .lastName("b")
                .username("s")
                .password("s")
                .archive(false)
                .balance(200.5)
                .phoneNumber("33")
                .clientTypeEnt(basicClientTypeEnt)
                .addressEnt(addressEnt)
                .clientAccessTypeEnt(ClientAccessTypeEnt.ADMINISTRATORS)
                .build();

        HardwareTypeEnt computerEnt = ComputerEnt.builder()
                .condition(ConditionEnt.AVERAGE)
                .type("COMPUTER")
                .build();

        HardwareEnt hardwareEnt = HardwareEnt.builder()
                .id(UUID.randomUUID())
                .archive(false)
                .price(200)
                .hardwareTypeEnt(computerEnt)
                .build();

        DateRangeEnt dateRangeEnt = new DateRangeEnt(new Date(), new Date());

        RepairEnt repairEnt = RepairEnt.builder()
                .id(UUID.randomUUID())
                .archive(false)
                .clientEnt(clientEnt)
                .hardwareEnt(hardwareEnt)
                .dateRangeEnt(dateRangeEnt)
                .build();

        Repair repair = repairConverter.convert(repairEnt);

        assertInstanceOf(Repair.class, repair);
        assertEquals(repairEnt.getId(), repair.getId());
        assertEquals(repairEnt.isArchive(), repair.isArchive());
    }
}
