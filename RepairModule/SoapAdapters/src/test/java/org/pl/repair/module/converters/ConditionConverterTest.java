package org.pl.repair.module.converters;

import org.junit.jupiter.api.Test;
import org.pl.repair.module.model.Condition;
import org.pl.repair.module.model.ConditionSoap;

import static org.junit.jupiter.api.Assertions.*;

public class ConditionConverterTest {
    private final ConditionConverter conditionConverter = new ConditionConverter();
    @Test
    void convertConditionFromDomainToSoapModelTest() {
        ConditionSoap unreperiableConditionSoap = conditionConverter.convert(Condition.UNREPAIRABLE);
        ConditionSoap veryBadConditionSoap = conditionConverter.convert(Condition.VERY_BAD);
        ConditionSoap badConditionSoap = conditionConverter.convert(Condition.BAD);
        ConditionSoap averageConditionSoap = conditionConverter.convert(Condition.AVERAGE);
        ConditionSoap dustyConditionSoap = conditionConverter.convert(Condition.DUSTY);
        ConditionSoap fineConditionSoap = conditionConverter.convert(Condition.FINE);
        assertInstanceOf(ConditionSoap.UNREPAIRABLE.getClass(), unreperiableConditionSoap);
        assertInstanceOf(ConditionSoap.VERY_BAD.getClass(), veryBadConditionSoap);
        assertInstanceOf(ConditionSoap.BAD.getClass(), badConditionSoap);
        assertInstanceOf(ConditionSoap.AVERAGE.getClass(), averageConditionSoap);
        assertInstanceOf(ConditionSoap.DUSTY.getClass(), dustyConditionSoap);
        assertInstanceOf(ConditionSoap.FINE.getClass(), fineConditionSoap);
    }

    @Test
    void convertConditionFromSoapToDomainModelTest() {
        Condition unreperiableCondition = conditionConverter.convert(ConditionSoap.UNREPAIRABLE);
        Condition veryBadCondition = conditionConverter.convert(ConditionSoap.VERY_BAD);
        Condition badCondition = conditionConverter.convert(ConditionSoap.BAD);
        Condition averageCondition = conditionConverter.convert(ConditionSoap.AVERAGE);
        Condition dustyCondition = conditionConverter.convert(ConditionSoap.DUSTY);
        Condition fineCondition = conditionConverter.convert(ConditionSoap.FINE);
        assertInstanceOf(Condition.UNREPAIRABLE.getClass(), unreperiableCondition);
        assertInstanceOf(Condition.VERY_BAD.getClass(), veryBadCondition);
        assertInstanceOf(Condition.BAD.getClass(), badCondition);
        assertInstanceOf(Condition.AVERAGE.getClass(), averageCondition);
        assertInstanceOf(Condition.DUSTY.getClass(), dustyCondition);
        assertInstanceOf(Condition.FINE.getClass(), fineCondition);
    }
}
