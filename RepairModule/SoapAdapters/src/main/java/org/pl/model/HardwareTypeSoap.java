package org.pl.model;

import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.pl.model.exceptions.HardwareSoapException;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@XmlRootElement(name = "hardwareType")
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class HardwareTypeSoap implements Serializable {
    @XmlAttribute
    protected UUID id = UUID.randomUUID();
    @NotNull
    @XmlElement(required = true)
    public ConditionSoap condition;
    @NotNull
    @XmlElement(required = true)
    protected String type;

    public HardwareTypeSoap() {
    }

    public HardwareTypeSoap(ConditionSoap condition) {
        setCondition(condition);
    }

    public abstract double calculateRepairCost(double price) throws HardwareSoapException;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ConditionSoap getCondition() {
        return condition;
    }

    public void setCondition(ConditionSoap condition) {
        this.condition = condition;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HardwareTypeSoap that = (HardwareTypeSoap) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("condition", condition)
                .append("type", type)
                .toString();
    }
}
