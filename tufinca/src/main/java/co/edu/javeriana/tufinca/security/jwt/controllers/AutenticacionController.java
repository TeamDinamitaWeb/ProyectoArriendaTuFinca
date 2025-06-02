package co.edu.javeriana.tufinca.security.jwt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
    public TokenDTO autenticar(@RequestBody UsuarioDTOs usuarioDTO){
        return new TokenDTO(jwtTokenService.generarToken(usuarioDTO), usuarioDTO);
    }

    @CrossOrigin
    @PostMapping(value = "/autenticar-correo-contrasena", produces = MediaType.APPLICATION_JSON_VALUE)
    public String autenticar(@RequestParam String correo, @RequestParam String contrasena) {

        Usuario usuario = usuarioRepository.findByCorreo(correo)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        System.out.println("Contraseña sin cifrar (input): " + contrasena);
        System.out.println("Contraseña cifrada (BD): " + usuario.getContrasena());
        System.out.println("¿Coinciden?: " + passwordEncoder.matches(contrasena, usuario.getContrasena()));


        if (!passwordEncoder.matches(contrasena, usuario.getContrasena())) {
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

        return jwtTokenService.generarToken(dto);
    }

}