package org.example.ikm.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class BirthDateValidator implements ConstraintValidator<BirthDateConstraint, LocalDate> {

    @Override
    public void initialize(BirthDateConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext context) {
        if (birthDate == null) {
            return false;
        }
        return birthDate.getYear() <= 2005;
    }
}