package org.pl.converters;

import jakarta.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

        RepairRest repairRest = repairConverter.convert(repair);

        assertInstanceOf(RepairRest.class, repairRest);
        assertInstanceOf(ClientRest.class, repairRest.getClient());
        assertInstanceOf(HardwareRest.class, repairRest.getHardware());
        assertInstanceOf(BasicRest.class, repairRest.getClient().getClientType());
        assertInstanceOf(ComputerRest.class, repairRest.getHardware().getHardwareType());
        assertInstanceOf(AddressRest.class, repairRest.getClient().getAddress());
        assertInstanceOf(DateRangeRest.class, repairRest.getDateRange());
        assertEquals(repair.getId(), repairRest.getId());
        assertEquals(repair.isArchive(), repairRest.isArchive());
        assertEquals(repair.getClient().getClientType().getType(), repairRest.getClient().getClientType().getType());
        assertEquals(repair.getClient().getClientType().getFactor(), repairRest.getClient().getClientType().getFactor());
        assertEquals(repair.getClient().getClientType().getMaxRepairs(), repairRest.getClient().getClientType().getMaxRepairs());
        assertEquals(repair.getClient().getAddress().getCity(), repairRest.getClient().getAddress().getCity());
        assertEquals(repair.getClient().getAddress().getNumber(), repairRest.getClient().getAddress().getNumber());
        assertEquals(repair.getClient().getAddress().getStreet(), repairRest.getClient().getAddress().getStreet());
        assertEquals(repair.getClient().getId(), repairRest.getClient().getId());
        assertEquals(repair.getClient().getFirstName(), repairRest.getClient().getFirstName());
        assertEquals(repair.getClient().getLastName(), repairRest.getClient().getLastName());
        assertEquals(repair.getClient().getUsername(), repairRest.getClient().getUsername());
        assertEquals(repair.getClient().getPassword(), repairRest.getClient().getPassword());
        assertEquals(repair.getClient().isArchive(), repairRest.getClient().isArchive());
        assertEquals(repair.getClient().getBalance(), repairRest.getClient().getBalance());
        assertEquals(repair.getClient().getPhoneNumber(), repairRest.getClient().getPhoneNumber());
        assertEquals(ClientAccessTypeRest.ADMINISTRATORS, repairRest.getClient().getClientAccessType());
        assertEquals(ConditionRest.AVERAGE, repairRest.getHardware().getHardwareType().getCondition());
        assertEquals(repair.getHardware().getHardwareType().getType(), repairRest.getHardware().getHardwareType().getType());
        assertEquals(repair.getHardware().getId(), repairRest.getHardware().getId());
        assertEquals(repair.getHardware().isArchive(), repairRest.getHardware().isArchive());
        assertEquals(repair.getHardware().getPrice(), repairRest.getHardware().getPrice());
        assertEquals(repair.getDateRange().getStartDate(), repairRest.getDateRange().getStartDate());
        assertEquals(repair.getDateRange().getEndDate(), repairRest.getDateRange().getEndDate());
    }

    @Test
    void convertRepairFromEntToDomainModelTest() {
        ClientTypeRest basicClientTypeRest = new BasicRest();

        AddressRest addressRest = AddressRest.builder()
                .city("c")
                .number("2")
                .street("c")
                .build();

        ClientRest clientRest = ClientRest.builder()
                .id(UUID.randomUUID())
                .firstName("a")
                .lastName("b")
                .username("s")
                .password("s")
                .archive(false)
                .balance(200.5)
                .phoneNumber("33")
                .clientType(basicClientTypeRest)
                .address(addressRest)
                .clientAccessType(ClientAccessTypeRest.ADMINISTRATORS)
                .build();

        HardwareTypeRest computerRest = ComputerRest.builder()
                .condition(ConditionRest.AVERAGE)
                .type("COMPUTER")
                .build();

        HardwareRest hardwareRest = HardwareRest.builder()
                .id(UUID.randomUUID())
                .archive(false)
                .price(200)
                .hardwareType(computerRest)
                .build();

        DateRangeRest dateRangeRest = new DateRangeRest(new Date(), new Date());

        RepairRest repairEnt = RepairRest.builder()
                .id(UUID.randomUUID())
                .archive(false)
                .client(clientRest)
                .hardware(hardwareRest)
                .dateRange(dateRangeRest)
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
        assertEquals(repairEnt.isArchive(), repair.isArchive());
        assertEquals(repairEnt.getClient().getClientType().getType(), repair.getClient().getClientType().getType());
        assertEquals(repairEnt.getClient().getClientType().getFactor(), repair.getClient().getClientType().getFactor());
        assertEquals(repairEnt.getClient().getClientType().getMaxRepairs(), repair.getClient().getClientType().getMaxRepairs());
        assertEquals(repairEnt.getClient().getAddress().getCity(), repair.getClient().getAddress().getCity());
        assertEquals(repairEnt.getClient().getAddress().getNumber(), repair.getClient().getAddress().getNumber());
        assertEquals(repairEnt.getClient().getAddress().getStreet(), repair.getClient().getAddress().getStreet());
        assertEquals(repairEnt.getClient().getId(), repair.getClient().getId());
        assertEquals(repairEnt.getClient().getFirstName(), repair.getClient().getFirstName());
        assertEquals(repairEnt.getClient().getLastName(), repair.getClient().getLastName());
        assertEquals(repairEnt.getClient().getUsername(), repair.getClient().getUsername());
        assertEquals(repairEnt.getClient().getPassword(), repair.getClient().getPassword());
        assertEquals(repairEnt.getClient().isArchive(), repair.getClient().isArchive());
        assertEquals(repairEnt.getClient().getBalance(), repair.getClient().getBalance());
        assertEquals(repairEnt.getClient().getPhoneNumber(), repair.getClient().getPhoneNumber());
        assertEquals(ClientAccessType.ADMINISTRATORS, repair.getClient().getClientAccessType());
        assertEquals(Condition.AVERAGE, repair.getHardware().getHardwareType().getCondition());
        assertEquals(repairEnt.getHardware().getHardwareType().getType(), repair.getHardware().getHardwareType().getType());
        assertEquals(repairEnt.getHardware().getId(), repair.getHardware().getId());
        assertEquals(repairEnt.getHardware().isArchive(), repair.getHardware().isArchive());
        assertEquals(repairEnt.getHardware().getPrice(), repair.getHardware().getPrice());
        assertEquals(repairEnt.getDateRange().getStartDate(), repair.getDateRange().getStartDate());
        assertEquals(repairEnt.getDateRange().getEndDate(), repair.getDateRange().getEndDate());
    }
}
