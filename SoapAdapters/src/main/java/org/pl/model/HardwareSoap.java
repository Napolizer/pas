package org.pl.model;

import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.pl.adapters.HardwareTypeSoapAdapter;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import static org.pl.model.ConditionSoap.FINE;

@XmlRootElement(name = "hardware")
@XmlAccessorType(XmlAccessType.FIELD)
public class HardwareSoap implements EntitySoap, Serializable {
    @XmlAttribute
    private UUID id;
    @NotNull
    @XmlElement(required = true)
    private Boolean archive;
    @NotNull
    @XmlElement(required = true)
    private Integer price;
    @NotNull
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(HardwareTypeSoapAdapter.class)
    private HardwareTypeSoap hardwareType;

    public HardwareSoap() {
    }

    public HardwareSoap(UUID id, Boolean archive, Integer price, HardwareTypeSoap hardwareType) {
        this.id = id;
        this.archive = archive;
        this.price = price;
        this.hardwareType = hardwareType;
    }

    public void repair() {
        hardwareType.setCondition(FINE);
    }

    @Override
    public void setArchive(Boolean archive) {
        this.archive = archive;
    }


    @Override
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public Boolean getArchive() {
        return archive;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public HardwareTypeSoap getHardwareType() {
        return hardwareType;
    }

    public void setHardwareType(HardwareTypeSoap hardwareType) {
        this.hardwareType = hardwareType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HardwareSoap hardware = (HardwareSoap) o;

        return Objects.equals(id, hardware.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("archive", archive)
                .append("price", price)
                .append("hardwareType", hardwareType)
                .toString();
    }
}
