package org.example.ikm.entities;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MovieAuthorId implements Serializable {
    private Integer movieId;
    private Integer authorId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieAuthorId that = (MovieAuthorId) o;
        return Objects.equals(movieId, that.movieId) && Objects.equals(authorId, that.authorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId, authorId);
    }
}