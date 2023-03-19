package org.pl.model.exceptions.authentication;

import jakarta.xml.bind.annotation.XmlTransient;

@XmlTransient
public class UserIsArchiveSoapException extends Exception {
    public UserIsArchiveSoapException() {
        super("Cannot authenticate archived user");
    }
}
