package org.pl.repair.module.model.exceptions.authentication;

import jakarta.xml.bind.annotation.XmlTransient;

@XmlTransient
public class UserNotFoundSoapException extends Exception {
    public UserNotFoundSoapException() {
        super("Given user does not exist");
    }
}
