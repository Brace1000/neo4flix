package com.neo4flix.movie.repository;

import com.neo4flix.movie.model.Movie;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovieRepository extends Neo4jRepository<Movie, String> {
    List<Movie> findByTitleContainingIgnoreCase(String title);
    
    @Query("MATCH (m:Movie) WHERE ANY(genre IN m.genres WHERE genre = $genre) RETURN m")
    List<Movie> findByGenre(String genre);
    
    @Query("MATCH (m:Movie) WHERE m.releaseDate >= $startDate AND m.releaseDate <= $endDate RETURN m")
    List<Movie> findByReleaseDateBetween(LocalDate startDate, LocalDate endDate);
    
    @Query("MATCH (m:Movie) WHERE m.title =~ $regex OR ANY(genre IN m.genres WHERE genre =~ $regex) RETURN m")
    List<Movie> searchMovies(String regex);
    
    @Query("MATCH (m:Movie) RETURN m ORDER BY m.averageRating DESC LIMIT $limit")
    List<Movie> findTopRatedMovies(int limit);
}
