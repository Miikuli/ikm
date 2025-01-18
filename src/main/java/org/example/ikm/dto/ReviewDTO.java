package org.example.ikm.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
public class ReviewDTO {
    private UUID id;

    @NotNull(message = "Рейтинг не может быть пустым")
    @Min(value = 1, message = "Рейтинг должен быть не меньше 1")
    @Max(value = 10, message = "Рейтинг должен быть не больше 10")
    private Short rating;

    private String reviewText;

    private OffsetDateTime reviewDate;

    private Integer movieId; // ID фильма, к которому относится отзыв
}