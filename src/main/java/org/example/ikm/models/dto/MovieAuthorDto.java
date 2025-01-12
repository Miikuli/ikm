package org.example.ikm.models.dto;

import lombok.Value;
import org.example.ikm.models.entities.MovieAuthor;

/**
 * DTO for {@link MovieAuthor}
 */
@Value
public class MovieAuthorDto {
    MovieAuthorIdDto id;
    AuthorDto author;
}