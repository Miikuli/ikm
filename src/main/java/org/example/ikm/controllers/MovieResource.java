package org.example.ikm.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.ikm.models.entities.Movie;
import org.example.ikm.repositories.MovieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/movies")
@RequiredArgsConstructor
public class MovieResource {

    private final MovieRepository movieRepository;

    private final ObjectMapper objectMapper;

    @GetMapping
    public List<Movie> getAll() {
        return movieRepository.findAll();
    }

    @GetMapping("/{id}")
    public Movie getOne(@PathVariable Integer id) {
        Optional<Movie> movieOptional = movieRepository.findById(id);
        return movieOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
    }

    @GetMapping("/by-ids")
    public List<Movie> getMany(@RequestParam List<Integer> ids) {
        return movieRepository.findAllById(ids);
    }

    @PostMapping
    public Movie create(@RequestBody Movie movie) {
        return movieRepository.save(movie);
    }

    @PatchMapping("/{id}")
    public Movie patch(@PathVariable Integer id, @RequestBody JsonNode patchNode) throws IOException {
        Movie movie = movieRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));

        objectMapper.readerForUpdating(movie).readValue(patchNode);

        return movieRepository.save(movie);
    }

    @PatchMapping
    public List<Integer> patchMany(@RequestParam List<Integer> ids, @RequestBody JsonNode patchNode) throws IOException {
        Collection<Movie> movies = movieRepository.findAllById(ids);

        for (Movie movie : movies) {
            objectMapper.readerForUpdating(movie).readValue(patchNode);
        }

        List<Movie> resultMovies = movieRepository.saveAll(movies);
        return resultMovies.stream()
                .map(Movie::getId)
                .toList();
    }

    @DeleteMapping("/{id}")
    public Movie delete(@PathVariable Integer id) {
        Movie movie = movieRepository.findById(id).orElse(null);
        if (movie != null) {
            movieRepository.delete(movie);
        }
        return movie;
    }

    @DeleteMapping
    public void deleteMany(@RequestParam List<Integer> ids) {
        movieRepository.deleteAllById(ids);
    }
}
