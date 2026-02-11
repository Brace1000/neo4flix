package com.neo4flix.user.controller;

import com.neo4flix.user.dto.*;
import com.neo4flix.user.model.User;
import com.neo4flix.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/2fa/enable")
    public ResponseEntity<String> enableTwoFactor(@PathVariable String id) {
        return ResponseEntity.ok(userService.enableTwoFactor(id));
    }

    @PostMapping("/{id}/2fa/disable")
    public ResponseEntity<Void> disableTwoFactor(@PathVariable String id) {
        userService.disableTwoFactor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/watchlist")
    public ResponseEntity<Set<String>> getWatchlist(@PathVariable String id) {
        return ResponseEntity.ok(userService.getWatchlist(id));
    }

    @PostMapping("/{id}/watchlist/{movieId}")
    public ResponseEntity<Void> addToWatchlist(@PathVariable String id, @PathVariable String movieId) {
        userService.addToWatchlist(id, movieId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/watchlist/{movieId}")
    public ResponseEntity<Void> removeFromWatchlist(@PathVariable String id, @PathVariable String movieId) {
        userService.removeFromWatchlist(id, movieId);
        return ResponseEntity.noContent().build();
    }
}
