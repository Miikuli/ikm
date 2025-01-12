package org.example.ikm.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class MovieAuthorId implements Serializable {
    private static final long serialVersionUID = 2177323530910358115L;
    @Column(name = "movie_id", nullable = false)
    private Integer movieId;

    @Column(name = "author_id", nullable = false)
    private Integer authorId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MovieAuthorId entity = (MovieAuthorId) o;
        return Objects.equals(this.movieId, entity.movieId) &&
                Objects.equals(this.authorId, entity.authorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId, authorId);
    }

}