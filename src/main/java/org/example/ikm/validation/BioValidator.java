package org.example.ikm.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BioValidator implements ConstraintValidator<ValidBio, Character> {

    @Override
    public void initialize(ValidBio constraintAnnotation) {
    }

    @Override
    public boolean isValid(Character bio, ConstraintValidatorContext context) {
        if (bio == null) {
            return false;
        }
        return bio == 'М' || bio == 'Ж';
    }
}