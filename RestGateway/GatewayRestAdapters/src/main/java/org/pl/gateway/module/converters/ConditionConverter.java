package org.pl.gateway.module.converters;

import jakarta.enterprise.context.ApplicationScoped;
import org.pl.gateway.module.model.Condition;
import org.pl.gateway.module.model.ConditionRest;

@ApplicationScoped
public class ConditionConverter {
    public ConditionRest convert(Condition condition) {
        return switch (condition) {
            case UNREPAIRABLE -> ConditionRest.UNREPAIRABLE;
            case VERY_BAD -> ConditionRest.VERY_BAD;
            case BAD -> ConditionRest.BAD;
            case AVERAGE -> ConditionRest.AVERAGE;
            case DUSTY -> ConditionRest.DUSTY;
            case FINE -> ConditionRest.FINE;
        };
    }

    public Condition convert(ConditionRest conditionEnt) {
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
