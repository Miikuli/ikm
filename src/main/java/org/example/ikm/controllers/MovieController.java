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

    @PostMapping("/movies/delete/{id}")
    public String deleteMovie(@PathVariable Integer id) {
        // Находим фильм по ID
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Фильм не найден"));

        // Удаляем связи с авторами
        movie.getAuthors().clear(); // Очищаем связи
        movieRepository.save(movie); // Сохраняем изменения

        // Удаляем фильм
        movieRepository.delete(movie);

        return "redirect:/movies";
    }

    @GetMapping("/movies/edit/{id}")
    public String editMovieForm(@PathVariable Integer id, Model model) {
        // Находим фильм по ID
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Фильм не найден"));

        // Получаем список всех авторов для выпадающего списка
        List<Author> authors = authorRepository.findAll();

        // Передаем фильм и список авторов в шаблон
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

        // Находим фильм по ID
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Фильм не найден"));

        // Находим авторов по ID
        Set<Author> authors = new HashSet<>(authorRepository.findAllById(authorIds));

        // Обновляем данные фильма
        movie.setTitle(title);
        movie.setReleaseDate(releaseDate);
        movie.setDuration(duration);
        movie.setAuthors(authors);

        // Сохраняем изменения
        movieRepository.save(movie);

        return "redirect:/movies";
    }
}