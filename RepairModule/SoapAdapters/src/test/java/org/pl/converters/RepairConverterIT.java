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

        RepairSoap repairSoap = repairConverter.convert(repair);

        assertInstanceOf(RepairSoap.class, repairSoap);
        assertInstanceOf(ClientSoap.class, repairSoap.getClient());
        assertInstanceOf(HardwareSoap.class, repairSoap.getHardware());
        assertInstanceOf(BasicSoap.class, repairSoap.getClient().getClientType());
        assertInstanceOf(ComputerSoap.class, repairSoap.getHardware().getHardwareType());
        assertInstanceOf(AddressSoap.class, repairSoap.getClient().getAddress());
        assertInstanceOf(DateRangeSoap.class, repairSoap.getDateRange());
        assertEquals(repair.getId(), repairSoap.getId());
        assertEquals(repair.getArchive(), repairSoap.getArchive());
        assertEquals(repair.getClient().getClientType().getType(), repairSoap.getClient().getClientType().getType());
        assertEquals(repair.getClient().getClientType().getFactor(), repairSoap.getClient().getClientType().getFactor());
        assertEquals(repair.getClient().getClientType().getMaxRepairs(), repairSoap.getClient().getClientType().getMaxRepairs());
        assertEquals(repair.getClient().getAddress().getCity(), repairSoap.getClient().getAddress().getCity());
        assertEquals(repair.getClient().getAddress().getNumber(), repairSoap.getClient().getAddress().getNumber());
        assertEquals(repair.getClient().getAddress().getStreet(), repairSoap.getClient().getAddress().getStreet());
        assertEquals(repair.getClient().getId(), repairSoap.getClient().getId());
        assertEquals(repair.getClient().getFirstName(), repairSoap.getClient().getFirstName());
        assertEquals(repair.getClient().getLastName(), repairSoap.getClient().getLastName());
        assertEquals(repair.getClient().getUsername(), repairSoap.getClient().getUsername());
        assertEquals(repair.getClient().getPassword(), repairSoap.getClient().getPassword());
        assertEquals(repair.getClient().getArchive(), repairSoap.getClient().getArchive());
        assertEquals(repair.getClient().getBalance(), repairSoap.getClient().getBalance());
        assertEquals(repair.getClient().getPhoneNumber(), repairSoap.getClient().getPhoneNumber());
        assertEquals(ClientAccessTypeSoap.ADMINISTRATORS, repairSoap.getClient().getClientAccessType());
        assertEquals(ConditionSoap.AVERAGE, repairSoap.getHardware().getHardwareType().getCondition());
        assertEquals(repair.getHardware().getHardwareType().getType(), repairSoap.getHardware().getHardwareType().getType());
        assertEquals(repair.getHardware().getId(), repairSoap.getHardware().getId());
        assertEquals(repair.getHardware().getArchive(), repairSoap.getHardware().getArchive());
        assertEquals(repair.getHardware().getPrice(), repairSoap.getHardware().getPrice());
        assertEquals(repair.getDateRange().getStartDate(), repairSoap.getDateRange().getStartDate());
        assertEquals(repair.getDateRange().getEndDate(), repairSoap.getDateRange().getEndDate());
    }

    @Test
    void convertRepairFromSoapToDomainModelTest() {
        ClientTypeSoap basicClientTypeSoap = new BasicSoap();

        AddressSoap addressSoap = new AddressSoap("c", "2", "c");

        ClientSoap clientSoap = new ClientSoap(UUID.randomUUID(), "s", "s", false, 200.5, "a", "b", "33", basicClientTypeSoap,
                addressSoap, ClientAccessTypeSoap.ADMINISTRATORS);

        HardwareTypeSoap computerSoap = new ComputerSoap(ConditionSoap.AVERAGE);

        HardwareSoap hardwareSoap = new HardwareSoap(UUID.randomUUID(), false, 200, computerSoap);

        DateRangeSoap dateRangeSoap = new DateRangeSoap(new Date(), new Date());

        RepairSoap repairSoap = new RepairSoap(UUID.randomUUID(), false, clientSoap, hardwareSoap, dateRangeSoap);

        Repair repair = repairConverter.convert(repairSoap);

        assertInstanceOf(Repair.class, repair);
        assertInstanceOf(Client.class, repair.getClient());
        assertInstanceOf(Hardware.class, repair.getHardware());
        assertInstanceOf(Basic.class, repair.getClient().getClientType());
        assertInstanceOf(Computer.class, repair.getHardware().getHardwareType());
        assertInstanceOf(Address.class, repair.getClient().getAddress());
        assertInstanceOf(DateRange.class, repair.getDateRange());
        assertEquals(repairSoap.getId(), repair.getId());
        assertEquals(repairSoap.getArchive(), repair.getArchive());
        assertEquals(repairSoap.getClient().getClientType().getType(), repair.getClient().getClientType().getType());
        assertEquals(repairSoap.getClient().getClientType().getFactor(), repair.getClient().getClientType().getFactor());
        assertEquals(repairSoap.getClient().getClientType().getMaxRepairs(), repair.getClient().getClientType().getMaxRepairs());
        assertEquals(repairSoap.getClient().getAddress().getCity(), repair.getClient().getAddress().getCity());
        assertEquals(repairSoap.getClient().getAddress().getNumber(), repair.getClient().getAddress().getNumber());
        assertEquals(repairSoap.getClient().getAddress().getStreet(), repair.getClient().getAddress().getStreet());
        assertEquals(repairSoap.getClient().getId(), repair.getClient().getId());
        assertEquals(repairSoap.getClient().getFirstName(), repair.getClient().getFirstName());
        assertEquals(repairSoap.getClient().getLastName(), repair.getClient().getLastName());
        assertEquals(repairSoap.getClient().getUsername(), repair.getClient().getUsername());
        assertEquals(repairSoap.getClient().getPassword(), repair.getClient().getPassword());
        assertEquals(repairSoap.getClient().getArchive(), repair.getClient().getArchive());
        assertEquals(repairSoap.getClient().getBalance(), repair.getClient().getBalance());
        assertEquals(repairSoap.getClient().getPhoneNumber(), repair.getClient().getPhoneNumber());
        assertEquals(ClientAccessType.ADMINISTRATORS, repair.getClient().getClientAccessType());
        assertEquals(Condition.AVERAGE, repair.getHardware().getHardwareType().getCondition());
        assertEquals(repairSoap.getHardware().getHardwareType().getType(), repair.getHardware().getHardwareType().getType());
        assertEquals(repairSoap.getHardware().getId(), repair.getHardware().getId());
        assertEquals(repairSoap.getHardware().getArchive(), repair.getHardware().getArchive());
        assertEquals(repairSoap.getHardware().getPrice(), repair.getHardware().getPrice());
        assertEquals(repairSoap.getDateRange().getStartDate(), repair.getDateRange().getStartDate());
        assertEquals(repairSoap.getDateRange().getEndDate(), repair.getDateRange().getEndDate());
    }
}
