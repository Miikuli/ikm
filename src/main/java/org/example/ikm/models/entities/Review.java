package org.example.ikm.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "reviews", schema = "films")
public class Review {
    @Id
    @Column(name = "review_id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    @Column(name = "rating")
    private Short rating;

    @Column(name = "review_text", length = Integer.MAX_VALUE)
    private String reviewText;

    @Column(name = "review_date")
    private OffsetDateTime reviewDate;

}