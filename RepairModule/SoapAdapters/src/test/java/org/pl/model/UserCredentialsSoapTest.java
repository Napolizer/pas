package org.pl.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserCredentialsSoapTest {
    ValidatorFactory validatorFactory;
    Validator validator;
    UserSoapCredentials validUserCredentials;

    @BeforeEach
    void setup() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
        validUserCredentials = new UserSoapCredentials("Michal", "Bakus");
    }

    @Test
    void fieldUsernamePositiveTest() {
        List<String> validUsernames = new ArrayList<>(List.of(
                "Frontas",
                "Fullstas",
                "ScrumMaster"
        ));
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
                "Haslo123"
        ));
        for (var password : validPasswords) {
            validUserCredentials.setPassword(password);
            assertEquals(password, validUserCredentials.getPassword());
            assertTrue(validator.validate(validUserCredentials).isEmpty());
        }
    }
}
