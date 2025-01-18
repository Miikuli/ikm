package org.example.ikm.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ReleaseDateValidator.class)
public @interface ReleaseDateConstraint {
    String message() default "Дата выпуска фильма должна быть не раньше 1895 года";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}