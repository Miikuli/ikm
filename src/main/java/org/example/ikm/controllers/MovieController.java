package org.example.ikm.controllers;

import lombok.RequiredArgsConstructor;
import org.example.ikm.models.entities.Author;
import org.example.ikm.models.entities.Movie;
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
            @RequestParam Set<Short> authorIds, // Используйте Integer вместо Short
            Model model) {

        Movie movie = new Movie(title, releaseDate, duration);

        // Получаем авторов по их ID
        List<Author> authorsList = authorRepository.findAllById(authorIds);

        // Преобразуем List<Author> в Set<Author>
        Set<Author> authorsSet = new HashSet<>(authorsList);

        // Устанавливаем авторов для фильма
        movie.setAuthors(authorsSet);

        // Сохраняем фильм
        movieRepository.save(movie);

        return "redirect:/movies";
    }
}