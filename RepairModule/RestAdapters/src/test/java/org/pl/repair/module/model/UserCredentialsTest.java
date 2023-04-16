package org.pl.repair.module.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.repair.module.model.UserRestCredentials;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserCredentialsTest {
    ValidatorFactory validatorFactory;
    Validator validator;
    UserRestCredentials validUserCredentials;

    @BeforeEach
    void setup() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
        validUserCredentials = UserRestCredentials.builder()
                .username("Michal")
                .password("Bakus")
                .build();
    }

    @Test
    void fieldUsernamePositiveTest() {
        List<String> validUsernames = List.of(
                "Frontas",
                "Fullstas",
                "ScrumMaster"
        );
        for (var username : validUsernames) {
            validUserCredentials.setUsername(username);
            assertEquals(username, validUserCredentials.getUsername());
            assertTrue(validator.validate(validUserCredentials).isEmpty());
        }
    }

    @Test
    void fieldUsernameNegativeTest() {
        List<String> invalidUsernames = new ArrayList<>(List.of(
                " ",
                "   ",
                "      ",
                "  ",
                ""
        ));
        invalidUsernames.add(null);
        for (var username : invalidUsernames) {
            validUserCredentials.setUsername(username);
            assertEquals(username, validUserCredentials.getUsername());
            assertFalse(validator.validate(validUserCredentials).isEmpty());
        }
    }

    @Test
    void fieldPasswordPositiveTest() {
        List<String> validPasswords = List.of(
                "NiezlomneHaslo",
                "HelloHecker",
                "Haslo123"
        );
        for (var password : validPasswords) {
            validUserCredentials.setPassword(password);
            assertEquals(password, validUserCredentials.getPassword());
            assertTrue(validator.validate(validUserCredentials).isEmpty());
        }
    }

    @Test
    void fieldPasswordNegativeTest() {
        List<String> invalidPasswords = new ArrayList<>(List.of(
                " ",
                "   ",
                "      ",
                "  ",
                ""
        ));
        invalidPasswords.add(null);
        for (var password : invalidPasswords) {
            validUserCredentials.setPassword(password);
            assertEquals(password, validUserCredentials.getPassword());
            assertFalse(validator.validate(validUserCredentials).isEmpty());
        }
    }
}
