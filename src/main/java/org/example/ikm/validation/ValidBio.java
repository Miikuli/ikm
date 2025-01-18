package org.example.ikm.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD }) // Аннотация применяется к полям
@Retention(RetentionPolicy.RUNTIME) // Аннотация доступна в runtime
@Constraint(validatedBy = BioValidator.class) // Указываем валидатор
public @interface ValidBio {
    String message() default "Поле bio должно быть 'М' или 'Ж'"; // Сообщение об ошибке

    Class<?>[] groups() default {}; // Группы валидации (по умолчанию пусто)

    Class<? extends Payload>[] payload() default {}; // Полезная нагрузка (по умолчанию пусто)
}