package org.example.ikm.models.dto;

import lombok.Value;
import org.example.ikm.models.entities.MovieAuthorId;

/**
 * DTO for {@link MovieAuthorId}
 */
@Value
public class MovieAuthorIdDto {
    Integer movieId;
    Integer authorId;
}