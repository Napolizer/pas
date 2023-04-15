package org.pl.services;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.pl.model.Address;
import org.pl.model.User;
import org.pl.model.UserAccessType;
import org.pl.model.exceptions.RepositoryException;
import org.pl.model.exceptions.UserException;

import java.io.File;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ArquillianExtension.class)
class UserServiceIT {
    @Inject
    private UserService userService;
    @Inject
    private UserTransaction userTransaction;
    @Inject
    private EntityManager entityManager;
    private Address validAddress;
    private User validUser;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "org.pl")
                .addPackages(true, "org.hamcrest")
                .addAsResource(new File("src/test/resources/META-INF"),"")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @BeforeEach
    void setUp() {
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
        try {
            userTransaction.begin();
            entityManager.clear();
            userTransaction.commit();
        } catch (Exception ignored) {

        }
    }

    @Test
    void addUserPositiveTest() throws UserException, RepositoryException {
        User createdUser = userService.add(validUser);
        assertEquals(validUser.getFirstName(), createdUser.getFirstName());
        assertEquals(validUser.getLastName(), createdUser.getLastName());
        assertEquals(validUser.getUsername(), createdUser.getUsername());
        assertEquals(validUser.getArchive(), createdUser.getArchive());
        assertEquals(validUser.getPassword(), createdUser.getPassword());
        assertEquals(validUser.getPhoneNumber(), createdUser.getPhoneNumber());
        assertEquals(validUser.getUserAccessType(), createdUser.getUserAccessType());
        assertEquals(validUser.getAddress().getCity(), createdUser.getAddress().getCity());
        assertEquals(validUser.getAddress().getNumber(), createdUser.getAddress().getNumber());
        assertEquals(validUser.getAddress().getStreet(), createdUser.getAddress().getStreet());
    }

    @Test
    void addUserNegativeTestFirstNameIsEmpty() {
        validUser.setFirstName("");
        assertThrows(UserException.class, () -> userService.add(validUser));
    }

    @Test
    void addUserNegativeTestLastNameIsEmpty() {
        validUser.setLastName("");
        assertThrows(UserException.class, () -> userService.add(validUser));
    }

    @Test
    void addUserNegativeTestPhoneNumberIsEmpty() {
        validUser.setPhoneNumber("");
        assertThrows(UserException.class, () -> userService.add(validUser));
    }

    @Test
    void addUserNegativeTestAddressIsEmpty() {
        validUser.setAddress(null);
        assertThrows(UserException.class, () -> userService.add(validUser));
    }

    @Test
    void getUserPositiveTest() throws UserException, RepositoryException {
        User createdUser = userService.add(validUser);
        assertEquals(validUser.getFirstName(), userService.get(createdUser.getId()).getFirstName());
        assertEquals(validUser.getLastName(), userService.get(createdUser.getId()).getLastName());
        assertEquals(validUser.getUsername(), userService.get(createdUser.getId()).getUsername());
        assertEquals(validUser.getArchive(), userService.get(createdUser.getId()).getArchive());
        assertEquals(validUser.getPassword(), userService.get(createdUser.getId()).getPassword());
        assertEquals(validUser.getPhoneNumber(), userService.get(createdUser.getId()).getPhoneNumber());
        assertEquals(validUser.getUserAccessType(), userService.get(createdUser.getId()).getUserAccessType());
        assertEquals(validUser.getAddress().getCity(), userService.get(createdUser.getId()).getAddress().getCity());
        assertEquals(validUser.getAddress().getNumber(), userService.get(createdUser.getId()).getAddress().getNumber());
        assertEquals(validUser.getAddress().getStreet(), userService.get(createdUser.getId()).getAddress().getStreet());
    }

    @Test
    void getUserNegativeTest() {
        assertThrows(RepositoryException.class, () -> userService.get(validUser.getId()));
    }

    @Test
    void getUserInfoTest() throws RepositoryException, UserException {
        User createdUser = userService.add(validUser);
        String clientInfo = createdUser.toString();
        assertEquals(clientInfo, userService.getInfo(createdUser.getId()));
    }

    @Test
    void isUserArchiveTest() throws RepositoryException, UserException {
        User createdUser = userService.add(validUser);
        Boolean isUserArchive = createdUser.getArchive();
        assertEquals(isUserArchive, userService.isUserArchive(createdUser.getId()));
    }

    @Test
    void archiveUserPositiveTest() throws RepositoryException, UserException {
        User createdUser = userService.add(validUser);
        assertFalse(userService.get(createdUser.getId()).getArchive());
        userService.archive(createdUser.getId());
        assertTrue(userService.get(createdUser.getId()).getArchive());
    }

    @Test
    void getAllUsersTest() throws RepositoryException, UserException {
        User validUser2 = User.builder()
                .id(UUID.randomUUID())
                .username("Username7")
                .password("Password")
                .archive(false)
                .firstName("Tester")
                .lastName("Testowy")
                .phoneNumber("123456789")
                .address(validAddress)
                .userAccessType(UserAccessType.EMPLOYEES)
                .build();
        userService.add(validUser);
        assertTrue(userService.getAllUsers().size() > 0);
        userService.add(validUser2);
        assertTrue(userService.getAllUsers().size() > 1);
    }

    @Test
    void getUserByUsernameTest() throws RepositoryException, UserException {
        User createdUser = userService.add(validUser);
        String userUsername = validUser.getUsername();
        assertEquals(createdUser, userService.getUserByUsername(userUsername));
    }

    @Test
    void getPresentAndArchiveSizeTest() throws RepositoryException, UserException {
        assertEquals(0, userService.getPresentSize());
        assertEquals(0, userService.getArchiveSize());
        User createdUser = userService.add(validUser);
        assertEquals(1, userService.getPresentSize());
        assertEquals(0, userService.getArchiveSize());
        userService.archive(createdUser.getId());
        assertEquals(0, userService.getPresentSize());
        assertEquals(1, userService.getArchiveSize());

    }

    @Test
    void getAllClientsFilterTest() throws RepositoryException, UserException {
        User validUser2 = User.builder()
                .id(UUID.randomUUID())
                .username("Username7")
                .password("Password")
                .archive(false)
                .firstName("Tester")
                .lastName("Testowy")
                .phoneNumber("123456789")
                .address(validAddress)
                .userAccessType(UserAccessType.EMPLOYEES)
                .build();
        assertEquals(0, userService.getAllUsersFilter("123454").size());
        User createdUser = userService.add(validUser);
        User createdUser2 = userService.add(validUser2);
        assertEquals(2, userService.getAllUsersFilter("").size());
        assertEquals(1, userService.getAllUsersFilter(createdUser.getId().toString()).size());
    }

    @Test
    void getClientsByUsernameTest() throws RepositoryException, UserException {
        User validUser2 = User.builder()
                .id(UUID.randomUUID())
                .username("Username7")
                .password("Password")
                .archive(false)
                .firstName("Tester")
                .lastName("Testowy")
                .phoneNumber("123456789")
                .address(validAddress)
                .userAccessType(UserAccessType.EMPLOYEES)
                .build();
        userService.add(validUser);
        userService.add(validUser2);
        assertEquals(2, userService.getUsersByUsername("").size());
        assertEquals(2, userService.getUsersByUsername("User").size());
        assertEquals(1, userService.getUsersByUsername("Username7").size());
        assertEquals(0, userService.getUsersByUsername("Usernameeee").size());
    }

    @Test
    void updateUserPositiveTest() throws RepositoryException, UserException {
        User createdUser = userService.add(validUser);
        createdUser.setUsername("newUsername");
        createdUser.setPhoneNumber("54321");
        userService.updateUser(createdUser.getId(), createdUser);
        assertEquals("newUsername", userService.get(createdUser.getId()).getUsername());
        assertEquals("54321", userService.get(createdUser.getId()).getPhoneNumber());
        assertEquals(validUser.getPassword(), userService.get(createdUser.getId()).getPassword());
    }

    @Test
    void updateUserNegativeTest() {
        assertThrows(RepositoryException.class, () -> userService.updateUser(validUser.getId(), validUser));
    }

    @Test
    void updatePasswordPositiveTest() throws RepositoryException, UserException {
        User createdClient = userService.add(validUser);
        assertEquals("Password", userService.get(createdClient.getId()).getPassword());
        userService.updatePassword(createdClient.getId(), "newPassword");
        assertEquals("newPassword", userService.get(createdClient.getId()).getPassword());
    }

    @Test
    void updatePasswordNegativeTest() {
        assertThrows(RepositoryException.class, () -> userService.updatePassword(validUser.getId(), "newPassword"));
    }

    @Test
    void dearchivePositiveTest() throws RepositoryException, UserException {
        validUser.setArchive(true);
        User createdClient = userService.add(validUser);
        assertTrue(userService.get(createdClient.getId()).getArchive());
        userService.dearchive(createdClient.getId());
        assertFalse(userService.get(createdClient.getId()).getArchive());
    }

    @Test
    void dearchiveNegativeTest() throws RepositoryException, UserException {
        User createdClient = userService.add(validUser);
        assertFalse(userService.get(createdClient.getId()).getArchive());
        assertThrows(RepositoryException.class, () -> userService.dearchive(createdClient.getId()));
    }
}