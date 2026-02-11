package com.neo4flix.movie.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import java.time.LocalDate;
import java.util.List;

@Node("Movie")
@Data
public class Movie {
    @Id
    @GeneratedValue
    private String id;
    
    private String title;
    private String description;
    private LocalDate releaseDate;
    private List<String> genres;
    private String posterUrl;
    private Double averageRating;
    private Integer ratingCount;
}
