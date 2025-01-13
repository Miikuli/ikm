package org.example.ikm.repositories;

import org.example.ikm.entities.MovieAuthor;
import org.example.ikm.entities.MovieAuthorId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieAuthorRepository extends JpaRepository<MovieAuthor, MovieAuthorId> {
}