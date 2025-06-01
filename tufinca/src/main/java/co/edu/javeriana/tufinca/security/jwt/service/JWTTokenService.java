package co.edu.javeriana.tufinca.security.jwt.service;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import co.edu.javeriana.tufinca.DTOS.UsuarioDTOs;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTTokenService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    private Key getKey() {
        byte[] secretBytes = secret.getBytes();
        return new SecretKeySpec(secretBytes, SignatureAlgorithm.HS512.getJcaName());
    }

    public String generarToken(UsuarioDTOs usuario) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        return Jwts.builder()
                .setSubject(usuario.getCorreo()) // solo correo como subject
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public String getCorreoDesdeToken(String jwtToken) {
        return decodificarToken(jwtToken).getSubject();
    }

    public Date getFechaExpiracion(String jwtToken) {
        return decodificarToken(jwtToken).getExpiration();
    }

    public Claims decodificarToken(String jwtToken) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }
}
