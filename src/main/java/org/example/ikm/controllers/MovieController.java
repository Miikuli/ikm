package org.example.ikm.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ikm.dto.DurationDTO;
import org.example.ikm.dto.MovieDTO;
import org.example.ikm.entities.Author;
import org.example.ikm.entities.Movie;
import org.example.ikm.repositories.AuthorRepository;
import org.example.ikm.repositories.MovieRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
@Tag(name = "Movies", description = "API для работы с фильмами")
public class MovieController {

    private final MovieRepository movieRepository;
    private final AuthorRepository authorRepository;

    @GetMapping
    @Operation(summary = "Получить все фильмы")
    public ResponseEntity<List<MovieDTO>> getAllMovies() {
        List<MovieDTO> movies = movieRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(movies);
    }

    @PostMapping
    @Operation(summary = "Добавить новый фильм")
    public ResponseEntity<MovieDTO> addMovie(
            @Valid @RequestBody MovieDTO movieDTO) {

        Movie movie = new Movie();
        movie.setTitle(movieDTO.getTitle());
        movie.setReleaseDate(movieDTO.getReleaseDate());

        // Преобразуем DurationDTO в LocalTime
        DurationDTO durationDTO = movieDTO.getDuration();
        LocalTime duration = LocalTime.of(
                durationDTO.getHour(),
                durationDTO.getMinute(),
                durationDTO.getSecond(),
                durationDTO.getNano()
        );
        movie.setDuration(duration);

        // Устанавливаем авторов
        Set<Author> authors = new HashSet<>(authorRepository.findAllById(movieDTO.getAuthorIds()));
        movie.setAuthors(authors);

        Movie savedMovie = movieRepository.save(movie);
        return ResponseEntity.ok(convertToDTO(savedMovie));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить фильм по ID")
    public ResponseEntity<Void> deleteMovie(@PathVariable Integer id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Фильм не найден"));
        movieRepository.delete(movie);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить данные фильма")
    public ResponseEntity<MovieDTO> updateMovie(
            @PathVariable Integer id,
            @Valid @RequestBody MovieDTO movieDTO) {

        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Фильм не найден"));

        movie.setTitle(movieDTO.getTitle());
        movie.setReleaseDate(movieDTO.getReleaseDate());

        // Преобразуем DurationDTO в LocalTime
        DurationDTO durationDTO = movieDTO.getDuration();
        LocalTime duration = LocalTime.of(
                durationDTO.getHour(),
                durationDTO.getMinute(),
                durationDTO.getSecond(),
                durationDTO.getNano()
        );
        movie.setDuration(duration);

        // Обновляем авторов
        Set<Author> authors = new HashSet<>(authorRepository.findAllById(movieDTO.getAuthorIds()));
        movie.setAuthors(authors);

        Movie updatedMovie = movieRepository.save(movie);
        return ResponseEntity.ok(convertToDTO(updatedMovie));
    }

    private MovieDTO convertToDTO(Movie movie) {
        MovieDTO dto = new MovieDTO();
        dto.setTitle(movie.getTitle());
        dto.setReleaseDate(movie.getReleaseDate());

        // Преобразуем LocalTime в DurationDTO
        LocalTime duration = movie.getDuration();
        DurationDTO durationDTO = new DurationDTO();
        durationDTO.setHour(duration.getHour());
        durationDTO.setMinute(duration.getMinute());
        durationDTO.setSecond(duration.getSecond());
        durationDTO.setNano(duration.getNano());
        dto.setDuration(durationDTO);

        dto.setAuthorIds(movie.getAuthors().stream()
                .map(Author::getId)
                .collect(Collectors.toSet()));
        return dto;
    }
}