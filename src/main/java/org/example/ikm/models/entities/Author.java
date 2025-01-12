package org.example.ikm.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "authors", schema = "films")
public class Author {
    @Id
    @Column(name = "author_id", nullable = false)
    private Short id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "bio", length = Integer.MAX_VALUE)
    private String bio;

}