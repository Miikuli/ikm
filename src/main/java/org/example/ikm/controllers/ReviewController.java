package org.example.ikm.controllers;

import lombok.RequiredArgsConstructor;
import org.example.ikm.entities.Movie;
import org.example.ikm.entities.Review;
import org.example.ikm.repositories.MovieRepository;
import org.example.ikm.repositories.ReviewRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;

    @GetMapping("/reviews")
    public String allReviews(Model model) {
        List<Review> reviews = reviewRepository.findAll();
        model.addAttribute("reviews", reviews);
        return "all-reviews";
    }

    @GetMapping("/reviews/add")
    public String addReviewForm(Model model) {
        List<Movie> movies = movieRepository.findAll();
        model.addAttribute("movies", movies);
        return "add-review";
    }

    @PostMapping("/reviews/add")
    public String addReview(
            @RequestParam Integer movieId,
            @RequestParam Short rating,
            @RequestParam String reviewText,
            Model model) {

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Фильм не найден"));

        Review review = new Review();
        review.setId(UUID.randomUUID()); // Генерация UUID
        review.setMovie(movie);
        review.setRating(rating);
        review.setReviewText(reviewText);
        review.setReviewDate(OffsetDateTime.now());

        reviewRepository.save(review);

        return "redirect:/reviews";
    }

    @GetMapping("/movies/{movieId}/reviews")
    public String movieReviews(@PathVariable Integer movieId, Model model) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Фильм не найден"));

        List<Review> reviews = reviewRepository.findByMovie(movie);
        model.addAttribute("movie", movie);
        model.addAttribute("reviews", reviews);

        return "movie-reviews";
    }

    @PostMapping("/reviews/delete/{id}")
    public String deleteReview(@PathVariable UUID id) {
        reviewRepository.deleteById(id);
        return "redirect:/reviews";
    }

    @GetMapping("/reviews/edit/{id}")
    public String editReviewForm(@PathVariable UUID id, Model model) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Отзыв не найден"));

        List<Movie> movies = movieRepository.findAll();

        model.addAttribute("review", review);
        model.addAttribute("movies", movies);

        return "edit-review";
    }

    @PostMapping("/reviews/edit/{id}")
    public String editReview(
            @PathVariable UUID id,
            @RequestParam Integer movieId,
            @RequestParam Short rating,
            @RequestParam String reviewText,
            Model model) {

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Отзыв не найден"));

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Фильм не найден"));

        review.setMovie(movie);
        review.setRating(rating);
        review.setReviewText(reviewText);

        reviewRepository.save(review);

        return "redirect:/reviews";
    }
}