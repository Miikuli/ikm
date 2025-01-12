package org.example.ikm.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.ikm.models.entities.Author;
import org.example.ikm.repositories.AuthorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/authors")
@RequiredArgsConstructor
public class AuthorResource {

    private final AuthorRepository authorRepository;

    private final ObjectMapper objectMapper;

    @GetMapping
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @GetMapping("/add")
    public Author getOne(@PathVariable Short id) {
        Optional<Author> authorOptional = authorRepository.findById(id);
        return authorOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
    }

    @GetMapping("/by-ids")
    public List<Author> getMany(@RequestParam List<Short> ids) {
        return authorRepository.findAllById(ids);
    }

    @PostMapping
    public Author create(@RequestBody Author author) {
        return authorRepository.save(author);
    }

    @PatchMapping("/{id}")
    public Author patch(@PathVariable Short id, @RequestBody JsonNode patchNode) throws IOException {
        Author author = authorRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));

        objectMapper.readerForUpdating(author).readValue(patchNode);

        return authorRepository.save(author);
    }

    @PatchMapping
    public List<Short> patchMany(@RequestParam List<Short> ids, @RequestBody JsonNode patchNode) throws IOException {
        Collection<Author> authors = authorRepository.findAllById(ids);

        for (Author author : authors) {
            objectMapper.readerForUpdating(author).readValue(patchNode);
        }

        List<Author> resultAuthors = authorRepository.saveAll(authors);
        return resultAuthors.stream()
                .map(Author::getId)
                .toList();
    }

    @DeleteMapping("/{id}")
    public Author delete(@PathVariable Short id) {
        Author author = authorRepository.findById(id).orElse(null);
        if (author != null) {
            authorRepository.delete(author);
        }
        return author;
    }

    @DeleteMapping
    public void deleteMany(@RequestParam List<Short> ids) {
        authorRepository.deleteAllById(ids);
    }
}
