package farcic.dev.users.service;

import farcic.dev.users.entity.UsersEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(UsersEntity user) {
        // Define o momento de criacao para calcular a expiracao do token.
        Instant now = Instant.now();

        return Jwts.builder()
                // Identifica qual aplicacao emitiu o token.
                .issuer("users-api")
                // Guarda o email como identificador do usuario no token.
                .subject(user.getEmail())
                .issuedAt(Date.from(now))
                // Token valido por 2 horas.
                .expiration(Date.from(now.plus(2, ChronoUnit.HOURS)))
                .signWith(getSignInKey())
                .compact();
    }

    public String validateToken(String token) {
        // Valida assinatura/expiracao e retorna o subject, que aqui e o email.
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    private SecretKey getSignInKey() {
        // Converte a secret configurada em uma chave HMAC usada para assinar o JWT.
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}
