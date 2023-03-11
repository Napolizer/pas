package org.pl.adapter.data.converters;

import org.junit.jupiter.api.Test;
import org.pl.adapter.data.model.BasicEnt;
import org.pl.adapter.data.model.ClientTypeEnt;
import org.pl.model.Basic;
import org.pl.model.ClientType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class ClientTypeConverterIT {
    private final ClientTypeConverter clientTypeConverter = new ClientTypeConverter();
    @Test
    void convertClientTypeFromDomainToEntModelTest() {
        ClientType basicClientType = new Basic();
        ClientTypeEnt basicClientTypeEnt = clientTypeConverter.convert(basicClientType);
        assertInstanceOf(BasicEnt.class, basicClientTypeEnt);
        assertEquals(basicClientType.getType(), basicClientTypeEnt.getType());
        assertEquals(basicClientType.getFactor(), basicClientTypeEnt.getFactor());
        assertEquals(basicClientType.getMaxRepairs(), basicClientTypeEnt.getMaxRepairs());
    }

    @Test
    void convertClientTypeFromEntToDomainModelTest() {
        ClientTypeEnt basicClientTypeEnt = new BasicEnt();
        ClientType basicClientType = clientTypeConverter.convert(basicClientTypeEnt);
        assertInstanceOf(Basic.class, basicClientType);
        assertEquals(basicClientTypeEnt.getType(), basicClientType.getType());
        assertEquals(basicClientTypeEnt.getFactor(), basicClientType.getFactor());
        assertEquals(basicClientTypeEnt.getMaxRepairs(), basicClientType.getMaxRepairs());
    }
}
