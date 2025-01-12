package org.example.ikm.models.dto;

import lombok.Value;
import org.example.ikm.models.entities.Author;

import java.time.LocalDate;

/**
 * DTO for {@link Author}
 */
@Value
public class AuthorDto {
    Short id;
    String name;
    LocalDate birthDate;
    String bio;
}