package org.pl.adapter.data.repositories;

import jakarta.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.pl.adapter.data.model.*;
import org.pl.adapter.data.model.exceptions.HardwareEntException;
import org.pl.adapter.data.model.exceptions.RepositoryEntException;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(ArquillianExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RepairEntRepositoryIT {
    @Inject
    private HardwareEntRepository hardwareEntRepository;
    @Inject
    private ClientEntRepository clientEntRepository;
    @Inject
    private RepairEntRepository repairEntRepository;

    private HardwareEnt hardwareEnt;
    private ClientEnt userEnt;
    private ClientEnt employeeEnt;
    private ClientEnt adminEnt;


    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "org.pl")
                .addPackages(true, "org.hamcrest")
                .addAsResource(new File("src/main/resources/"), "")
                .addAsResource(new File("target/classes/META-INF/"), "META-INF/")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @BeforeEach
    void setUp() throws RepositoryEntException {
        if (hardwareEntRepository.getAllHardwares().isEmpty()) {
            hardwareEnt = HardwareEnt.builder()
                .archive(false)
                .price(100)
                .hardwareTypeEnt(new ComputerEnt())
                .build();
            userEnt = ClientEnt.builder()
                .username("janko333")
                .password("password")
                .archive(false)
                .balance(100.0)
                .firstName("Janek")
                .lastName("Kowalski")
                .phoneNumber("123456789")
                .clientTypeEnt(new BasicEnt())
                .addressEnt(AddressEnt.builder()
                    .city("Lodz")
                    .street("Przybyszewskiego")
                    .number("21")
                    .build())
                .clientAccessTypeEnt(ClientAccessTypeEnt.USERS)
                .build();
            employeeEnt = ClientEnt.builder()
                .username("Username")
                .password("Password")
                .archive(false)
                .balance(100.0)
                .firstName("Jan")
                .lastName("Kwiatkowski")
                .phoneNumber("123456789")
                .clientTypeEnt(new BasicEnt())
                .addressEnt(AddressEnt.builder()
                    .city("Lodz")
                    .street("Karpacka")
                    .number("9")
                    .build())
                .clientAccessTypeEnt(ClientAccessTypeEnt.EMPLOYEES)
                .build();
            adminEnt = ClientEnt.builder()
                .username("admino23")
                .password("password")
                .archive(false)
                .balance(100.0)
                .firstName("Jakub")
                .lastName("Czarodziej")
                .phoneNumber("123456789")
                .clientTypeEnt(new BasicEnt())
                .addressEnt(AddressEnt.builder()
                    .city("Lodz")
                    .street("Karpacka")
                    .number("9")
                    .build())
                .clientAccessTypeEnt(ClientAccessTypeEnt.EMPLOYEES)
                .build();

            hardwareEntRepository.saveHardware(hardwareEnt);
            userEnt = clientEntRepository.saveClient(userEnt);
            employeeEnt = clientEntRepository.saveClient(employeeEnt);
            adminEnt = clientEntRepository.saveClient(adminEnt);
        }
    }

    @Test
    @Order(1)
    void shouldProperlySaveRepair() throws RepositoryEntException {
        DateRangeEnt validDateRange = new DateRangeEnt(
                        Date.from(LocalDateTime.now().toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                        Date.from(LocalDateTime.now().plusDays(1).toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        RepairEnt repairEnt = RepairEnt.builder()
                .archive(false)
                .clientEnt(userEnt)
                .hardwareEnt(hardwareEnt)
                .dateRangeEnt(validDateRange)
                .build();
        RepairEnt savedRepair = repairEntRepository.saveRepair(repairEnt);
        assertThat(savedRepair, is(notNullValue()));
        assertThat(savedRepair.getId(), is(notNullValue()));
        assertThat(savedRepair.getHardwareEnt(), is(equalTo(hardwareEnt)));
        assertThat(savedRepair.getClientEnt(), is(equalTo(userEnt)));
        assertThat(savedRepair.getArchive(), is(equalTo(false)));
        assertThat(savedRepair.getDateRangeEnt(), is(equalTo(validDateRange)));
    }

    @Test
    @Order(2)
    void shouldThrowExceptionWhenSavingRepairWithInvalidClient() {
        DateRangeEnt validDateRange = new DateRangeEnt(
                Date.from(LocalDateTime.now().toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(LocalDateTime.now().plusDays(1).toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        RepairEnt repairEnt = RepairEnt.builder()
                .archive(false)
                .clientEnt(ClientEnt.builder().build())
                .hardwareEnt(hardwareEnt)
                .dateRangeEnt(validDateRange)
                .build();
        assertThrows(RepositoryEntException.class, () -> repairEntRepository.saveRepair(repairEnt));
    }

    @Test
    @Order(3)
    void shouldThrowExceptionWhenSavingRepairWithInvalidHardware() {
        DateRangeEnt validDateRange = new DateRangeEnt(
                Date.from(LocalDateTime.now().toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(LocalDateTime.now().plusDays(1).toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        RepairEnt repairEnt = RepairEnt.builder()
                .archive(false)
                .clientEnt(userEnt)
                .hardwareEnt(HardwareEnt.builder().build())
                .dateRangeEnt(validDateRange)
                .build();
        assertThrows(RepositoryEntException.class, () -> repairEntRepository.saveRepair(repairEnt));
    }

    @Test
    @Order(4)
    void shouldThrowExceptionWhenSavingRepairWithNullHardware() {
        DateRangeEnt validDateRange = new DateRangeEnt(
                Date.from(LocalDateTime.now().toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(LocalDateTime.now().plusDays(1).toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        RepairEnt repairEnt = RepairEnt.builder()
                .archive(false)
                .clientEnt(userEnt)
                .dateRangeEnt(validDateRange)
                .hardwareEnt(null)
                .build();
        assertThrows(RepositoryEntException.class, () -> repairEntRepository.saveRepair(repairEnt));
    }

    @Test
    @Order(5)
    void shouldThrowExceptionWhenSavingRepairWithNullClient() {
        DateRangeEnt validDateRange = new DateRangeEnt(
                Date.from(LocalDateTime.now().toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(LocalDateTime.now().plusDays(1).toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        RepairEnt repairEnt = RepairEnt.builder()
                .archive(false)
                .hardwareEnt(hardwareEnt)
                .dateRangeEnt(validDateRange)
                .build();
        assertThrows(RepositoryEntException.class, () -> repairEntRepository.saveRepair(repairEnt));
    }

    @Test
    @Order(6)
    void shouldThrowExceptionWhenSavingRepairWithNullDateRange() {
        RepairEnt repairEnt = RepairEnt.builder()
                .archive(false)
                .clientEnt(userEnt)
                .hardwareEnt(hardwareEnt)
                .build();
        assertThrows(RepositoryEntException.class, () -> repairEntRepository.saveRepair(repairEnt));
    }

    @Test
    @Order(7)
    void shouldProperlyRepair() throws RepositoryEntException, HardwareEntException {
        List<RepairEnt> allRepairs = repairEntRepository.getAllRepairs();
        assertThat(allRepairs, is(notNullValue()));
        assertThat(allRepairs.size(), is(equalTo(1)));
        RepairEnt repairEnt = allRepairs.get(0);
        repairEnt.setArchive(true);
        RepairEnt repairedRepair = repairEntRepository.repair(repairEnt.getId());
        assertThat(repairedRepair, is(notNullValue()));
        assertThat(repairedRepair.getId(), is(equalTo(repairEnt.getId())));
        assertThat(repairedRepair.getArchive(), is(equalTo(true)));
    }


    @Test
    @Order(8)
    void shouldProperlyGetAllRepairs() {
        List<RepairEnt> allRepairs = repairEntRepository.getAllRepairs();
        assertThat(allRepairs, is(notNullValue()));
        assertThat(allRepairs.size(), is(equalTo(1)));
    }

    @Test
    @Order(9)
    void shouldThrowExceptionWhenRepairingNonExistingRepair() {
        assertThrows(RepositoryEntException.class, () -> repairEntRepository.repair(UUID.randomUUID()));
    }

    @Test
    @Order(10)
    void shouldThrowExceptionWhenRepairingNullRepair() {
        assertThrows(RepositoryEntException.class, () -> repairEntRepository.repair(null));
    }

    @Test
    @Order(11)
    void shouldThrowExceptionWhenRepairingRepairWithNullId() {
        RepairEnt repairEnt = RepairEnt.builder()
                .archive(false)
                .clientEnt(userEnt)
                .hardwareEnt(hardwareEnt)
                .build();
        assertThrows(RepositoryEntException.class, () -> repairEntRepository.repair(repairEnt.getId()));
    }
}
