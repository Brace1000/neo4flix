package com.neo4flix.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String userId;
    private String username;
    private boolean twoFactorEnabled;
    private String qrCode;
}
