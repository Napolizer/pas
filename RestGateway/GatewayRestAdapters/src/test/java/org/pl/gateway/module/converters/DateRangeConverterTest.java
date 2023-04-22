package org.pl.gateway.module.converters;

import org.junit.jupiter.api.Test;
import org.pl.gateway.module.converters.DateRangeConverter;
import org.pl.gateway.module.model.DateRangeRest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class DateRangeConverterTest {
    private final DateRangeConverter dateRangeConverter = new DateRangeConverter();

    @Test
    void convertDateRangeFromDomainToEntModelTest() {
        DateRange dateRange = new DateRange(new Date(), new Date());
        DateRangeRest dateRangeRest = dateRangeConverter.convert(dateRange);
        assertInstanceOf(DateRangeRest.class, dateRangeRest);
        assertEquals(dateRange.getStartDate(), dateRangeRest.getStartDate());
        assertEquals(dateRange.getEndDate(), dateRangeRest.getEndDate());
    }

    @Test
    void convertDateRangeFromEntToDomainModelTest() {
        DateRangeRest dateRangeRest = new DateRangeRest(new Date(), new Date());
        DateRange dateRange = dateRangeConverter.convert(dateRangeRest);
        assertInstanceOf(DateRange.class, dateRange);
        assertEquals(dateRangeRest.getStartDate(), dateRange.getStartDate());
        assertEquals(dateRangeRest.getEndDate(), dateRange.getEndDate());
    }
}