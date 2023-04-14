package org.pl.model;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@XmlRootElement(name = "premium")
public class PremiumSoap extends ClientTypeSoap implements Serializable {
    public PremiumSoap() {
        setFactor(0.9);
        setMaxRepairs(5);
        setType("PREMIUM");
    }
}
