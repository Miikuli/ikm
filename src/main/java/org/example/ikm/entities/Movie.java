package org.example.ikm.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "Название фильма не может быть пустым")
    @Size(max = 100, message = "Название фильма не может быть длиннее 100 символов")
    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "duration")
    private LocalTime duration;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "movie_authors",
            schema = "films",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Review> reviews;

    public Movie() {
    }

    public Movie(String title, LocalDate releaseDate, LocalTime duration) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }
}