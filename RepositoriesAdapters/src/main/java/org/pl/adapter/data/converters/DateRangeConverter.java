package org.pl.adapter.data.converters;

import jakarta.enterprise.context.ApplicationScoped;
import org.pl.adapter.data.model.ClientEnt;
import org.pl.adapter.data.model.DateRangeEnt;
import org.pl.adapter.data.model.HardwareEnt;
import org.pl.adapter.data.model.RepairEnt;
import org.pl.model.DateRange;
import org.pl.model.Repair;

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
