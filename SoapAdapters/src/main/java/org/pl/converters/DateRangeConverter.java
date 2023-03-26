package org.pl.converters;

import jakarta.enterprise.context.ApplicationScoped;
import org.pl.model.DateRange;
import org.pl.model.DateRangeSoap;

@ApplicationScoped
public class DateRangeConverter {
    public DateRangeSoap convert(DateRange dateRange) {
        if (dateRange == null) return null;
        try {
            return new DateRangeSoap(dateRange.getStartDate(), dateRange.getEndDate());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public DateRange convert(DateRangeSoap dateRangeEnt) {
        if (dateRangeEnt == null) return null;
        try {
            return new DateRange(dateRangeEnt.getStartDate(), dateRangeEnt.getEndDate());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
