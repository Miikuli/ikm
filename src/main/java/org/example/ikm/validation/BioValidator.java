package org.example.ikm.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BioValidator implements ConstraintValidator<ValidBio, Character> {

    @Override
    public void initialize(ValidBio constraintAnnotation) {
        // Инициализация (если нужно)
    }

    @Override
    public boolean isValid(Character bio, ConstraintValidatorContext context) {
        if (bio == null) {
            return false; // Поле не может быть null
        }
        return bio == 'М' || bio == 'Ж'; // Проверяем, что bio равно 'М' или 'Ж'
    }
}