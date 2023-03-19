package org.pl.model;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@XmlRootElement(name = "vip")
public class VipSoap extends ClientTypeSoap implements Serializable {
    public VipSoap() {
        setFactor(0.8);
        setMaxRepairs(10);
        setType("VIP");
    }
}
