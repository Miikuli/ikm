package org.example.ikm.controllers;

import lombok.RequiredArgsConstructor;
import org.example.ikm.entities.Author;
import org.example.ikm.repositories.AuthorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorRepository authorRepository;

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
        // Находим автора по ID
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Автор не найден"));

        // Удаляем связи с фильмами
        author.getMovies().clear(); // Очищаем связи
        authorRepository.save(author); // Сохраняем изменения

        // Удаляем автора
        authorRepository.delete(author);

        return "redirect:/authors";
    }

    @GetMapping("/authors/edit/{id}")
    public String editAuthorForm(@PathVariable Short id, Model model) {
        // Находим автора по ID
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Автор не найден"));

        // Передаем автора в шаблон
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

        // Находим автора по ID
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Автор не найден"));

        // Обновляем данные автора
        author.setName(name);
        author.setBirthDate(birthDate);
        author.setBio(bio);

        // Сохраняем изменения
        authorRepository.save(author);

        return "redirect:/authors";
    }
}