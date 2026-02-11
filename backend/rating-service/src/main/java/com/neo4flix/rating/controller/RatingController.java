package com.neo4flix.rating.controller;

import com.neo4flix.rating.model.Rating;
import com.neo4flix.rating.service.RatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ratings")
@CrossOrigin(origins = "*")
public class RatingController {
    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping
    public ResponseEntity<Rating> createRating(@RequestBody Rating rating) {
        return ResponseEntity.ok(ratingService.createOrUpdateRating(
                rating.getUserId(), rating.getMovieId(), rating.getScore(), rating.getReview()));
    }

    @GetMapping("/user/{userId}/movie/{movieId}")
    public ResponseEntity<Rating> getRating(@PathVariable String userId, @PathVariable String movieId) {
        return ResponseEntity.ok(ratingService.getRating(userId, movieId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Rating>> getUserRatings(@PathVariable String userId) {
        return ResponseEntity.ok(ratingService.getUserRatings(userId));
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Rating>> getMovieRatings(@PathVariable String movieId) {
        return ResponseEntity.ok(ratingService.getMovieRatings(movieId));
    }

    @DeleteMapping("/user/{userId}/movie/{movieId}")
    public ResponseEntity<Void> deleteRating(@PathVariable String userId, @PathVariable String movieId) {
        ratingService.deleteRating(userId, movieId);
        return ResponseEntity.noContent().build();
    }
}
