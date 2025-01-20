package com.demo.ecommerce.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.*;

import javax.crypto.SecretKey;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtSecretManager {


    private final SecretsManagerClient secretsClient;


    @Value("${aws.secrets.jwt-secret-name}")
    private String secretName;


    public SecretKey getSecretKey() {
        try {
            GetSecretValueRequest valueRequest = GetSecretValueRequest.builder()
                    .secretId(secretName)
                    .build();

            GetSecretValueResponse valueResponse = secretsClient.getSecretValue(valueRequest);
            String secretString = valueResponse.secretString();

            log.info("Secret key retrieved from AWS Secrets Manager");
            return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretString));

        } catch (SecretsManagerException e) {
            if (e.awsErrorDetails().errorCode().equals("ResourceNotFoundException")) {
                log.warn("Secret key not found in AWS Secrets Manager. Creating a new one...");
                return createSecretKey();
            }
            throw e;
        }
    }


    private SecretKey createSecretKey() {
        try {
            SecretKey key = Jwts.SIG.HS256.key().build();
            String secretString = Encoders.BASE64.encode(key.getEncoded());

            CreateSecretRequest createSecretRequest = CreateSecretRequest.builder()
                    .name(secretName)
                    .secretString(secretString)
                    .build();

            secretsClient.createSecret(createSecretRequest);

            log.info("New secret key created and stored in AWS Secrets Manager");
            return key;

        } catch (ResourceExistsException e) {
            log.warn("Secret key already exist in AWS Secrets Manager. Retrieving it...");
            return getSecretKey();
        }
    }
}
