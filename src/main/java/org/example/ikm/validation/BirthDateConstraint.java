package org.example.ikm.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BirthDateValidator.class)
public @interface BirthDateConstraint {
    String message() default "Дата рождения должна быть не позже 2005 года";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}