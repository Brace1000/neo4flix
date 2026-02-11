package com.neo4flix.recommendation.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import java.time.LocalDate;
import java.util.List;

@Data
public class MovieRecommendation {
    @Id
    private String id;
    private String title;
    private String description;
    private LocalDate releaseDate;
    private List<String> genres;
    private String posterUrl;
    private Double averageRating;
    private Double relevanceScore;
}
