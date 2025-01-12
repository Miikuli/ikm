package org.example.ikm.models.dto;

import lombok.Value;
import org.example.ikm.models.entities.Review;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * DTO for {@link Review}
 */
@Value
public class ReviewDto {
    UUID id;
    MovieDto movie;
    AuthorDto author;
    Short rating;
    String reviewText;
    OffsetDateTime reviewDate;
}