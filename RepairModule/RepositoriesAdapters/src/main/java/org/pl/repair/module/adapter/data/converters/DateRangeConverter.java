package org.pl.repair.module.adapter.data.converters;

import jakarta.enterprise.context.ApplicationScoped;
import org.pl.repair.module.adapter.data.model.DateRangeEnt;
import org.pl.repair.module.model.DateRange;

@ApplicationScoped
public class DateRangeConverter {
    public DateRangeEnt convert(DateRange dateRange) {
        if (dateRange == null) return null;
        try {
            DateRangeEnt dateRangeEnt = new DateRangeEnt(dateRange.getStartDate(), dateRange.getEndDate());
            return dateRangeEnt;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public DateRange convert(DateRangeEnt dateRangeEnt) {
        if (dateRangeEnt == null) return null;
        try {
            DateRange dateRange = new DateRange(dateRangeEnt.getStartDate(), dateRangeEnt.getEndDate());
            return dateRange;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
