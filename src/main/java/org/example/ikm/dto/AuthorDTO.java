package org.example.ikm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.example.ikm.validation.BirthDateConstraint;
import org.example.ikm.validation.ValidBio;

import java.time.LocalDate;

@Getter
@Setter
public class AuthorDTO {
    @NotBlank(message = "Имя автора не может быть пустым")
    @Size(max = 100, message = "Имя автора не может быть длиннее 100 символов")
    private String name;

    @BirthDateConstraint
    private LocalDate birthDate;

    @ValidBio
    private char bio;
}