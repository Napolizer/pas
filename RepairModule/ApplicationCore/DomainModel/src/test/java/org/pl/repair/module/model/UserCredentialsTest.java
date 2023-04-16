package org.pl.repair.module.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pl.repair.module.model.UserCredentials;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserCredentialsTest {
    ValidatorFactory validatorFactory;
    Validator validator;
    UserCredentials validUserCredentials;

    @BeforeEach
    void setup() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
        validUserCredentials = UserCredentials.builder()
                .username("Michal")
                .password("Bakus")
                .build();
    }

    @Test
    void fieldUsernamePositiveTest() {
        List<String> validUsernames = new ArrayList<>(List.of(
                "Frontas",
                "Fullstas",
                "ScrumMaster",
                " ",
                "   ",
                "      "
        ));
        validUsernames.add(null);
        for (var username : validUsernames) {
            validUserCredentials.setUsername(username);
            assertEquals(username, validUserCredentials.getUsername());
            assertTrue(validator.validate(validUserCredentials).isEmpty());
        }
    }

    @Test
    void fieldPasswordPositiveTest() {
        List<String> validPasswords = new ArrayList<>(List.of(
                "NiezlomneHaslo",
                "HelloHecker",
                "Haslo123",
                " ",
                "   ",
                "      "
        ));
        validPasswords.add(null);
        for (var password : validPasswords) {
            validUserCredentials.setPassword(password);
            assertEquals(password, validUserCredentials.getPassword());
            assertTrue(validator.validate(validUserCredentials).isEmpty());
        }
    }
}
