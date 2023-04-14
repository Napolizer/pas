package org.pl.model.exceptions.authentication;

import jakarta.xml.bind.annotation.XmlTransient;

@XmlTransient
public class InvalidCredentialsSoapException extends Exception {
    public InvalidCredentialsSoapException() {
        super("Provided credentials are invalid");
    }
}