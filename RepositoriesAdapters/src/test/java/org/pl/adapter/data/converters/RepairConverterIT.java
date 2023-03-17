package org.pl.adapter.data.converters;

import jakarta.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.pl.adapter.data.model.*;
import org.pl.model.*;

import java.io.File;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@ExtendWith(ArquillianExtension.class)
public class RepairConverterIT {
    @Inject
    private RepairConverter repairConverter;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "org.pl")
                .addPackages(true, "org.hamcrest")
                .addAsResource(new File("src/main/resources/"),"")
                .addAsResource(new File("target/classes/META-INF/"), "META-INF/")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

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
        assertInstanceOf(ClientEnt.class, repairEnt.getClientEnt());
        assertInstanceOf(HardwareEnt.class, repairEnt.getHardwareEnt());
        assertInstanceOf(BasicEnt.class, repairEnt.getClientEnt().getClientTypeEnt());
        assertInstanceOf(ComputerEnt.class, repairEnt.getHardwareEnt().getHardwareTypeEnt());
        assertInstanceOf(AddressEnt.class, repairEnt.getClientEnt().getAddressEnt());
        assertInstanceOf(DateRangeEnt.class, repairEnt.getDateRangeEnt());
        assertEquals(repair.getId(), repairEnt.getId());
        assertEquals(repair.getArchive(), repairEnt.isArchive());
        assertEquals(repair.getClient().getClientType().getType(), repairEnt.getClientEnt().getClientTypeEnt().getType());
        assertEquals(repair.getClient().getClientType().getFactor(), repairEnt.getClientEnt().getClientTypeEnt().getFactor());
        assertEquals(repair.getClient().getClientType().getMaxRepairs(), repairEnt.getClientEnt().getClientTypeEnt().getMaxRepairs());
        assertEquals(repair.getClient().getAddress().getCity(), repairEnt.getClientEnt().getAddressEnt().getCity());
        assertEquals(repair.getClient().getAddress().getNumber(), repairEnt.getClientEnt().getAddressEnt().getNumber());
        assertEquals(repair.getClient().getAddress().getStreet(), repairEnt.getClientEnt().getAddressEnt().getStreet());
        assertEquals(repair.getClient().getId(), repairEnt.getClientEnt().getId());
        assertEquals(repair.getClient().getFirstName(), repairEnt.getClientEnt().getFirstName());
        assertEquals(repair.getClient().getLastName(), repairEnt.getClientEnt().getLastName());
        assertEquals(repair.getClient().getUsername(), repairEnt.getClientEnt().getUsername());
        assertEquals(repair.getClient().getPassword(), repairEnt.getClientEnt().getPassword());
        assertEquals(repair.getClient().getArchive(), repairEnt.getClientEnt().isArchive());
        assertEquals(repair.getClient().getBalance(), repairEnt.getClientEnt().getBalance());
        assertEquals(repair.getClient().getPhoneNumber(), repairEnt.getClientEnt().getPhoneNumber());
        assertEquals(ClientAccessTypeEnt.ADMINISTRATORS, repairEnt.getClientEnt().getClientAccessTypeEnt());
        assertEquals(ConditionEnt.AVERAGE, repairEnt.getHardwareEnt().getHardwareTypeEnt().getCondition());
        assertEquals(repair.getHardware().getHardwareType().getType(), repairEnt.getHardwareEnt().getHardwareTypeEnt().getType());
        assertEquals(repair.getHardware().getId(), repairEnt.getHardwareEnt().getId());
        assertEquals(repair.getHardware().getArchive(), repairEnt.getHardwareEnt().isArchive());
        assertEquals(repair.getHardware().getPrice(), repairEnt.getHardwareEnt().getPrice());
        assertEquals(repair.getDateRange().getStartDate(), repairEnt.getDateRangeEnt().getStartDate());
        assertEquals(repair.getDateRange().getEndDate(), repairEnt.getDateRangeEnt().getEndDate());
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
        assertInstanceOf(Client.class, repair.getClient());
        assertInstanceOf(Hardware.class, repair.getHardware());
        assertInstanceOf(Basic.class, repair.getClient().getClientType());
        assertInstanceOf(Computer.class, repair.getHardware().getHardwareType());
        assertInstanceOf(Address.class, repair.getClient().getAddress());
        assertInstanceOf(DateRange.class, repair.getDateRange());
        assertEquals(repairEnt.getId(), repair.getId());
        assertEquals(repairEnt.isArchive(), repair.getArchive());
        assertEquals(repairEnt.getClientEnt().getClientTypeEnt().getType(), repair.getClient().getClientType().getType());
        assertEquals(repairEnt.getClientEnt().getClientTypeEnt().getFactor(), repair.getClient().getClientType().getFactor());
        assertEquals(repairEnt.getClientEnt().getClientTypeEnt().getMaxRepairs(), repair.getClient().getClientType().getMaxRepairs());
        assertEquals(repairEnt.getClientEnt().getAddressEnt().getCity(), repair.getClient().getAddress().getCity());
        assertEquals(repairEnt.getClientEnt().getAddressEnt().getNumber(), repair.getClient().getAddress().getNumber());
        assertEquals(repairEnt.getClientEnt().getAddressEnt().getStreet(), repair.getClient().getAddress().getStreet());
        assertEquals(repairEnt.getClientEnt().getId(), repair.getClient().getId());
        assertEquals(repairEnt.getClientEnt().getFirstName(), repair.getClient().getFirstName());
        assertEquals(repairEnt.getClientEnt().getLastName(), repair.getClient().getLastName());
        assertEquals(repairEnt.getClientEnt().getUsername(), repair.getClient().getUsername());
        assertEquals(repairEnt.getClientEnt().getPassword(), repair.getClient().getPassword());
        assertEquals(repairEnt.getClientEnt().isArchive(), repair.getClient().getArchive());
        assertEquals(repairEnt.getClientEnt().getBalance(), repair.getClient().getBalance());
        assertEquals(repairEnt.getClientEnt().getPhoneNumber(), repair.getClient().getPhoneNumber());
        assertEquals(ClientAccessType.ADMINISTRATORS, repair.getClient().getClientAccessType());
        assertEquals(Condition.AVERAGE, repair.getHardware().getHardwareType().getCondition());
        assertEquals(repairEnt.getHardwareEnt().getHardwareTypeEnt().getType(), repair.getHardware().getHardwareType().getType());
        assertEquals(repairEnt.getHardwareEnt().getId(), repair.getHardware().getId());
        assertEquals(repairEnt.getHardwareEnt().isArchive(), repair.getHardware().getArchive());
        assertEquals(repairEnt.getHardwareEnt().getPrice(), repair.getHardware().getPrice());
        assertEquals(repairEnt.getDateRangeEnt().getStartDate(), repair.getDateRange().getStartDate());
        assertEquals(repairEnt.getDateRangeEnt().getEndDate(), repair.getDateRange().getEndDate());
    }
}
