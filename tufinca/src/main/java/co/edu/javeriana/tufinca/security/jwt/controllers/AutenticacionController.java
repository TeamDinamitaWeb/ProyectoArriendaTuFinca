package co.edu.javeriana.tufinca.security.jwt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import co.edu.javeriana.tufinca.DTOS.LoginDTO;
import co.edu.javeriana.tufinca.DTOS.TokenDTO;
import co.edu.javeriana.tufinca.DTOS.UsuarioDTOs;
import co.edu.javeriana.tufinca.entities.Usuario;
import co.edu.javeriana.tufinca.repositories.UsuarioRepository;
import co.edu.javeriana.tufinca.security.jwt.service.JWTTokenService;


@RestController
@RequestMapping(value = "/jwt/security/autenticar")
public class AutenticacionController {

    @Autowired
    JWTTokenService jwtTokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @CrossOrigin
    @PostMapping(value = "/autenticar", produces = MediaType.APPLICATION_JSON_VALUE)
    public TokenDTO autenticar(@RequestBody LoginDTO loginDTO) {
        try {
            System.out.println("Correo recibido: " + loginDTO.getCorreo());
            System.out.println("Contraseña sin cifrar (input): " + loginDTO.getContrasena());

            Usuario usuario = usuarioRepository.findByCorreo(loginDTO.getCorreo())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            System.out.println("Contraseña cifrada (BD): " + usuario.getContrasena());
            System.out.println("¿Coinciden?: " + passwordEncoder.matches(loginDTO.getContrasena(), usuario.getContrasena()));

            if (!passwordEncoder.matches(loginDTO.getContrasena(), usuario.getContrasena())) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
            }

            UsuarioDTOs dto = new UsuarioDTOs(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getCorreo(),
                usuario.getTipoUsuario(),
                usuario.getStatus()
            );

            return new TokenDTO(jwtTokenService.generarToken(dto), dto);

        } catch (Exception e) {
            System.err.println("Error durante autenticación:");
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno");
        }
    }



    /*@CrossOrigin
    @PostMapping(value = "/autenticar-correo-contrasena", produces = MediaType.APPLICATION_JSON_VALUE)
    public TokenDTO autenticar(@RequestBody LoginDTO loginDTO) {

        Usuario usuario = usuarioRepository.findByCorreo(loginDTO.getCorreo())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        System.out.println("Correo recibido: " + loginDTO.getCorreo());
        System.out.println("Contraseña sin cifrar (input): " + loginDTO.getContrasena());


        usuario = usuarioRepository.findByCorreo(loginDTO.getCorreo())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));


        System.out.println("Contraseña cifrada (BD): " + usuario.getContrasena());
        System.out.println("¿Coinciden?: " + passwordEncoder.matches(loginDTO.getContrasena(), usuario.getContrasena()));


        if (!passwordEncoder.matches(loginDTO.getContrasena(), usuario.getContrasena())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
        }

        UsuarioDTOs dto = new UsuarioDTOs(
            usuario.getId(),
            usuario.getNombre(),
            usuario.getApellido(),
            usuario.getCorreo(),
            usuario.getTipoUsuario(),
            usuario.getStatus()
        );

        return new TokenDTO(jwtTokenService.generarToken(dto), dto);
    }*/
}