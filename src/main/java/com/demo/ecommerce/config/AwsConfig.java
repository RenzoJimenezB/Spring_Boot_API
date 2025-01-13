package com.demo.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;


@Configuration
public class AwsConfig {


    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(ProfileCredentialsProvider.create("s3-local-user"))
                .build();
    }


//    @Bean
//    public SecretsManagerClient secretsManagerClient() {
//        return SecretsManagerClient.builder()
//                .region(Region.US_EAST_1)
//                .build();
//    }
}
