package org.example.ikm.controllers;

import lombok.RequiredArgsConstructor;
import org.example.ikm.entities.Author;
import org.example.ikm.entities.Movie;
import org.example.ikm.repositories.AuthorRepository;
import org.example.ikm.repositories.MovieRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class MovieController {

    private final MovieRepository movieRepository;
    private final AuthorRepository authorRepository;

    @GetMapping("/movies")
    public String allMovies(Model model) {
        Iterable<Movie> movies = movieRepository.findAll();
        model.addAttribute("movies", movies);
        return "all-movies";
    }

    @GetMapping("/movies/add")
    public String addMovie(Model model) {
        Iterable<Author> authors = authorRepository.findAll();
        model.addAttribute("authors", authors);
        return "add-movie";
    }

    @PostMapping("/movies/add")
    public String addMovie(
            @RequestParam String title,
            @RequestParam LocalDate releaseDate,
            @RequestParam LocalTime duration,
            @RequestParam Set<Short> authorIds,
            Model model) {

        Movie movie = new Movie(title, releaseDate, duration);

        List<Author> authorsList = authorRepository.findAllById(authorIds);

        Set<Author> authorsSet = new HashSet<>(authorsList);

        movie.setAuthors(authorsSet);

        movieRepository.save(movie);

        return "redirect:/movies";
    }

    @PostMapping("/movies/delete/{id}")
    public String deleteMovie(@PathVariable Integer id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Фильм не найден"));

        movie.getAuthors().clear();
        movieRepository.save(movie);

        movieRepository.delete(movie);

        return "redirect:/movies";
    }

    @GetMapping("/movies/edit/{id}")
    public String editMovieForm(@PathVariable Integer id, Model model) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Фильм не найден"));

        List<Author> authors = authorRepository.findAll();

        model.addAttribute("movie", movie);
        model.addAttribute("authors", authors);

        return "edit-movie";
    }

    @PostMapping("/movies/edit/{id}")
    public String editMovie(
            @PathVariable Integer id,
            @RequestParam String title,
            @RequestParam LocalDate releaseDate,
            @RequestParam LocalTime duration,
            @RequestParam Set<Short> authorIds,
            Model model) {

        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Фильм не найден"));

        Set<Author> authors = new HashSet<>(authorRepository.findAllById(authorIds));

        movie.setTitle(title);
        movie.setReleaseDate(releaseDate);
        movie.setDuration(duration);
        movie.setAuthors(authors);

        movieRepository.save(movie);

        return "redirect:/movies";
    }
}