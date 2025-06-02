package co.edu.javeriana.tufinca.security.jwt.service;

import java.security.Key;
import java.util.*;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import co.edu.javeriana.tufinca.DTOS.UsuarioDTOs;
import io.jsonwebtoken.*;

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

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", usuario.getId());
        claims.put("nombre", usuario.getNombre());
        claims.put("apellido", usuario.getApellido());
        claims.put("tipoUsuario", usuario.getTipoUsuario());

        return Jwts.builder()
                .setClaims(claims) // ← Aquí van todos los datos adicionales
                .setSubject(usuario.getCorreo()) // ← El subject sigue siendo el correo
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
