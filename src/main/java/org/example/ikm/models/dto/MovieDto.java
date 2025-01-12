package org.example.ikm.models.dto;

import lombok.Value;
import org.example.ikm.models.entities.Movie;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO for {@link Movie}
 */
@Value
public class MovieDto {
    Integer id;
    String title;
    LocalDate releaseDate;
    LocalTime duration;
}