package org.pl.model;

import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.pl.annotations.ValidDateRangeSoap;
import org.pl.model.exceptions.HardwareSoapException;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@XmlRootElement(name = "repair")
@XmlAccessorType(XmlAccessType.FIELD)
public class RepairSoap implements EntitySoap, Serializable {
    @XmlAttribute
    private UUID id;
    @NotNull
    @XmlElement(required = true)
    private Boolean archive;
    @NotNull
    @XmlElement(required = true)
    private ClientSoap client;
    @NotNull
    @XmlElement(required = true)
    private HardwareSoap hardware;
    @NotNull
    @ValidDateRangeSoap
    @XmlElement(required = true)
    private DateRangeSoap dateRange;

    public RepairSoap() {
    }

    public RepairSoap(UUID id, Boolean archive, ClientSoap client, HardwareSoap hardware, DateRangeSoap dateRange) {
        this.id = id;
        this.archive = archive;
        this.client = client;
        this.hardware = hardware;
        this.dateRange = dateRange;
    }

    public double calculateRepairCost() throws HardwareSoapException {
        return getHardware().getHardwareType().calculateRepairCost(getHardware().getPrice());
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

    public ClientSoap getClient() {
        return client;
    }

    public void setClient(ClientSoap client) {
        this.client = client;
    }

    public HardwareSoap getHardware() {
        return hardware;
    }

    public void setHardware(HardwareSoap hardware) {
        this.hardware = hardware;
    }

    public DateRangeSoap getDateRange() {
        return dateRange;
    }

    public void setDateRange(DateRangeSoap dateRange) {
        this.dateRange = dateRange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RepairSoap repair = (RepairSoap) o;

        return Objects.equals(id, repair.id);
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
                .append("client", client)
                .append("hardware", hardware)
                .append("dateRange", dateRange)
                .toString();
    }
}
