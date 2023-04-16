package org.pl.user.module.adapter.data.aggregates;


import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.pl.user.module.model.Address;
import org.pl.user.module.model.User;
import org.pl.user.module.model.UserAccessType;
import org.pl.user.module.model.exceptions.RepositoryException;

import javax.transaction.UserTransaction;
import java.io.File;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ArquillianExtension.class)
public class UserRepositoryAdapterIT {
    @Inject
    private UserRepositoryAdapter userRepositoryAdapter;
    @Inject
    private UserTransaction userTransaction;
    @Inject
    private EntityManager entityManager;
    private User validUser;
    private User validUser2;
    private Address validAddress;


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
        validAddress = Address.builder()
                .city("Lodz")
                .street("Przybyszewskiego")
                .number("21")
                .build();
        validUser = User.builder()
                .id(UUID.randomUUID())
                .username("Username")
                .password("Password")
                .archive(false)
                .firstName("Tester")
                .lastName("Testowy")
                .phoneNumber("123456789")
                .address(validAddress)
                .userAccessType(UserAccessType.EMPLOYEES)
                .build();
        validUser2 = User.builder()
                .id(UUID.randomUUID())
                .username("Username2")
                .password("Password")
                .archive(true)
                .firstName("Tester")
                .lastName("Testowy")
                .phoneNumber("123456789")
                .address(validAddress)
                .userAccessType(UserAccessType.EMPLOYEES)
                .build();
        try {
            userTransaction.begin();
            entityManager.clear();
            userTransaction.commit();
        } catch (Exception ignored) {

        }
    }

    @Test
    void createUserPositiveTest() throws RepositoryException {
        User createdUser = userRepositoryAdapter.createUser(validUser);
        assertNotNull(createdUser.getId());
        assertEquals(validUser.getUsername(), createdUser.getUsername());
        assertEquals(validUser.getPassword(), createdUser.getPassword());
        assertEquals(validUser.getArchive(), createdUser.getArchive());
        assertEquals(validUser.getFirstName(), createdUser.getFirstName());
        assertEquals(validUser.getLastName(), createdUser.getLastName());
        assertEquals(validUser.getPhoneNumber(), createdUser.getPhoneNumber());
        assertEquals(validUser.getAddress(), createdUser.getAddress());
        assertEquals(validUser.getUserAccessType(), createdUser.getUserAccessType());
    }

    @Test
    void createUserNegativeTest() {
        assertDoesNotThrow(() -> userRepositoryAdapter.createUser(validUser));
        assertThrows(RepositoryException.class, () -> userRepositoryAdapter.createUser(validUser));
    }

    @Test
    void getUserByUuidPositiveTest() throws RepositoryException {
        User createdUser = userRepositoryAdapter.createUser(validUser);
        assertEquals(validUser.getUsername(), userRepositoryAdapter.getUser(createdUser.getId()).getUsername());
    }

    @Test
    void getUserByUuidNegativeTest() {
        assertThrows(RepositoryException.class, () -> userRepositoryAdapter.getUser(validUser.getId()));
    }

    @Test
    void changePasswordPositiveTest() throws RepositoryException {
        User createdUser = userRepositoryAdapter.createUser(validUser);
        assertEquals("Password", userRepositoryAdapter.getUser(createdUser.getId()).getPassword());
        assertDoesNotThrow(() -> userRepositoryAdapter.changePassword(createdUser.getId(), "newPassword"));
        assertEquals("newPassword", userRepositoryAdapter.getUser(createdUser.getId()).getPassword());
    }

    @Test
    void changePasswordNegativeTest() {
        assertThrows(RepositoryException.class, () -> userRepositoryAdapter.changePassword(validUser.getId(), "newPassword"));
    }

    @Test
    void deleteUserPositiveTest() throws RepositoryException {
        User createdUser = userRepositoryAdapter.createUser(validUser);
        assertNotNull(userRepositoryAdapter.getUser(createdUser.getId()));
        User createdUser2 = userRepositoryAdapter.deleteUser(createdUser.getId());
        assertTrue(userRepositoryAdapter.getUser(createdUser2.getId()).getArchive());
    }

    @Test
    void deleteUserNegativeTest() {
        assertThrows(RepositoryException.class, () -> userRepositoryAdapter.deleteUser(validUser.getId()));
    }

    @Test
    void getAllUsersFilterTest() throws RepositoryException {
        assertEquals(0, userRepositoryAdapter.getAllUsersFilter(validUser.getId().toString()).size());
        validUser.setUsername("Username7");
        User createdUser = userRepositoryAdapter.createUser(validUser);
        userRepositoryAdapter.createUser(validUser2);
        assertEquals(1, userRepositoryAdapter.getAllUsersFilter(createdUser.getId().toString()).size());
    }

    @Test
    void getAllUsersTest() throws RepositoryException {
        assertEquals(0, userRepositoryAdapter.getAllUsers().size());
        userRepositoryAdapter.createUser(validUser);
        assertEquals(1, userRepositoryAdapter.getAllUsers().size());
        userRepositoryAdapter.createUser(validUser2);
        assertEquals(2, userRepositoryAdapter.getAllUsers().size());
    }

    @Test
    void getUserByUsernamePositiveTest() throws RepositoryException {
        User createdUser = userRepositoryAdapter.createUser(validUser);
        assertEquals(createdUser.getId(), userRepositoryAdapter.getUserByUsername(validUser.getUsername()).getId());
    }

    @Test
    void getUserByUsernameNegativeTest() {
        assertThrows(RepositoryException.class, () -> userRepositoryAdapter.getUserByUsername(validUser.getUsername()));
    }

    @Test
    void getUserListTest() throws RepositoryException {
        assertEquals(0, userRepositoryAdapter.getUserList(false).size());
        assertEquals(0, userRepositoryAdapter.getUserList(true).size());
        userRepositoryAdapter.createUser(validUser);
        assertEquals(1, userRepositoryAdapter.getUserList(false).size());
        assertEquals(0, userRepositoryAdapter.getUserList(true).size());
        userRepositoryAdapter.createUser(validUser2);
        assertEquals(1, userRepositoryAdapter.getUserList(false).size());
        assertEquals(1, userRepositoryAdapter.getUserList(true).size());
    }

    @Test
    void getUsersByUsernameTest() throws RepositoryException {
        assertEquals(0, userRepositoryAdapter.getUsersByUsername("Username14").size());
        userRepositoryAdapter.createUser(validUser);
        assertEquals(1, userRepositoryAdapter.getUsersByUsername("Username14").size());
    }

    @Test
    void restoreUserPositiveTest() throws RepositoryException {
        User createdUser = userRepositoryAdapter.createUser(validUser);
        userRepositoryAdapter.deleteUser(createdUser.getId());
        assertTrue(userRepositoryAdapter.getUser(createdUser.getId()).getArchive());
        userRepositoryAdapter.restoreUser(createdUser.getId());
        assertFalse(userRepositoryAdapter.getUser(createdUser.getId()).getArchive());
    }

    @Test
    void restoreUserNegativeTest() throws RepositoryException {
        assertThrows(RepositoryException.class, () -> userRepositoryAdapter.restoreUser(validUser.getId()));
        User createdUser = userRepositoryAdapter.createUser(validUser);
        assertThrows(RepositoryException.class, () -> userRepositoryAdapter.restoreUser(createdUser.getId()));
    }

    @Test
    void updateUserPositiveTest() throws RepositoryException {
        User createdUser = userRepositoryAdapter.createUser(validUser);
        User updatedUser = userRepositoryAdapter.updateUser(createdUser.getId(), validUser2);
        assertEquals(createdUser.getId(), createdUser.getId());
        assertEquals(createdUser.getPassword(), updatedUser.getPassword());
        assertEquals(validUser2.getUsername(), updatedUser.getUsername());
        assertEquals(validUser2.getArchive(), updatedUser.getArchive());
        assertEquals(validUser2.getFirstName(), updatedUser.getFirstName());
        assertEquals(validUser2.getLastName(), updatedUser.getLastName());
        assertEquals(validUser2.getPhoneNumber(), updatedUser.getPhoneNumber());
        assertEquals(validUser2.getAddress(), updatedUser.getAddress());
        assertEquals(validUser2.getUserAccessType(), updatedUser.getUserAccessType());
    }
}
