package com.neo4flix.movie.service;

import com.neo4flix.movie.model.Movie;
import com.neo4flix.movie.repository.MovieRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie createMovie(Movie movie) {
        movie.setAverageRating(0.0);
        movie.setRatingCount(0);
        return movieRepository.save(movie);
    }

    public Movie getMovieById(String id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie updateMovie(String id, Movie updatedMovie) {
        Movie movie = getMovieById(id);
        if (updatedMovie.getTitle() != null) movie.setTitle(updatedMovie.getTitle());
        if (updatedMovie.getDescription() != null) movie.setDescription(updatedMovie.getDescription());
        if (updatedMovie.getReleaseDate() != null) movie.setReleaseDate(updatedMovie.getReleaseDate());
        if (updatedMovie.getGenres() != null) movie.setGenres(updatedMovie.getGenres());
        if (updatedMovie.getPosterUrl() != null) movie.setPosterUrl(updatedMovie.getPosterUrl());
        return movieRepository.save(movie);
    }

    public void deleteMovie(String id) {
        movieRepository.deleteById(id);
    }

    public List<Movie> searchByTitle(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Movie> searchByGenre(String genre) {
        return movieRepository.findByGenre(genre);
    }

    public List<Movie> searchByDateRange(LocalDate startDate, LocalDate endDate) {
        return movieRepository.findByReleaseDateBetween(startDate, endDate);
    }

    public List<Movie> search(String query) {
        String regex = "(?i).*" + query + ".*";
        return movieRepository.searchMovies(regex);
    }

    public List<Movie> getTopRated(int limit) {
        return movieRepository.findTopRatedMovies(limit);
    }

    public Movie createMovieFromTMDB(Map<String, Object> tmdbMovie) {
        Movie movie = new Movie();
        movie.setId(String.valueOf(tmdbMovie.get("id")));
        movie.setTitle((String) tmdbMovie.get("title"));
        movie.setDescription((String) tmdbMovie.get("overview"));
        
        String releaseDate = (String) tmdbMovie.get("release_date");
        if (releaseDate != null && !releaseDate.isEmpty()) {
            movie.setReleaseDate(LocalDate.parse(releaseDate));
        }
        
        String posterPath = (String) tmdbMovie.get("poster_path");
        if (posterPath != null) {
            movie.setPosterUrl("https://image.tmdb.org/t/p/w500" + posterPath);
        }
        
        Object voteAverage = tmdbMovie.get("vote_average");
        if (voteAverage != null) {
            double rating = ((Number) voteAverage).doubleValue() / 2.0;
            movie.setAverageRating(rating);
        } else {
            movie.setAverageRating(0.0);
        }
        
        List<Integer> genreIds = (List<Integer>) tmdbMovie.get("genre_ids");
        if (genreIds != null) {
            List<String> genres = new ArrayList<>();
            Map<Integer, String> genreMap = Map.ofEntries(
                Map.entry(28, "Action"), Map.entry(12, "Adventure"), Map.entry(16, "Animation"),
                Map.entry(35, "Comedy"), Map.entry(80, "Crime"), Map.entry(99, "Documentary"),
                Map.entry(18, "Drama"), Map.entry(10751, "Family"), Map.entry(14, "Fantasy"),
                Map.entry(36, "History"), Map.entry(27, "Horror"), Map.entry(10402, "Music"),
                Map.entry(9648, "Mystery"), Map.entry(10749, "Romance"), Map.entry(878, "Sci-Fi"),
                Map.entry(53, "Thriller"), Map.entry(10752, "War"), Map.entry(37, "Western")
            );
            for (Integer id : genreIds) {
                if (genreMap.containsKey(id)) {
                    genres.add(genreMap.get(id));
                }
            }
            movie.setGenres(genres);
        }
        
        movie.setRatingCount(0);
        return movieRepository.save(movie);
    }
}
