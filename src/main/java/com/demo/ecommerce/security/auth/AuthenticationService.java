package com.demo.ecommerce.security.auth;

import com.demo.ecommerce.dto.auth.AuthenticationRequest;
import com.demo.ecommerce.dto.auth.AuthenticationResponse;
import com.demo.ecommerce.dto.auth.RegisterRequest;
import com.demo.ecommerce.enums.Role;
import com.demo.ecommerce.exception.InvalidRefreshTokenException;
import com.demo.ecommerce.model.User;
import com.demo.ecommerce.repository.UserRepository;
import com.demo.ecommerce.security.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .name(request.getName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                ));

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(); // add Exception

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }


    public AuthenticationResponse refreshToken(
            HttpServletRequest request) {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new InvalidRefreshTokenException("Missing or invalid Authorization header");

        String refreshToken = authHeader.substring(7);
        String email = jwtService.getUsernameFromToken(refreshToken);

        if (email == null)
            throw new InvalidRefreshTokenException("Invalid refresh token");

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidRefreshTokenException("User not found"));

        if (!jwtService.isTokenValid(refreshToken, user))
            throw new InvalidRefreshTokenException("Refresh token is expired or invalid");

        String accessToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
