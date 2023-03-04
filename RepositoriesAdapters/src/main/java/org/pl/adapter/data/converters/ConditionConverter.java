package org.pl.adapter.data.converters;

import org.pl.adapter.data.model.ConditionEnt;
import org.pl.model.Condition;

public class ConditionConverter {
    public ConditionEnt convert(Condition condition) {
        return switch (condition) {
            case UNREPAIRABLE -> ConditionEnt.UNREPAIRABLE;
            case VERY_BAD -> ConditionEnt.VERY_BAD;
            case BAD -> ConditionEnt.BAD;
            case AVERAGE -> ConditionEnt.AVERAGE;
            case DUSTY -> ConditionEnt.DUSTY;
            case FINE -> ConditionEnt.FINE;
        };
    }

    public Condition convert(ConditionEnt conditionEnt) {
        return switch (conditionEnt) {
            case UNREPAIRABLE -> Condition.UNREPAIRABLE;
            case VERY_BAD -> Condition.VERY_BAD;
            case BAD -> Condition.BAD;
            case AVERAGE -> Condition.AVERAGE;
            case DUSTY -> Condition.DUSTY;
            case FINE -> Condition.FINE;
        };
    }
}
