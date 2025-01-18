package org.example.ikm.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    private Short id;

    @NotBlank(message = "Имя автора не может быть пустым")
    @Size(max = 100, message = "Имя автора не может быть длиннее 100 символов")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "bio", length = 1)
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