package org.example.ikm.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "movies", schema = "films")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private LocalDate releaseDate;
    private LocalTime duration;

    @ManyToMany
    @JoinTable(
            name = "movie_authors",
            schema = "films",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors;

    public Movie() {
    }

    public Movie(String title, LocalDate releaseDate, LocalTime duration) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }
}