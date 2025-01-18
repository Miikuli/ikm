package org.example.ikm.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ikm.dto.ReviewDTO;
import org.example.ikm.entities.Review;
import org.example.ikm.repositories.MovieRepository;
import org.example.ikm.repositories.ReviewRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Tag(name = "Reviews", description = "API для работы с отзывами")
public class ReviewController {

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;

    @GetMapping
    @Operation(summary = "Получить все отзывы")
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        List<ReviewDTO> reviews = reviewRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reviews);
    }

    @PostMapping
    @Operation(summary = "Добавить новый отзыв")
    public ResponseEntity<ReviewDTO> addReview(
            @Valid @RequestBody ReviewDTO reviewDTO) {

        Review review = new Review();
        review.setId(UUID.randomUUID());
        review.setRating(reviewDTO.getRating());
        review.setReviewText(reviewDTO.getReviewText());
        review.setReviewDate(reviewDTO.getReviewDate());

        // Устанавливаем фильм
        review.setMovie(movieRepository.findById(reviewDTO.getMovieId())
                .orElseThrow(() -> new RuntimeException("Фильм не найден")));

        Review savedReview = reviewRepository.save(review);
        return ResponseEntity.ok(convertToDTO(savedReview));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить отзыв по ID")
    public ResponseEntity<Void> deleteReview(@PathVariable UUID id) {
        reviewRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить данные отзыва")
    public ResponseEntity<ReviewDTO> updateReview(
            @PathVariable UUID id,
            @Valid @RequestBody ReviewDTO reviewDTO) {

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Отзыв не найден"));

        review.setRating(reviewDTO.getRating());
        review.setReviewText(reviewDTO.getReviewText());
        review.setReviewDate(reviewDTO.getReviewDate());

        // Обновляем фильм
        review.setMovie(movieRepository.findById(reviewDTO.getMovieId())
                .orElseThrow(() -> new RuntimeException("Фильм не найден")));

        Review updatedReview = reviewRepository.save(review);
        return ResponseEntity.ok(convertToDTO(updatedReview));
    }

    private ReviewDTO convertToDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setRating(review.getRating());
        dto.setReviewText(review.getReviewText());
        dto.setReviewDate(review.getReviewDate());
        dto.setMovieId(review.getMovie().getId());
        return dto;
    }
}