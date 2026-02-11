package com.neo4flix.user.repository;

import com.neo4flix.user.model.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends Neo4jRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    
    @Query("MATCH (u:User) WHERE id(u) = $userId " +
           "MATCH (u)-[:WATCHLIST]->(m:Movie) " +
           "RETURN m.id")
    Set<String> findWatchlistByUserId(Long userId);
    
    @Query("MATCH (u:User) WHERE id(u) = $userId " +
           "MATCH (m:Movie {id: $movieId}) " +
           "MERGE (u)-[:WATCHLIST]->(m)")
    void addToWatchlist(Long userId, String movieId);
    
    @Query("MATCH (u:User) WHERE id(u) = $userId " +
           "MATCH (u)-[r:WATCHLIST]->(m:Movie {id: $movieId}) " +
           "DELETE r")
    void removeFromWatchlist(Long userId, String movieId);
}
