package org.example.ikm.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ikm.dto.AuthorDTO;
import org.example.ikm.entities.Author;
import org.example.ikm.repositories.AuthorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
@Tag(name = "Authors", description = "API для работы с авторами")
public class AuthorController {

    private final AuthorRepository authorRepository;

    @GetMapping
    @Operation(summary = "Получить всех авторов")
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        List<AuthorDTO> authors = authorRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(authors);
    }

    @PostMapping
    @Operation(summary = "Добавить нового автора")
    public ResponseEntity<AuthorDTO> addAuthor(@Valid @RequestBody AuthorDTO authorDTO) {
        Author author = new Author();
        author.setName(authorDTO.getName());
        author.setBirthDate(authorDTO.getBirthDate());
        author.setBio(authorDTO.getBio());

        Author savedAuthor = authorRepository.save(author);
        return ResponseEntity.ok(convertToDTO(savedAuthor));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить автора по ID")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Short id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Автор не найден"));
        authorRepository.delete(author);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить данные автора")
    public ResponseEntity<AuthorDTO> updateAuthor(
            @PathVariable Short id,
            @Valid @RequestBody AuthorDTO authorDTO) {

        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Автор не найден"));

        author.setName(authorDTO.getName());
        author.setBirthDate(authorDTO.getBirthDate());
        author.setBio(authorDTO.getBio());

        Author updatedAuthor = authorRepository.save(author);
        return ResponseEntity.ok(convertToDTO(updatedAuthor));
    }

    private AuthorDTO convertToDTO(Author author) {
        AuthorDTO dto = new AuthorDTO();
        dto.setName(author.getName());
        dto.setBirthDate(author.getBirthDate());
        dto.setBio(author.getBio());
        return dto;
    }
}