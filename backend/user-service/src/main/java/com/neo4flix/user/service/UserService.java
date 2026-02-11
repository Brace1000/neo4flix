package com.neo4flix.user.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.neo4flix.common.security.JwtUtil;
import com.neo4flix.user.dto.*;
import com.neo4flix.user.model.User;
import com.neo4flix.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setTwoFactorEnabled(false);
        
        user = userRepository.save(user);
        String token = jwtUtil.generateToken(user.getUsername(), String.valueOf(user.getId()));
        
        return new AuthResponse(token, String.valueOf(user.getId()), user.getUsername(), false, null);
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        if (user.isTwoFactorEnabled()) {
            if (request.getTwoFactorCode() == null || !verifyTOTP(user.getTwoFactorSecret(), request.getTwoFactorCode())) {
                throw new RuntimeException("Invalid 2FA code");
            }
        }

        String token = jwtUtil.generateToken(user.getUsername(), String.valueOf(user.getId()));
        return new AuthResponse(token, String.valueOf(user.getId()), user.getUsername(), user.isTwoFactorEnabled(), null);
    }

    public String enableTwoFactor(String userId) {
        User user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        String secret = generateSecret();
        user.setTwoFactorSecret(secret);
        user.setTwoFactorEnabled(true);
        userRepository.save(user);
        
        return generateQRCode(user.getUsername(), secret);
    }

    public void disableTwoFactor(String userId) {
        User user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setTwoFactorEnabled(false);
        user.setTwoFactorSecret(null);
        userRepository.save(user);
    }

    public User getUserById(String id) {
        return userRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUser(String id, User updatedUser) {
        User user = getUserById(id);
        if (updatedUser.getEmail() != null) user.setEmail(updatedUser.getEmail());
        return userRepository.save(user);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(Long.parseLong(id));
    }

    public Set<String> getWatchlist(String userId) {
        User user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userRepository.findWatchlistByUserId(user.getId());
    }

    @Transactional
    public void addToWatchlist(String userId, String movieId) {
        User user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.addToWatchlist(user.getId(), movieId);
    }

    @Transactional
    public void removeFromWatchlist(String userId, String movieId) {
        User user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.removeFromWatchlist(user.getId(), movieId);
    }

    private String generateSecret() {
        return Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes());
    }

    private String generateQRCode(String username, String secret) {
        try {
            String data = String.format("otpauth://totp/Neo4flix:%s?secret=%s&issuer=Neo4flix", username, secret);
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 200, 200);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            return Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate QR code", e);
        }
    }

    private boolean verifyTOTP(String secret, String code) {
        try {
            long timeWindow = System.currentTimeMillis() / 1000 / 30;
            String generatedCode = generateTOTP(secret, timeWindow);
            return generatedCode.equals(code);
        } catch (Exception e) {
            return false;
        }
    }

    private String generateTOTP(String secret, long timeWindow) throws Exception {
        byte[] key = Base64.getDecoder().decode(secret);
        byte[] data = new byte[8];
        for (int i = 7; i >= 0; i--) {
            data[i] = (byte) (timeWindow & 0xff);
            timeWindow >>= 8;
        }
        
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(new SecretKeySpec(key, "HmacSHA1"));
        byte[] hash = mac.doFinal(data);
        
        int offset = hash[hash.length - 1] & 0xf;
        int binary = ((hash[offset] & 0x7f) << 24) |
                     ((hash[offset + 1] & 0xff) << 16) |
                     ((hash[offset + 2] & 0xff) << 8) |
                     (hash[offset + 3] & 0xff);
        
        int otp = binary % 1000000;
        return String.format("%06d", otp);
    }
}
