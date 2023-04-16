package org.pl.repair.module.adapter.data.model;

import jakarta.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.pl.repair.module.adapter.data.model.exceptions.RepositoryEntException;
import org.pl.repair.module.adapter.data.repositories.HardwareEntRepository;

import java.io.File;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

@ExtendWith(ArquillianExtension.class)
public class UserIT {
    @Inject
    private User user;

    @Inject
    private HardwareEntRepository hardwareEntRepository;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "org.pl")
                .addPackages(true, "org.hamcrest")
                .addAsResource(new File("src/main/resources/"), "")
                .addAsResource(new File("target/classes/META-INF/"), "META-INF/")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
//                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void test() {
        assertThat(user, is(notNullValue()));
        assertThat(user.getUsername(), is("john"));
    }

    @Test
    public void testRepo() throws RepositoryEntException {
        assertThat(hardwareEntRepository, is(notNullValue()));
        HardwareEnt hardwareEnt = HardwareEnt.builder()
                .archive(false)
                .hardwareTypeEnt(new ComputerEnt())
                .price(100)
                .build();
        HardwareEnt savedHardware = hardwareEntRepository.saveHardware(hardwareEnt);
        assertThat(savedHardware, is(notNullValue()));
    }
}
