package forohub.forohub.infra.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import forohub.forohub.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String apiSecret;

    public String generarToken(Usuario usuario) {
        try {
            var algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("forohub")
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(fechaExpiracion())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Error al generar el  token jwt", exception);
        }
    }

    public String getSubject(String tokenJWT) {
        if (tokenJWT==null) {
            throw new RuntimeException();
        }
        DecodedJWT verifier= null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            verifier = JWT.require(algorithm)
                    .withIssuer("forohub")
                    .build()
                    .verify(tokenJWT);
            verifier.getSubject();
        } catch (JWTVerificationException exception) {
            System.out.println(exception.toString());
        }
        if (verifier.getSubject()== null){
            throw new RuntimeException("Token JWT inválido o expirado!");
        }
        return verifier.getSubject();
    }

    private Instant fechaExpiracion() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}