package com.neo4flix.user.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Node("User")
@Data
public class User {
    @Id
    @GeneratedValue
    private Long id;
    
    private String username;
    private String email;
    private String password;
    private String twoFactorSecret;
    private boolean twoFactorEnabled;
    private LocalDateTime createdAt;
}
