package org.pl.adapter.data.converters;

import org.junit.jupiter.api.Test;
import org.pl.adapter.data.model.ConditionEnt;
import org.pl.model.Condition;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class ConditionConverterIT {
    private final ConditionConverter conditionConverter = new ConditionConverter();
    @Test
    void convertConditionFromDomainToEntModelTest() {
        ConditionEnt unreperiableConditionEnt = conditionConverter.convert(Condition.UNREPAIRABLE);
        ConditionEnt veryBadConditionEnt = conditionConverter.convert(Condition.VERY_BAD);
        ConditionEnt badConditionEnt = conditionConverter.convert(Condition.BAD);
        ConditionEnt averageConditionEnt = conditionConverter.convert(Condition.AVERAGE);
        ConditionEnt dustyConditionEnt = conditionConverter.convert(Condition.DUSTY);
        ConditionEnt fineConditionEnt = conditionConverter.convert(Condition.FINE);
        assertInstanceOf(ConditionEnt.UNREPAIRABLE.getClass(), unreperiableConditionEnt);
        assertInstanceOf(ConditionEnt.VERY_BAD.getClass(), veryBadConditionEnt);
        assertInstanceOf(ConditionEnt.BAD.getClass(), badConditionEnt);
        assertInstanceOf(ConditionEnt.AVERAGE.getClass(), averageConditionEnt);
        assertInstanceOf(ConditionEnt.DUSTY.getClass(), dustyConditionEnt);
        assertInstanceOf(ConditionEnt.FINE.getClass(), fineConditionEnt);
    }

    @Test
    void convertConditionFromEntToDomainModelTest() {
        Condition unreperiableCondition = conditionConverter.convert(ConditionEnt.UNREPAIRABLE);
        Condition veryBadCondition = conditionConverter.convert(ConditionEnt.VERY_BAD);
        Condition badCondition = conditionConverter.convert(ConditionEnt.BAD);
        Condition averageCondition = conditionConverter.convert(ConditionEnt.AVERAGE);
        Condition dustyCondition = conditionConverter.convert(ConditionEnt.DUSTY);
        Condition fineCondition = conditionConverter.convert(ConditionEnt.FINE);
        assertInstanceOf(Condition.UNREPAIRABLE.getClass(), unreperiableCondition);
        assertInstanceOf(Condition.VERY_BAD.getClass(), veryBadCondition);
        assertInstanceOf(Condition.BAD.getClass(), badCondition);
        assertInstanceOf(Condition.AVERAGE.getClass(), averageCondition);
        assertInstanceOf(Condition.DUSTY.getClass(), dustyCondition);
        assertInstanceOf(Condition.FINE.getClass(), fineCondition);
    }
}
