package org.pl.model;

import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;


@XmlRootElement(name = "dateRange")
@XmlAccessorType(XmlAccessType.FIELD)
public class DateRangeSoap implements Serializable {
    @NotNull
    @XmlElement(required = true)
    private Date startDate;

    @NotNull
    @XmlElement(required = true)
    private Date endDate;

    public DateRangeSoap() {
    }

    public DateRangeSoap(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        DateRangeSoap that = (DateRangeSoap) o;

        return new EqualsBuilder().append(startDate, that.startDate).append(endDate, that.endDate).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(startDate).append(endDate).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("startDate", startDate)
                .append("endDate", endDate)
                .toString();
    }
}
