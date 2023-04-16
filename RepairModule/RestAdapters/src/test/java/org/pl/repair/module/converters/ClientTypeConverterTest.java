package org.pl.repair.module.converters;

import org.junit.jupiter.api.Test;
import org.pl.repair.module.model.Basic;
import org.pl.repair.module.model.BasicRest;
import org.pl.repair.module.model.ClientType;
import org.pl.repair.module.model.ClientTypeRest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class ClientTypeConverterTest {
    private final ClientTypeConverter clientTypeConverter = new ClientTypeConverter();
    @Test
    void convertClientTypeFromDomainToEntModelTest() {
        ClientType basicClientType = new Basic();
        ClientTypeRest basicClientTypeRest = clientTypeConverter.convert(basicClientType);
        assertInstanceOf(BasicRest.class, basicClientTypeRest);
        assertEquals(basicClientType.getType(), basicClientTypeRest.getType());
        assertEquals(basicClientType.getFactor(), basicClientTypeRest.getFactor());
        assertEquals(basicClientType.getMaxRepairs(), basicClientTypeRest.getMaxRepairs());
    }

    @Test
    void convertClientTypeFromEntToDomainModelTest() {
        ClientTypeRest basicClientTypeRest = new BasicRest();
        ClientType basicClientType = clientTypeConverter.convert(basicClientTypeRest);
        assertInstanceOf(Basic.class, basicClientType);
        assertEquals(basicClientTypeRest.getType(), basicClientType.getType());
        assertEquals(basicClientTypeRest.getFactor(), basicClientType.getFactor());
        assertEquals(basicClientTypeRest.getMaxRepairs(), basicClientType.getMaxRepairs());
    }
}
