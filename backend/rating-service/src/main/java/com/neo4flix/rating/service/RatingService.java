package com.neo4flix.rating.service;

import com.neo4flix.rating.model.Rating;
import com.neo4flix.rating.repository.RatingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RatingService {
    private final RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Transactional
    public Rating createOrUpdateRating(String userId, String movieId, Double score, String review) {
        if (score < 0 || score > 5) {
            throw new RuntimeException("Rating must be between 0 and 5");
        }
        return ratingRepository.createOrUpdateRating(Long.parseLong(userId), movieId, score, review);
    }

    public Rating getRating(String userId, String movieId) {
        return ratingRepository.findByUserIdAndMovieId(Long.parseLong(userId), movieId)
                .orElseThrow(() -> new RuntimeException("Rating not found"));
    }

    public List<Rating> getUserRatings(String userId) {
        return ratingRepository.findByUserId(Long.parseLong(userId));
    }

    public List<Rating> getMovieRatings(String movieId) {
        return ratingRepository.findByMovieId(movieId);
    }

    @Transactional
    public void deleteRating(String userId, String movieId) {
        ratingRepository.deleteRating(Long.parseLong(userId), movieId);
    }
}
