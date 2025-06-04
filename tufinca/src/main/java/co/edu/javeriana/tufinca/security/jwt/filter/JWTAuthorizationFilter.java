package co.edu.javeriana.tufinca.security.jwt.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.*;
import org.springframework.web.filter.OncePerRequestFilter;

import co.edu.javeriana.tufinca.security.jwt.CustomUserDetailsService;
import co.edu.javeriana.tufinca.security.jwt.service.JWTTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter{

    public static final String HEADER = "Authorization";
    public static final String PREFIX = "Bearer ";

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JWTTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain) throws ServletException, IOException {

        String path = request.getRequestURI();

        //  Ignorar rutas públicas (login, registro, etc.)
        if (path.startsWith("/jwt/security/autenticar")) {
            chain.doFilter(request, response);
            return;
        }

        try {
            if (tieneTokenValido(request)) {
                Claims claims = getClaimsDesdeToken(request);
                System.out.println("CLAIMS DEL TOKEN: " + claims);

                String correo = claims.getSubject();
                UserDetails userDetails = userDetailsService.loadUserByUsername(correo);

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(correo, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                System.out.println("TOKEN VÁLIDO: " + correo);
                SecurityContextHolder.getContext().setAuthentication(authToken);

            } else {
                SecurityContextHolder.clearContext();
            }

            chain.doFilter(request, response);

        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Token inválido o expirado: " + e.getMessage());
        }
    }

    //  Helpers

    private boolean tieneTokenValido(HttpServletRequest request) {
        String header = request.getHeader(HEADER);
        return header != null && header.startsWith(PREFIX);
    }

    private Claims getClaimsDesdeToken(HttpServletRequest request) {
        String token = request.getHeader(HEADER).replace(PREFIX, "");
        return jwtTokenService.decodificarToken(token);
    }
}