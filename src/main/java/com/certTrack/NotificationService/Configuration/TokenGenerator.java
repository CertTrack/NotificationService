package com.certTrack.NotificationService.Configuration;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Component
public class TokenGenerator {
    @Value("${security.jwt.secret-key}")    
    private String secretKey;

    public String generateServiceToken(int userId) {
        return JWT.create()
                .withSubject(userId+"")
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600 * 1000))
				.withClaim("e", "dima6836753@gmail.com")
				.withClaim("a", List.of("ROLE_SERVICE"))
                .sign(Algorithm.HMAC256(secretKey));
    }
}