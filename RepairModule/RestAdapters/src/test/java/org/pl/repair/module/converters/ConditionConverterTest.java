package org.pl.repair.module.converters;

import org.junit.jupiter.api.Test;
import org.pl.repair.module.model.Condition;
import org.pl.repair.module.model.ConditionRest;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class ConditionConverterTest {
    private final ConditionConverter conditionConverter = new ConditionConverter();
    @Test
    void convertConditionFromDomainToEntModelTest() {
        ConditionRest unreperiableConditionRest = conditionConverter.convert(Condition.UNREPAIRABLE);
        ConditionRest veryBadConditionRest = conditionConverter.convert(Condition.VERY_BAD);
        ConditionRest badConditionRest = conditionConverter.convert(Condition.BAD);
        ConditionRest averageConditionRest = conditionConverter.convert(Condition.AVERAGE);
        ConditionRest dustyConditionRest = conditionConverter.convert(Condition.DUSTY);
        ConditionRest fineConditionRest = conditionConverter.convert(Condition.FINE);
        assertInstanceOf(ConditionRest.UNREPAIRABLE.getClass(), unreperiableConditionRest);
        assertInstanceOf(ConditionRest.VERY_BAD.getClass(), veryBadConditionRest);
        assertInstanceOf(ConditionRest.BAD.getClass(), badConditionRest);
        assertInstanceOf(ConditionRest.AVERAGE.getClass(), averageConditionRest);
        assertInstanceOf(ConditionRest.DUSTY.getClass(), dustyConditionRest);
        assertInstanceOf(ConditionRest.FINE.getClass(), fineConditionRest);
    }

    @Test
    void convertConditionFromEntToDomainModelTest() {
        Condition unreperiableCondition = conditionConverter.convert(ConditionRest.UNREPAIRABLE);
        Condition veryBadCondition = conditionConverter.convert(ConditionRest.VERY_BAD);
        Condition badCondition = conditionConverter.convert(ConditionRest.BAD);
        Condition averageCondition = conditionConverter.convert(ConditionRest.AVERAGE);
        Condition dustyCondition = conditionConverter.convert(ConditionRest.DUSTY);
        Condition fineCondition = conditionConverter.convert(ConditionRest.FINE);
        assertInstanceOf(Condition.UNREPAIRABLE.getClass(), unreperiableCondition);
        assertInstanceOf(Condition.VERY_BAD.getClass(), veryBadCondition);
        assertInstanceOf(Condition.BAD.getClass(), badCondition);
        assertInstanceOf(Condition.AVERAGE.getClass(), averageCondition);
        assertInstanceOf(Condition.DUSTY.getClass(), dustyCondition);
        assertInstanceOf(Condition.FINE.getClass(), fineCondition);
    }
}
