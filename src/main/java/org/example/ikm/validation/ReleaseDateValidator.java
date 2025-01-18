package org.example.ikm.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class ReleaseDateValidator implements ConstraintValidator<ReleaseDateConstraint, LocalDate> {

    @Override
    public void initialize(ReleaseDateConstraint constraintAnnotation) {
        // Инициализация (если нужно)
    }

    @Override
    public boolean isValid(LocalDate releaseDate, ConstraintValidatorContext context) {
        if (releaseDate == null) {
            return false; // Поле не может быть null
        }
        return releaseDate.getYear() >= 1895; // Проверяем, что год выпуска >= 1895
    }
}