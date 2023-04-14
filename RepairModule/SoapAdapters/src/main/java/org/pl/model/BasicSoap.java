package org.pl.model;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@XmlRootElement(name = "basic")
public class BasicSoap extends ClientTypeSoap implements Serializable {
    public BasicSoap() {
        setFactor(1.0);
        setMaxRepairs(2);
        setType("BASIC");
    }
}
