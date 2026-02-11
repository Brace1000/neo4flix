package com.neo4flix.movie.controller;

import com.neo4flix.movie.model.Movie;
import com.neo4flix.movie.service.MovieService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = "*")
public class MovieController {
    private final MovieService movieService;
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String TMDB_API_KEY = "8265bd1679663a7ea12ac168da84d2e8";

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        return ResponseEntity.ok(movieService.createMovie(movie));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable String id) {
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable String id, @RequestBody Movie movie) {
        return ResponseEntity.ok(movieService.updateMovie(id, movie));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable String id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Movie>> search(@RequestParam String query) {
        return ResponseEntity.ok(movieService.search(query));
    }

    @GetMapping("/search/title")
    public ResponseEntity<List<Movie>> searchByTitle(@RequestParam String title) {
        return ResponseEntity.ok(movieService.searchByTitle(title));
    }

    @GetMapping("/search/genre")
    public ResponseEntity<List<Movie>> searchByGenre(@RequestParam String genre) {
        return ResponseEntity.ok(movieService.searchByGenre(genre));
    }

    @GetMapping("/search/date")
    public ResponseEntity<List<Movie>> searchByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(movieService.searchByDateRange(startDate, endDate));
    }

    @GetMapping("/top-rated")
    public ResponseEntity<List<Movie>> getTopRated(@RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(movieService.getTopRated(limit));
    }

    @GetMapping("/search/tmdb")
    public ResponseEntity<?> searchTMDB(@RequestParam String query) {
        String url = String.format("https://api.themoviedb.org/3/search/movie?api_key=%s&query=%s", 
                                   TMDB_API_KEY, query);
        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/add-from-tmdb")
    public ResponseEntity<Movie> addFromTMDB(@RequestBody Map<String, Object> tmdbMovie) {
        try {
            Movie movie = movieService.createMovieFromTMDB(tmdbMovie);
            return ResponseEntity.ok(movie);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
