package org.pl.adapter.data.repositories;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.pl.adapter.data.model.AddressEnt;
import org.pl.adapter.data.model.UserAccessTypeEnt;
import org.pl.adapter.data.model.UserEnt;
import org.pl.adapter.data.model.exceptions.RepositoryEntException;

import javax.transaction.UserTransaction;
import java.io.File;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(ArquillianExtension.class)
public class UserEntRepositoryIT {
    @Inject
    private UserEntRepository userEntRepository;

    @Inject
    private UserTransaction userTransaction;

    @Inject
    private EntityManager entityManager;

    private UserEnt validUser;
    private AddressEnt validAddress;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "org.pl")
                .addPackages(true, "org.hamcrest")
                .addAsResource(new File("src/main/resources/"),"")
                .addAsResource(new File("target/classes/META-INF/"), "META-INF/")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @BeforeEach
    void setup() {
        validAddress = AddressEnt.builder()
                .city("Lodz")
                .street("Przybyszewskiego")
                .number("21")
                .build();
        validUser = UserEnt.builder()
                .id(UUID.randomUUID())
                .username("Username")
                .password("Password")
                .archive(false)
                .firstName("Tester")
                .lastName("Testowy")
                .phoneNumber("123456789")
                .addressEnt(validAddress)
                .userAccessType(UserAccessTypeEnt.EMPLOYEES)
                .build();

        try {
            userTransaction.begin();
            entityManager.clear();
            userTransaction.commit();
        } catch (Exception ignored) {

        }


    }

    @Test
    void repositoryInjectionTest() {
        assertThat(userEntRepository, is(notNullValue()));
    }

    @Test
    void saveUserPositiveTest() throws RepositoryEntException {
        UserEnt savedUser = userEntRepository.saveUser(validUser);
        assertThat(savedUser, is(notNullValue()));
        assertThat(savedUser.getId(), is(equalTo(validUser.getId())));
        assertThat(savedUser.getUsername(), is(equalTo(validUser.getUsername())));
        assertThat(savedUser.getPassword(), is(equalTo(validUser.getPassword())));
        assertThat(savedUser.getArchive(), is(equalTo(validUser.getArchive())));
        assertThat(savedUser.getFirstName(), is(equalTo(validUser.getFirstName())));
        assertThat(savedUser.getLastName(), is(equalTo(validUser.getLastName())));
        assertThat(savedUser.getPhoneNumber(), is(equalTo(validUser.getPhoneNumber())));
        assertThat(savedUser.getAddressEnt(), is(equalTo(validUser.getAddressEnt())));
        assertThat(savedUser.getUserAccessType(), is(equalTo(validUser.getUserAccessType())));
    }

    @Test
    void saveUserNegativeTest() throws RepositoryEntException {
        UserEnt savedUser = userEntRepository.saveUser(validUser);
        assertThat(savedUser, is(notNullValue()));
        Assertions.assertThrows(RepositoryEntException.class, () -> userEntRepository.saveUser(validUser));
    }

    @Test
    void getUserByIdPositiveTest() throws RepositoryEntException {
        UserEnt savedUser = userEntRepository.saveUser(validUser);
        assertThat(savedUser, is(notNullValue()));
        assertThat(userEntRepository.getUserById(savedUser.getId()), is(notNullValue()));
        assertThat(userEntRepository.getUserById(savedUser.getId()), is(equalTo(savedUser)));
    }

    @Test
    void getUserByIdNegativeTest() {
        Assertions.assertThrows(RepositoryEntException.class, () -> userEntRepository.getUserById(UUID.randomUUID()));
    }

    @Test
    void updateUserPositiveTest() throws RepositoryEntException {
        UserEnt userEnt = UserEnt.builder()
                .id(UUID.randomUUID())
                .username("TEST")
                .password("test")
                .archive(true)
                .firstName("Test")
                .lastName("Testow")
                .phoneNumber("123456712")
                .addressEnt(AddressEnt.builder()
                        .city("Krakow")
                        .street("Krakowska")
                        .number("12")
                        .build())
                .userAccessType(UserAccessTypeEnt.ADMINISTRATORS)
                .build();
        UserEnt savedUser = userEntRepository.saveUser(validUser);
        assertThat(savedUser, is(notNullValue()));
        UserEnt updatedUser = userEntRepository.updateUser(savedUser.getId(), userEnt);

        assertThat(updatedUser.getId(), is(equalTo(savedUser.getId())));
        assertThat(updatedUser.getPassword(), is(equalTo(savedUser.getPassword())));

        assertThat(updatedUser.getUsername(), is(equalTo(userEnt.getUsername())));
        assertThat(updatedUser.getArchive(), is(equalTo(userEnt.getArchive())));
        assertThat(updatedUser.getFirstName(), is(equalTo(userEnt.getFirstName())));
        assertThat(updatedUser.getLastName(), is(equalTo(userEnt.getLastName())));
        assertThat(updatedUser.getPhoneNumber(), is(equalTo(userEnt.getPhoneNumber())));
        assertThat(updatedUser.getAddressEnt(), is(equalTo(userEnt.getAddressEnt())));
        assertThat(updatedUser.getUserAccessType(), is(equalTo(userEnt.getUserAccessType())));
    }

    @Test
    void updateUserNegativeTest() throws RepositoryEntException {
        UserEnt savedUser = userEntRepository.saveUser(validUser);
        assertThat(savedUser, is(notNullValue()));
        Assertions.assertThrows(RepositoryEntException.class, () -> userEntRepository.updateUser(UUID.randomUUID(), validUser));
        Assertions.assertThrows(RepositoryEntException.class, () -> userEntRepository.updateUser(validUser.getId(), null));
    }

    @Test
    void changePasswordPositiveTest() throws RepositoryEntException {
        UserEnt savedUser = userEntRepository.saveUser(validUser);
        assertThat(savedUser, is(notNullValue()));
        UserEnt updatedUser = userEntRepository.changePassword(validUser.getId(), "haslo123");

        assertThat(updatedUser.getId(), is(equalTo(savedUser.getId())));
        assertThat(updatedUser.getPassword(), not(is(equalTo(savedUser.getPassword()))));

        assertThat(updatedUser.getUsername(), is(equalTo(validUser.getUsername())));
        assertThat(updatedUser.getArchive(), is(equalTo(validUser.getArchive())));
        assertThat(updatedUser.getFirstName(), is(equalTo(validUser.getFirstName())));
        assertThat(updatedUser.getLastName(), is(equalTo(validUser.getLastName())));
        assertThat(updatedUser.getPhoneNumber(), is(equalTo(validUser.getPhoneNumber())));
        assertThat(updatedUser.getAddressEnt(), is(equalTo(validUser.getAddressEnt())));
        assertThat(updatedUser.getUserAccessType(), is(equalTo(validUser.getUserAccessType())));
    }

    @Test
    void changePasswordNegativeTest() throws RepositoryEntException {
        UserEnt savedUser = userEntRepository.saveUser(validUser);
        assertThat(savedUser, is(notNullValue()));
        Assertions.assertThrows(RepositoryEntException.class, () -> userEntRepository.changePassword(UUID.randomUUID(), "haslo"));
        Assertions.assertThrows(RepositoryEntException.class, () -> userEntRepository.changePassword(validUser.getId(), null));
    }

    @Test
    void deleteUserPositiveTest() throws RepositoryEntException {
        UserEnt savedUser = userEntRepository.saveUser(validUser);
        assertThat(savedUser, is(notNullValue()));
        UserEnt deletedUser = userEntRepository.deleteUser(savedUser.getId());
        assertThat(deletedUser.getArchive(), is(true));
        Assertions.assertDoesNotThrow(() -> userEntRepository.getUserById(savedUser.getId()));
    }

    @Test
    void deleteUserNegativeTest() throws RepositoryEntException {
        validUser.setArchive(true);
        UserEnt savedUser = userEntRepository.saveUser(validUser);
        assertThat(savedUser, is(notNullValue()));
        Assertions.assertThrows(RepositoryEntException.class, () -> userEntRepository.deleteUser(validUser.getId()));
        Assertions.assertThrows(RepositoryEntException.class, () -> userEntRepository.deleteUser(UUID.randomUUID()));
        Assertions.assertThrows(RepositoryEntException.class, () -> userEntRepository.deleteUser(null));
    }

    @Test
    void getUsersPositiveTest() throws RepositoryEntException {
        UserEnt savedUser1 = userEntRepository.saveUser(validUser);
        assertThat(savedUser1, is(notNullValue()));
        savedUser1.setId(UUID.randomUUID());
        savedUser1.setArchive(true);
        UserEnt savedUser2 = userEntRepository.saveUser(savedUser1);
        assertThat(savedUser2, is(notNullValue()));
        assertThat(userEntRepository.getUsers(false), is(notNullValue()));
        assertThat(userEntRepository.getUsers(true), is(notNullValue()));
    }

    @Test
    void getAllUsersPositiveTest() throws RepositoryEntException {
        UserEnt savedUser1 = userEntRepository.saveUser(validUser);
        assertThat(savedUser1, is(notNullValue()));
        assertThat(userEntRepository.getAllUsers(), is(notNullValue()));
        savedUser1.setId(UUID.randomUUID());
        UserEnt savedUser2 = userEntRepository.saveUser(savedUser1);
        assertThat(savedUser2, is(notNullValue()));
        assertThat(userEntRepository.getAllUsers(), is(notNullValue()));
    }

    @Test
    void getUserByUsernamePositiveTest() throws RepositoryEntException {
        UserEnt savedUser1 = userEntRepository.saveUser(validUser);
        assertThat(savedUser1, is(notNullValue()));
        assertThat(userEntRepository.getUserByUsername(savedUser1.getUsername()), is(equalTo(savedUser1)));
    }

    @Test
    void getUserByUsernameNegativeTest() {
        Assertions.assertThrows(RepositoryEntException.class, () -> userEntRepository.getUserByUsername("mkdmsajnajhk"));
        Assertions.assertThrows(RepositoryEntException.class, () -> userEntRepository.getUserByUsername(null));
    }

    @Test
    void getAllUsersFilterPositiveTest() throws RepositoryEntException {
        assertThat(userEntRepository.getAllUsersFilter("1234567890123456789012345678901234567890").size(), is(equalTo(0)));
        UserEnt savedUser1 = userEntRepository.saveUser(validUser);
        assertThat(savedUser1, is(notNullValue()));
        assertThat(userEntRepository.getAllUsersFilter("").size(), is(notNullValue()));
        String id = savedUser1.getId().toString();
        id = id.substring(0, id.length() - 3);
        assertThat(userEntRepository.getAllUsersFilter(id).size(), is(notNullValue()));
    }

    @Test
    void getUsersByUsernamePositiveTest() throws RepositoryEntException {
        UserEnt savedUser1 = userEntRepository.saveUser(validUser);
        assertThat(savedUser1, is(notNullValue()));
        assertThat(userEntRepository.getUsersByUsername("").size(), is(notNullValue()));
        assertThat(userEntRepository.getUsersByUsername("User").size(), is(equalTo(1)));
        assertThat(userEntRepository.getUsersByUsername("Usernameee").size(), is(equalTo(0)));
        savedUser1.setId(UUID.randomUUID());
        savedUser1.setUsername("Usernam");
        UserEnt savedUser2 = userEntRepository.saveUser(savedUser1);
        assertThat(savedUser2, is(notNullValue()));
        assertThat(userEntRepository.getUsersByUsername("").size(), is(notNullValue()));
        assertThat(userEntRepository.getUsersByUsername("User").size(), is(equalTo(2)));
        assertThat(userEntRepository.getUsersByUsername("Username").size(), is(equalTo(1)));
    }

    @Test
    void restoreUserPositiveTest() throws RepositoryEntException {
        validUser.setArchive(true);
        UserEnt savedUser = userEntRepository.saveUser(validUser);
        assertThat(savedUser, is(notNullValue()));
        UserEnt restoredUser = userEntRepository.restoreUser(savedUser.getId());
        assertThat(restoredUser.getArchive(), is(false));
    }

    @Test
    void restoreUserNegativeTest() throws RepositoryEntException {
        UserEnt savedUser = userEntRepository.saveUser(validUser);
        assertThat(savedUser, is(notNullValue()));
        Assertions.assertThrows(RepositoryEntException.class, () -> userEntRepository.restoreUser(savedUser.getId()));
        Assertions.assertThrows(RepositoryEntException.class, () -> userEntRepository.restoreUser(UUID.randomUUID()));
    }
}
