package org.example.ikm.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "authors", schema = "films")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private LocalDate birthDate;
    private String bio;

    @ManyToMany(mappedBy = "authors")
    private Set<Movie> movies;

    public Author() {
    }

    public Author(String name, LocalDate birthDate, String bio) {
        this.name = name;
        this.birthDate = birthDate;
        this.bio = bio;
    }
}