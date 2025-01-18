package org.example.ikm.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class BirthDateValidator implements ConstraintValidator<BirthDateConstraint, LocalDate> {

    @Override
    public void initialize(BirthDateConstraint constraintAnnotation) {
        // Инициализация (если нужно)
    }

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext context) {
        if (birthDate == null) {
            return false; // Поле не может быть null
        }
        return birthDate.getYear() <= 2005; // Проверяем, что год рождения <= 2005
    }
}