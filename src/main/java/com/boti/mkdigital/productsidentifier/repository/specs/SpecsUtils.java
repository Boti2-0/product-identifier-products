package com.boti.mkdigital.productsidentifier.repository.specs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import java.util.List;


public class SpecsUtils {

    private SpecsUtils() {}

    public static String iLikeFormat(String value) {
        return "%" + (value == null ? "" : value.trim().toLowerCase()) + "%";
    }

    public static String startsWithFormat(String value) {
        return (value == null ? "" : value.trim().toLowerCase()) + "%";
    }

    public static Expression replace(CriteriaBuilder builder, Expression field, List<String> from, String to) {
        Expression expression = field;
        for (String charToReplace : from) {
            expression = replace(builder, expression, charToReplace, to);
        }
        return expression;
    }

    public static Expression replace(CriteriaBuilder builder, Expression field, String from, String to) {
        return builder.function("replace", String.class, field, builder.literal(from), builder.literal(to));
    }

    public static Expression onlyLastWord(CriteriaBuilder builder, Expression field) {
        return builder.function(
            "regexp_replace",
            String.class,
            field,
            builder.literal("'^.* '"),
            builder.literal("' '")
        );
    }
}
