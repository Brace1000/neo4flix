package com.neo4flix.recommendation.controller;

import com.neo4flix.recommendation.model.MovieRecommendation;
import com.neo4flix.recommendation.service.RecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@CrossOrigin(origins = "*")
public class RecommendationController {
    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/user/{userId}/collaborative")
    public ResponseEntity<List<MovieRecommendation>> getCollaborativeRecommendations(
            @PathVariable String userId,
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(recommendationService.getCollaborativeRecommendations(userId, limit));
    }

    @GetMapping("/user/{userId}/content-based")
    public ResponseEntity<List<MovieRecommendation>> getContentBasedRecommendations(
            @PathVariable String userId,
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(recommendationService.getContentBasedRecommendations(userId, limit));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MovieRecommendation>> getHybridRecommendations(
            @PathVariable String userId,
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(recommendationService.getHybridRecommendations(userId, limit));
    }

    @GetMapping("/user/{userId}/genre/{genre}")
    public ResponseEntity<List<MovieRecommendation>> getRecommendationsByGenre(
            @PathVariable String userId,
            @PathVariable String genre,
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(recommendationService.getRecommendationsByGenre(userId, genre, limit));
    }
}
