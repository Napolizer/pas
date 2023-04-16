package org.pl.repair.module.adapter.data.repositories;

import jakarta.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.pl.repair.module.adapter.data.model.ComputerEnt;
import org.pl.repair.module.adapter.data.model.HardwareEnt;
import org.pl.repair.module.adapter.data.model.exceptions.RepositoryEntException;

import java.io.File;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(ArquillianExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HardwareEntRepositoryIT {
    @Inject
    private HardwareEntRepository hardwareEntRepository;

    private HardwareEnt hardwareEnt;

    @BeforeEach void setUp() {
        hardwareEnt = HardwareEnt.builder()
                .hardwareTypeEnt(new ComputerEnt())
                .archive(false)
                .price(100)
                .build();
    }

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "org.pl")
                .addPackages(true, "org.hamcrest")
                .addAsResource(new File("src/main/resources/"), "")
                .addAsResource(new File("target/classes/META-INF/"), "META-INF/")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    @Order(1)
    void shouldSaveHardware() throws RepositoryEntException {
            HardwareEnt savedHardware = hardwareEntRepository.saveHardware(hardwareEnt);
            assertThat(savedHardware, is(notNullValue()));
            assertThat(savedHardware.getHardwareTypeEnt(), is(equalTo(hardwareEnt.getHardwareTypeEnt())));
            assertThat(savedHardware.getPrice(), is(equalTo(hardwareEnt.getPrice())));
            assertThat(savedHardware.isArchive(), is(equalTo(hardwareEnt.isArchive())));
            assertThat(savedHardware.getId(), is(notNullValue()));
    }

    @Test
    @Order(2)
    void shouldSaveHardwareWithGivenId() throws RepositoryEntException {
        hardwareEnt.setId(UUID.randomUUID());
        HardwareEnt savedHardware = hardwareEntRepository.saveHardware(hardwareEnt);
        assertThat(savedHardware, is(notNullValue()));
        assertThat(savedHardware.getHardwareTypeEnt(), is(equalTo(hardwareEnt.getHardwareTypeEnt())));
        assertThat(savedHardware.getPrice(), is(equalTo(hardwareEnt.getPrice())));
        assertThat(savedHardware.isArchive(), is(equalTo(hardwareEnt.isArchive())));
        assertThat(savedHardware.getId(), is(equalTo(hardwareEnt.getId())));
    }

    @Test
    @Order(3)
    void shouldProperlySaveHardwareWithSameId() throws RepositoryEntException {
        hardwareEnt.setId(UUID.randomUUID());
        HardwareEnt firstSavedHardware = hardwareEntRepository.saveHardware(hardwareEnt);
        HardwareEnt secondSavedHardware = hardwareEntRepository.saveHardware(hardwareEnt);
        assertThat(firstSavedHardware.getId(), is(notNullValue()));
        assertThat(secondSavedHardware.getId(), is(notNullValue()));
        assertThat(firstSavedHardware.getId(), is(equalTo(secondSavedHardware.getId())));
    }

    @Test
    @Order(4)
    void shouldProperlySaveHardwareWithNullArchive() throws RepositoryEntException {
        hardwareEnt = HardwareEnt.builder()
                .hardwareTypeEnt(new ComputerEnt())
                .archive(null)
                .price(100)
                .build();
        HardwareEnt savedHardware = hardwareEntRepository.saveHardware(hardwareEnt);
        hardwareEnt.setId(savedHardware.getId());
        assertThat(savedHardware, is(equalTo(hardwareEnt)));
        assertThat(savedHardware.getArchive(), is(equalTo(false)));
    }

    @Test
    @Order(5)
    void shouldProperlySaveHardwareWithTrueArchive() throws RepositoryEntException {
        hardwareEnt.setArchive(true);
        hardwareEntRepository.saveHardware(hardwareEnt);
    }

    @Test
    @Order(6)
    void shouldProperlySaveHardwareWithNullPrice() throws RepositoryEntException {
        hardwareEnt = HardwareEnt.builder()
                .hardwareTypeEnt(new ComputerEnt())
                .archive(false)
                .price(null)
                .build();
        HardwareEnt savedHardware = hardwareEntRepository.saveHardware(hardwareEnt);
        hardwareEnt.setId(savedHardware.getId());
        assertThat(savedHardware, is(equalTo(hardwareEnt)));
        assertThat(savedHardware.getPrice(), is(equalTo(0)));
    }

    @Test
    @Order(7)
    void shouldThrowExceptionWhenSaveHardwareWithNullHardwareType() {
        hardwareEnt = HardwareEnt.builder()
                .hardwareTypeEnt(null)
                .archive(false)
                .price(100)
                .build();
        assertThrows(RepositoryEntException.class, () -> hardwareEntRepository.saveHardware(hardwareEnt));
    }

    @Test
    @Order(8)
    void properlyGetsHardwareById() throws RepositoryEntException {
        hardwareEnt.setId(UUID.randomUUID());
        HardwareEnt savedHardware = hardwareEntRepository.saveHardware(hardwareEnt);
        HardwareEnt hardwareById = hardwareEntRepository.getHardwareById(savedHardware.getId());
        assertThat(hardwareById, is(equalTo(savedHardware)));
    }

    @Test
    @Order(9)
    void shouldThrowExceptionWhenGetHardwareByIdWithNullId() {
        assertThrows(RepositoryEntException.class, () -> hardwareEntRepository.getHardwareById(null));
    }

    @Test
    @Order(10)
    void shouldReturnNullWhenGetsHardwareWithNonExistingId() throws RepositoryEntException {
        HardwareEnt hardwareById = hardwareEntRepository.getHardwareById(UUID.randomUUID());
        assertThat(hardwareById, is(nullValue()));
    }

    @Test
    @Order(11)
    void shouldProperlyUpdateHardwarePrice() throws RepositoryEntException {
        hardwareEnt.setId(UUID.randomUUID());
        HardwareEnt savedHardware = hardwareEntRepository.saveHardware(hardwareEnt);
        savedHardware.setPrice(200);
        HardwareEnt updatedHardware = hardwareEntRepository.updateHardware(savedHardware.getId(), savedHardware);
        assertThat(updatedHardware, is(equalTo(savedHardware)));
    }

    @Test
    @Order(12)
    void shouldProperlyUpdateHardwareArchive() throws RepositoryEntException {
        hardwareEnt.setId(UUID.randomUUID());
        HardwareEnt savedHardware = hardwareEntRepository.saveHardware(hardwareEnt);

        savedHardware.setArchive(true);
        HardwareEnt updatedHardware = hardwareEntRepository.updateHardware(savedHardware.getId(), savedHardware);
        assertThat(updatedHardware, is(equalTo(savedHardware)));

        savedHardware.setArchive(false);
        updatedHardware = hardwareEntRepository.updateHardware(savedHardware.getId(), savedHardware);
        assertThat(updatedHardware, is(equalTo(savedHardware)));
    }

    @Test
    @Order(13)
    void shouldProperlyUpdateHardwareType() throws RepositoryEntException {
        hardwareEnt.setId(UUID.randomUUID());
        HardwareEnt savedHardware = hardwareEntRepository.saveHardware(hardwareEnt);
        savedHardware.setHardwareTypeEnt(new ComputerEnt());
        HardwareEnt updatedHardware = hardwareEntRepository.updateHardware(savedHardware.getId(), savedHardware);
        assertThat(updatedHardware, is(equalTo(savedHardware)));
    }

    @Test
    @Order(14)
    void shouldThrowExceptionWhenUpdateHardwareWithNullId() {
        assertThrows(RepositoryEntException.class, () -> hardwareEntRepository.updateHardware(null, hardwareEnt));
    }

    @Test
    @Order(15)
    void shouldThrowExceptionWhenUpdateHardwareWithNullHardware() {
        assertThrows(RepositoryEntException.class, () -> hardwareEntRepository.updateHardware(UUID.randomUUID(), null));
    }

    @Test
    @Order(16)
    void shouldThrowExceptionWhenUpdateHardwareWithNonExistingId() {
        hardwareEnt.setId(UUID.randomUUID());
        assertThrows(RepositoryEntException.class, () -> hardwareEntRepository.updateHardware(UUID.randomUUID(), hardwareEnt));
    }

    @Test
    @Order(17)
    void shouldProperlyDeleteHardware() throws RepositoryEntException {
        hardwareEnt.setId(UUID.randomUUID());
        HardwareEnt savedHardware = hardwareEntRepository.saveHardware(hardwareEnt);
        hardwareEntRepository.deleteHardware(savedHardware.getId());
        HardwareEnt hardwareById = hardwareEntRepository.getHardwareById(savedHardware.getId());
        assertThat(hardwareById, is(nullValue()));
    }

    @Test
    @Order(18)
    void shouldThrowExceptionWhenDeleteHardwareWithNullId() {
        assertThrows(RepositoryEntException.class, () -> hardwareEntRepository.deleteHardware(null));
    }

    @Test
    @Order(19)
    void shouldThrowExceptionWhenDeleteHardwareWithNonExistingId() {
        assertThrows(RepositoryEntException.class, () -> hardwareEntRepository.deleteHardware(UUID.randomUUID()));
    }

    @Test
    @Order(20)
    void properlyGetsHardwareList() throws RepositoryEntException {
        List<HardwareEnt> archiveHardwareList = hardwareEntRepository.getHardwareList(false);
        List<HardwareEnt> presentHardwareList = hardwareEntRepository.getHardwareList(true);
        assertThat(archiveHardwareList, is(notNullValue()));
        assertThat(presentHardwareList, is(notNullValue()));

        hardwareEnt.setArchive(false);
        hardwareEntRepository.saveHardware(hardwareEnt);
        assertThat(hardwareEntRepository.getHardwareList(false).size(), is(equalTo(archiveHardwareList.size() + 1)));
        assertThat(hardwareEntRepository.getHardwareList(true).size(), is(equalTo(presentHardwareList.size())));

        hardwareEnt.setArchive(true);
        hardwareEntRepository.saveHardware(hardwareEnt);
        assertThat(hardwareEntRepository.getHardwareList(false).size(), is(equalTo(archiveHardwareList.size() + 1)));
        assertThat(hardwareEntRepository.getHardwareList(true).size(), is(equalTo(presentHardwareList.size() + 1)));
    }

    @Test
    @Order(21)
    void shouldProperlyGetAllHardwares() throws RepositoryEntException {
        List<HardwareEnt> allHardware = hardwareEntRepository.getAllHardwares();
        int size = allHardware.size();
        assertThat(allHardware, is(notNullValue()));
        hardwareEntRepository.saveHardware(hardwareEnt);
        allHardware = hardwareEntRepository.getAllHardwares();
        assertThat(allHardware.size(), is(equalTo(size + 1)));
    }

    @Test
    @Order(22)
    void properlyGetsAllPresentHardwares() throws RepositoryEntException {
        List<HardwareEnt> presentHardware = hardwareEntRepository.getAllPresentHardwares();
        assertThat(presentHardware, is(notNullValue()));

        hardwareEnt.setArchive(false);
        hardwareEntRepository.saveHardware(hardwareEnt);
        assertThat(hardwareEntRepository.getAllPresentHardwares().size(), is(equalTo(presentHardware.size() + 1)));

        hardwareEnt.setArchive(true);
        hardwareEntRepository.saveHardware(hardwareEnt);
        assertThat(hardwareEntRepository.getAllPresentHardwares().size(), is(equalTo(presentHardware.size() + 1)));
    }

    @Test
    @Order(23)
    void properlyGetsAllPresentHardwareFilter() throws RepositoryEntException {
        HardwareEnt savedHardware = hardwareEntRepository.saveHardware(hardwareEnt);
        List<HardwareEnt> hardware = hardwareEntRepository.getAllPresentHardwareFilter(savedHardware.getId().toString().substring(3, 5));
        assertThat(hardware, is(notNullValue()));
        assertThat(hardware.size(), is(greaterThanOrEqualTo(1)));

        hardware = hardwareEntRepository.getAllPresentHardwareFilter("PPPPPPPPPPPPPPPPP");
        assertThat(hardware, is(notNullValue()));
        assertThat(hardware, is(empty()));
    }
}
