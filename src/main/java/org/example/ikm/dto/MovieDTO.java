package org.example.ikm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.example.ikm.validation.ReleaseDateConstraint;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Getter
@Setter
public class MovieDTO {
    @NotBlank(message = "Название фильма не может быть пустым")
    @Size(max = 100, message = "Название фильма не может быть длиннее 100 символов")
    private String title;

    @ReleaseDateConstraint
    private LocalDate releaseDate;

    private LocalTime duration;

    private Set<Short> authorIds;
}