package org.pl.adapter.data.converters;

import org.junit.jupiter.api.Test;
import org.pl.adapter.data.model.DateRangeEnt;
import org.pl.model.DateRange;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class DateRangeConverterIT {
    private final DateRangeConverter dateRangeConverter = new DateRangeConverter();

    @Test
    void convertDateRangeFromDomainToEntModelTest() {
        DateRange dateRange = new DateRange(new Date(), new Date());
        DateRangeEnt dateRangeEnt = dateRangeConverter.convert(dateRange);
        assertInstanceOf(DateRangeEnt.class, dateRangeEnt);
        assertEquals(dateRange.getStartDate(), dateRangeEnt.getStartDate());
        assertEquals(dateRange.getEndDate(), dateRangeEnt.getEndDate());
    }

    @Test
    void convertDateRangeFromEntToDomainModelTest() {
        DateRangeEnt dateRangeEnt = new DateRangeEnt(new Date(), new Date());
        DateRange dateRange = dateRangeConverter.convert(dateRangeEnt);
        assertInstanceOf(DateRange.class, dateRange);
        assertEquals(dateRangeEnt.getStartDate(), dateRange.getStartDate());
        assertEquals(dateRangeEnt.getEndDate(), dateRange.getEndDate());
    }
}
