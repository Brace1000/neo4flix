package com.neo4flix.recommendation.repository;

import com.neo4flix.recommendation.model.MovieRecommendation;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RecommendationRepository extends Neo4jRepository<MovieRecommendation, String> {
    
    // Collaborative Filtering: Find movies liked by similar users
    @Query("MATCH (u:User) WHERE ID(u) = $userId " +
           "MATCH (u)-[r1:RATED]->(m:Movie) " +
           "WITH u, avg(r1.score) as userAvg " +
           "MATCH (u)-[r2:RATED]->(m2:Movie)<-[r3:RATED]-(other:User) " +
           "WHERE r2.score > userAvg AND r3.score > userAvg AND u <> other " +
           "WITH u, other, count(DISTINCT m2) as commonMovies " +
           "ORDER BY commonMovies DESC LIMIT 10 " +
           "MATCH (other)-[r4:RATED]->(rec:Movie) " +
           "WHERE NOT EXISTS((u)-[:RATED]->(rec)) AND r4.score >= 4 " +
           "RETURN rec.id as id, rec.title as title, rec.description as description, " +
           "rec.releaseDate as releaseDate, rec.genres as genres, rec.posterUrl as posterUrl, " +
           "rec.averageRating as averageRating, avg(r4.score) as relevanceScore " +
           "ORDER BY relevanceScore DESC, rec.averageRating DESC LIMIT $limit")
    List<MovieRecommendation> findCollaborativeRecommendations(Long userId, int limit);
    
    // Content-Based: Find similar movies based on genre
    @Query("MATCH (u:User) WHERE ID(u) = $userId " +
           "MATCH (u)-[r:RATED]->(m:Movie) " +
           "WHERE r.score >= 4 " +
           "UNWIND m.genres as genre " +
           "MATCH (rec:Movie) " +
           "WHERE genre IN rec.genres AND NOT EXISTS((u)-[:RATED]->(rec)) " +
           "RETURN rec.id as id, rec.title as title, rec.description as description, " +
           "rec.releaseDate as releaseDate, rec.genres as genres, rec.posterUrl as posterUrl, " +
           "rec.averageRating as averageRating, count(genre) as relevanceScore " +
           "ORDER BY relevanceScore DESC, rec.averageRating DESC LIMIT $limit")
    List<MovieRecommendation> findContentBasedRecommendations(Long userId, int limit);
    
    // Hybrid: Combine collaborative and content-based
    @Query("MATCH (u:User) WHERE ID(u) = $userId " +
           "MATCH (u)-[r1:RATED]->(m:Movie) " +
           "WITH u, collect(m) as ratedMovies, avg(r1.score) as userAvg " +
           "MATCH (u)-[r2:RATED]->(m2:Movie)<-[r3:RATED]-(other:User) " +
           "WHERE r2.score >= 4 AND r3.score >= 4 " +
           "WITH u, ratedMovies, other, count(DISTINCT m2) as commonMovies " +
           "ORDER BY commonMovies DESC LIMIT 5 " +
           "MATCH (other)-[r4:RATED]->(rec:Movie) " +
           "WHERE NOT rec IN ratedMovies AND r4.score >= 4 " +
           "WITH u, rec, avg(r4.score) as collabScore, ratedMovies " +
           "UNWIND ratedMovies as rated " +
           "WITH rec, collabScore, rated " +
           "WHERE ANY(g IN rated.genres WHERE g IN rec.genres) " +
           "RETURN rec.id as id, rec.title as title, rec.description as description, " +
           "rec.releaseDate as releaseDate, rec.genres as genres, rec.posterUrl as posterUrl, " +
           "rec.averageRating as averageRating, " +
           "(collabScore * 0.7 + rec.averageRating * 0.3) as relevanceScore " +
           "ORDER BY relevanceScore DESC LIMIT $limit")
    List<MovieRecommendation> findHybridRecommendations(Long userId, int limit);
    
    // Filter by genre
    @Query("MATCH (u:User) WHERE ID(u) = $userId " +
           "MATCH (u)-[r:RATED]->(m:Movie) " +
           "WHERE r.score >= 4 AND $genre IN m.genres " +
           "MATCH (rec:Movie) " +
           "WHERE $genre IN rec.genres AND NOT EXISTS((u)-[:RATED]->(rec)) " +
           "RETURN rec.id as id, rec.title as title, rec.description as description, " +
           "rec.releaseDate as releaseDate, rec.genres as genres, rec.posterUrl as posterUrl, " +
           "rec.averageRating as averageRating, rec.averageRating as relevanceScore " +
           "ORDER BY relevanceScore DESC LIMIT $limit")
    List<MovieRecommendation> findRecommendationsByGenre(Long userId, String genre, int limit);
}
