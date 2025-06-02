package co.edu.javeriana.tufinca.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.util.matcher.*;

import co.edu.javeriana.tufinca.security.jwt.filter.JWTAuthorizationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements ISecurityConfig {

    @Autowired
    private JWTAuthorizationFilter jwtAuthorizationFilter;

	@Override
    @Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

	@Override
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http
            .cors(Customizer.withDefaults()) // ¡Activa CORS aquí!
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/jwt/security/autenticar/**").permitAll()
                .requestMatchers("/api/usuarios/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/propiedades/**").authenticated()
                .anyRequest().authenticated()
            )
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

	/*private RequestMatcher ignoreSpecificRequests() {
        return new OrRequestMatcher(
            // new AntPathRequestMatcher("/indicadoressuim/api/autenticacion"),
            // new AntPathRequestMatcher("/indicadoressuim/api/peticion-mes"),
            new AntPathRequestMatcher("/jwt/security/autenticar/**", HttpMethod.GET.name()),
            new AntPathRequestMatcher("/jwt/security/autenticar/**", HttpMethod.POST.name()),
            new AntPathRequestMatcher("/jwt/security/autenticar/**", HttpMethod.PUT.name()),
            new AntPathRequestMatcher("/jwt/security/autenticar/**", HttpMethod.DELETE.name()),
            new AntPathRequestMatcher("/jwt/security/usuario/**", HttpMethod.GET.name()),
            new AntPathRequestMatcher("/jwt/security/usuario/**", HttpMethod.POST.name()),
            new AntPathRequestMatcher("/jwt/security/usuario/**", HttpMethod.PUT.name()),
            new AntPathRequestMatcher("/jwt/security/usuario/**", HttpMethod.DELETE.name()),
            new AntPathRequestMatcher("/api/usuarios", HttpMethod.POST.name()),
            new AntPathRequestMatcher("/api/usuarios/crear", HttpMethod.POST.name())
        );
    }*/
}