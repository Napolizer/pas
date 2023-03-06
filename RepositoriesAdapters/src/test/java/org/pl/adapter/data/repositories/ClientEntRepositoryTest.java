package org.pl.adapter.data.repositories;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.pl.adapter.data.model.AddressEnt;
import org.pl.adapter.data.model.User;
import org.jboss.shrinkwrap.descriptor.api.DescriptorImporter;

import java.io.File;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Arquillian.class)
@ExtendWith(ArquillianExtension.class)
public class ClientEntRepositoryTest {
//    @Inject
//    private ClientEntRepository clientEntRepository;
//    @Inject
//    private AddressConverter addressConverter;
//    @Inject
//    private AddressEnt addressEnt;
//    @Inject
//    private EntityManager entityManager;
//    @Inject
//    private User user;
//    @Inject
//    private ClientEntRepository clientEntRepository;

    @Deployment
    public static JavaArchive createDeployment() {
        if (! new File("src/test/resources/beans.xml").exists()) {
            throw new RuntimeException("beans.xml not found");
        }
        return ShrinkWrap.create(JavaArchive.class)
//                .addPackages(true, "org")
//                .addClass(User.class)
                .addAsManifestResource(new File("src/test/resources/beans.xml"))
                .addAsManifestResource(new File("src/test/resources/persistence.xml"));
//                .addAsResource("persistence.xml")
//                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void test() {
        assertThat(null, is(nullValue()));
//        assertThat(user, is(not(nullValue())));
//        assertThat(clientEntRepository, is(not(nullValue())));
    }
}
