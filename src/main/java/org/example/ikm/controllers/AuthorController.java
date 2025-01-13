package org.example.ikm.controllers;

import lombok.RequiredArgsConstructor;
import org.example.ikm.models.entities.Author;
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
            @RequestParam String bio,
            Model model) {

        Author author = new Author(name, birthDate, bio);
        authorRepository.save(author);
        return "redirect:/authors";
    }
}