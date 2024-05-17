package com.example.ucomandbackend.util;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.*;

/**
 * Проверяет, что хотя бы одно из указанных полей не null.
 * Класс обязан иметь геттеры указанных полей
 */
@Constraint(validatedBy = OneOf.Validator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface OneOf {

    String[] value();

    String message() default "Хотя бы одно из указанных полей не должно быть null";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Slf4j
    class Validator implements ConstraintValidator<OneOf, Object> {

        private String[] getters;

        @Override
        public boolean isValid(Object value, ConstraintValidatorContext context) {
            if (value == null) {
                return false;
            }

            try {
                for (String getter : getters) {
                    Object field = value.getClass().getMethod(getter).invoke(value);
                    if (field != null) {
                        return true;
                    }
                }
            } catch (Exception e) {
                log.error("Не удалось получить поле при проверке OneOf", e);
                return false;
            }

            return false;
        }

        @Override
        public void initialize(OneOf constraintAnnotation) {
            getters = new String[constraintAnnotation.value().length];
            for (int i = 0; i < constraintAnnotation.value().length; i++) {
                String field = constraintAnnotation.value()[i];
                getters[i] = "get" + Character.toUpperCase(field.charAt(0)) + field.substring(1);
            }
        }
    }
}
