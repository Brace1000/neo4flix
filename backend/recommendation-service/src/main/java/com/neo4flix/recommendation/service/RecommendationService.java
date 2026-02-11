package com.neo4flix.recommendation.service;

import com.neo4flix.recommendation.model.MovieRecommendation;
import com.neo4flix.recommendation.repository.RecommendationRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RecommendationService {
    private final RecommendationRepository recommendationRepository;

    public RecommendationService(RecommendationRepository recommendationRepository) {
        this.recommendationRepository = recommendationRepository;
    }

    public List<MovieRecommendation> getCollaborativeRecommendations(String userId, int limit) {
        return recommendationRepository.findCollaborativeRecommendations(Long.parseLong(userId), limit);
    }

    public List<MovieRecommendation> getContentBasedRecommendations(String userId, int limit) {
        return recommendationRepository.findContentBasedRecommendations(Long.parseLong(userId), limit);
    }

    public List<MovieRecommendation> getHybridRecommendations(String userId, int limit) {
        return recommendationRepository.findHybridRecommendations(Long.parseLong(userId), limit);
    }

    public List<MovieRecommendation> getRecommendationsByGenre(String userId, String genre, int limit) {
        return recommendationRepository.findRecommendationsByGenre(Long.parseLong(userId), genre, limit);
    }
}
