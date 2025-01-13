package com.demo.ecommerce.config;

import com.demo.ecommerce.security.JwtSecretManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;


@Configuration
@RequiredArgsConstructor
public class JwtConfig {

    
    private final JwtSecretManager jwtSecretManager;


    @Bean
    public SecretKey jwtSecretKey() {
        return jwtSecretManager.getSecretKey();
    }

}
