package org.example.ikm.entities;

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
    private char bio;

    @ManyToMany(mappedBy = "authors", cascade = CascadeType.ALL)
    private Set<Movie> movies;

    public Author() {
    }

    public Author(String name, LocalDate birthDate, char bio) {
        this.name = name;
        this.birthDate = birthDate;
        this.bio = bio;
    }
}