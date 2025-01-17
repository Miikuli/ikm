package org.example.ikm.controllers;

import lombok.RequiredArgsConstructor;
import org.example.ikm.entities.Author;
import org.example.ikm.entities.Movie;
import org.example.ikm.repositories.AuthorRepository;
import org.example.ikm.repositories.MovieRepository;
import org.example.ikm.repositories.ReviewRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorRepository authorRepository;
    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;

    @GetMapping("/authors")
    public String allAuthors(Model model) {
        Iterable<Author> authors = authorRepository.findAll();
        model.addAttribute("authors", authors);
        return "all-authors";
    }

    @GetMapping("/authors/add")
    public String addAuthor(Model model) {
        return "add-author";
    }

    @PostMapping("/authors/add")
    public String addAuthor(
            @RequestParam String name,
            @RequestParam LocalDate birthDate,
            @RequestParam char bio,
            Model model) {

        Author author = new Author(name, birthDate, bio);
        authorRepository.save(author);
        return "redirect:/authors";
    }

    @PostMapping("/authors/delete/{id}")
    public String deleteAuthor(@PathVariable Short id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Автор не найден"));


        for (Movie movie : author.getMovies()) {
            // Удаляем все отзывы на фильм
            reviewRepository.deleteAll(movie.getReviews());
            // Удаляем фильм
            movieRepository.delete(movie);
        }

        authorRepository.delete(author);

        return "redirect:/authors";
    }

    @GetMapping("/authors/edit/{id}")
    public String editAuthorForm(@PathVariable Short id, Model model) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Автор не найден"));

        model.addAttribute("author", author);

        return "edit-author";
    }

    @PostMapping("/authors/edit/{id}")
    public String editAuthor(
            @PathVariable Short id,
            @RequestParam String name,
            @RequestParam LocalDate birthDate,
            @RequestParam char bio,
            Model model) {

        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Автор не найден"));

        author.setName(name);
        author.setBirthDate(birthDate);
        author.setBio(bio);

        authorRepository.save(author);

        return "redirect:/authors";
    }
}