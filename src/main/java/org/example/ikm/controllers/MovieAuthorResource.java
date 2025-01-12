package org.example.ikm.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.ikm.models.entities.MovieAuthor;
import org.example.ikm.models.entities.MovieAuthorId;
import org.example.ikm.repositories.MovieAuthorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/movieAuthors")
@RequiredArgsConstructor
public class MovieAuthorResource {

    private final MovieAuthorRepository movieAuthorRepository;

    private final ObjectMapper objectMapper;

    @GetMapping
    public List<MovieAuthor> getAll() {
        return movieAuthorRepository.findAll();
    }

    @GetMapping("/{id}")
    public MovieAuthor getOne(@PathVariable MovieAuthorId id) {
        Optional<MovieAuthor> movieAuthorOptional = movieAuthorRepository.findById(id);
        return movieAuthorOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
    }

    @GetMapping("/by-ids")
    public List<MovieAuthor> getMany(@RequestParam List<MovieAuthorId> ids) {
        return movieAuthorRepository.findAllById(ids);
    }

    @PostMapping
    public MovieAuthor create(@RequestBody MovieAuthor movieAuthor) {
        return movieAuthorRepository.save(movieAuthor);
    }

    @PatchMapping("/{id}")
    public MovieAuthor patch(@PathVariable MovieAuthorId id, @RequestBody JsonNode patchNode) throws IOException {
        MovieAuthor movieAuthor = movieAuthorRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));

        objectMapper.readerForUpdating(movieAuthor).readValue(patchNode);

        return movieAuthorRepository.save(movieAuthor);
    }

    @PatchMapping
    public List<MovieAuthorId> patchMany(@RequestParam List<MovieAuthorId> ids, @RequestBody JsonNode patchNode) throws IOException {
        Collection<MovieAuthor> movieAuthors = movieAuthorRepository.findAllById(ids);

        for (MovieAuthor movieAuthor : movieAuthors) {
            objectMapper.readerForUpdating(movieAuthor).readValue(patchNode);
        }

        List<MovieAuthor> resultMovieAuthors = movieAuthorRepository.saveAll(movieAuthors);
        return resultMovieAuthors.stream()
                .map(MovieAuthor::getId)
                .toList();
    }

    @DeleteMapping("/{id}")
    public MovieAuthor delete(@PathVariable MovieAuthorId id) {
        MovieAuthor movieAuthor = movieAuthorRepository.findById(id).orElse(null);
        if (movieAuthor != null) {
            movieAuthorRepository.delete(movieAuthor);
        }
        return movieAuthor;
    }

    @DeleteMapping
    public void deleteMany(@RequestParam List<MovieAuthorId> ids) {
        movieAuthorRepository.deleteAllById(ids);
    }
}
