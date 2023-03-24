package org.pl.converters;

import jakarta.enterprise.context.ApplicationScoped;
import org.pl.model.Condition;
import org.pl.model.ConditionSoap;

@ApplicationScoped
public class ConditionConverter {
    public ConditionSoap convert(Condition condition) {
        return switch (condition) {
            case UNREPAIRABLE -> ConditionSoap.UNREPAIRABLE;
            case VERY_BAD -> ConditionSoap.VERY_BAD;
            case BAD -> ConditionSoap.BAD;
            case AVERAGE -> ConditionSoap.AVERAGE;
            case DUSTY -> ConditionSoap.DUSTY;
            case FINE -> ConditionSoap.FINE;
        };
    }

    public Condition convert(ConditionSoap conditionEnt) {
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
