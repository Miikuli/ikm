package org.example.ikm.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BioValidator.class)
public @interface ValidBio {
    String message() default "Поле bio должно быть 'М' или 'Ж'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}