package org.example.ikm.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.ikm.models.entities.Review;
import org.example.ikm.repositories.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/rest/reviews")
@RequiredArgsConstructor
public class ReviewResource {

    private final ReviewRepository reviewRepository;

    private final ObjectMapper objectMapper;

    @GetMapping
    public List<Review> getAll() {
        return reviewRepository.findAll();
    }

    @GetMapping("/{id}")
    public Review getOne(@PathVariable UUID id) {
        Optional<Review> reviewOptional = reviewRepository.findById(id);
        return reviewOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
    }

    @GetMapping("/by-ids")
    public List<Review> getMany(@RequestParam List<UUID> ids) {
        return reviewRepository.findAllById(ids);
    }

    @PostMapping
    public Review create(@RequestBody Review review) {
        return reviewRepository.save(review);
    }

    @PatchMapping("/{id}")
    public Review patch(@PathVariable UUID id, @RequestBody JsonNode patchNode) throws IOException {
        Review review = reviewRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));

        objectMapper.readerForUpdating(review).readValue(patchNode);

        return reviewRepository.save(review);
    }

    @PatchMapping
    public List<UUID> patchMany(@RequestParam List<UUID> ids, @RequestBody JsonNode patchNode) throws IOException {
        Collection<Review> reviews = reviewRepository.findAllById(ids);

        for (Review review : reviews) {
            objectMapper.readerForUpdating(review).readValue(patchNode);
        }

        List<Review> resultReviews = reviewRepository.saveAll(reviews);
        return resultReviews.stream()
                .map(Review::getId)
                .toList();
    }

    @DeleteMapping("/{id}")
    public Review delete(@PathVariable UUID id) {
        Review review = reviewRepository.findById(id).orElse(null);
        if (review != null) {
            reviewRepository.delete(review);
        }
        return review;
    }

    @DeleteMapping
    public void deleteMany(@RequestParam List<UUID> ids) {
        reviewRepository.deleteAllById(ids);
    }
}
