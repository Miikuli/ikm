package org.example.ikm.repositories;

import org.example.ikm.entities.Movie;
import org.example.ikm.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> findByMovie(Movie movie);
}