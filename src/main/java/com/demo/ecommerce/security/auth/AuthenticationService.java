package com.demo.ecommerce.security.auth;

import com.demo.ecommerce.dto.auth.AuthenticationRequest;
import com.demo.ecommerce.dto.auth.AuthenticationResponse;
import com.demo.ecommerce.dto.auth.RegisterRequest;
import com.demo.ecommerce.model.User;
import com.demo.ecommerce.repository.UserRepository;
import com.demo.ecommerce.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService {


    private final UserRepository userRepository;
    private final JwtService jwtService;


    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .name(request.getName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(request.getPassword())
                .phone(request.getPhone())
                .role(request.getRole())
                .build();

        User savedUser = userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
//        var token = Token.builder()
//                .user(user)
//                .token
    }

//    public AuthenticationResponse authenticate(AuthenticationRequest request) {
//    }
}
