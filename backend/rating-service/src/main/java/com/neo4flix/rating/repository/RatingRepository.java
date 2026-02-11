package com.neo4flix.rating.repository;

import com.neo4flix.rating.model.Rating;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends Neo4jRepository<Rating, String> {
    
    @Query("MATCH (u:User) WHERE id(u) = $userId " +
           "MATCH (u)-[r:RATED]->(m:Movie) WHERE m.id = $movieId " +
           "RETURN r")
    Optional<Rating> findByUserIdAndMovieId(Long userId, String movieId);
    
    @Query("MATCH (u:User) WHERE id(u) = $userId " +
           "MATCH (u)-[r:RATED]->(m:Movie) RETURN r")
    List<Rating> findByUserId(Long userId);
    
    @Query("MATCH (u:User)-[r:RATED]->(m:Movie) WHERE m.id = $movieId RETURN r")
    List<Rating> findByMovieId(String movieId);
    
    @Query("MATCH (u:User) WHERE id(u) = $userId " +
           "MATCH (m:Movie) WHERE m.id = $movieId " +
           "MERGE (u)-[r:RATED]->(m) " +
           "SET r.score = $score, r.review = $review, r.createdAt = datetime() " +
           "WITH m " +
           "MATCH (m)<-[ratings:RATED]-() " +
           "WITH m, avg(ratings.score) as avgRating, count(ratings) as ratingCount " +
           "SET m.averageRating = avgRating, m.ratingCount = ratingCount " +
           "RETURN r")
    Rating createOrUpdateRating(Long userId, String movieId, Double score, String review);
    
    @Query("MATCH (u:User) WHERE id(u) = $userId " +
           "MATCH (u)-[r:RATED]->(m:Movie) WHERE m.id = $movieId " +
           "DELETE r " +
           "WITH m " +
           "OPTIONAL MATCH (m)<-[ratings:RATED]-() " +
           "WITH m, avg(ratings.score) as avgRating, count(ratings) as ratingCount " +
           "SET m.averageRating = COALESCE(avgRating, 0.0), m.ratingCount = ratingCount")
    void deleteRating(Long userId, String movieId);
}
