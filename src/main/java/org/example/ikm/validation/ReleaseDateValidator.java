package org.example.ikm.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class ReleaseDateValidator implements ConstraintValidator<ReleaseDateConstraint, LocalDate> {

    @Override
    public void initialize(ReleaseDateConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDate releaseDate, ConstraintValidatorContext context) {
        if (releaseDate == null) {
            return false;
        }
        return releaseDate.getYear() >= 1895;
    }
}