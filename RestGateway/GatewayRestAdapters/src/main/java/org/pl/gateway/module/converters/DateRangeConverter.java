package org.pl.gateway.module.converters;

import jakarta.enterprise.context.ApplicationScoped;
import org.pl.gateway.module.model.DateRangeRest;

@ApplicationScoped
public class DateRangeConverter {
    public DateRangeRest convert(DateRange dateRange) {
        if (dateRange == null) return null;
        try {
            return new DateRangeRest(dateRange.getStartDate(), dateRange.getEndDate());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public DateRange convert(DateRangeRest dateRangeEnt) {
        if (dateRangeEnt == null) return null;
        try {
            return new DateRange(dateRangeEnt.getStartDate(), dateRangeEnt.getEndDate());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
