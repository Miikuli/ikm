package org.example.ikm.repositories;

import org.example.ikm.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Short> {
}