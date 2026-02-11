package com.neo4flix.rating.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;
import java.time.LocalDateTime;

@RelationshipProperties
@Data
public class Rating {
    @Id
    @GeneratedValue
    private String id;
    
    private String userId;
    private String movieId;
    private Double score;
    private String review;
    private LocalDateTime createdAt;
}
