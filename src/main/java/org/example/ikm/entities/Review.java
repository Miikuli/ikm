package org.example.ikm.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "reviews", schema = "films")
@DynamicInsert // Использовать значения по умолчанию из базы данных при вставке
@DynamicUpdate
public class Review {
    @Id
    @Column(name = "review_id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @NotNull(message = "Рейтинг не может быть пустым")
    @Min(value = 1, message = "Рейтинг должен быть не меньше 1")
    @Max(value = 10, message = "Рейтинг должен быть не больше 10")
    @Column(name = "rating")
    private Short rating;

    @Column(name = "review_text", length = Integer.MAX_VALUE)
    private String reviewText;

    @Column(name = "review_date")
    private OffsetDateTime reviewDate;


    public Review() {
    }

    public Review(UUID id, Movie movie, Short rating, String reviewText, OffsetDateTime reviewDate) {
        this.id = id;
        this.movie = movie;
        this.rating = rating;
        this.reviewText = reviewText;
        this.reviewDate = reviewDate;
    }
}