package org.rossijr.bebsystem.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET = "your-256-bit-secret";
    private static final long EXPIRE_TIME = 5 * 60 * 60 * 1000; // 5 hours in milliseconds -- Time in milliseconds to the token expire
    private static final Algorithm algorithm = Algorithm.HMAC256(SECRET);

    public static String createToken(String subject) {
        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + EXPIRE_TIME);

        return JWT.create()
                .withSubject(subject)
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .sign(algorithm);
    }

    public static DecodedJWT verifyToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    public static String getUsernameFromToken(String token) {
        try {
            DecodedJWT jwt = JWT.require(algorithm).build().verify(token);
            return jwt.getClaim("sub").asString();
        } catch (Exception e) {
            // Handle exception (e.g., token expiration, invalid signature)
            System.err.println("Error decoding token: " + e.getMessage());
            return null;
        }
    }
}
