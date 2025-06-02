package co.edu.javeriana.tufinca.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import co.edu.javeriana.tufinca.DTOS.UsuarioDTOs;
import co.edu.javeriana.tufinca.entities.Usuario;
import co.edu.javeriana.tufinca.services.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping
    public ResponseEntity<List<UsuarioDTOs>> obtenerTodos() {
        return ResponseEntity.ok(usuarioService.obtenerTodos());
    }
    
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/all-including-deleted")
    public ResponseEntity<List<UsuarioDTOs>> obtenerTodosInclusoEliminados() {
        return ResponseEntity.ok(usuarioService.obtenerTodosInclusoEliminados());
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTOs> obtenerPorId(@PathVariable Long id) {
        UsuarioDTOs usuario = usuarioService.obtenerPorId(id);
        return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/crear")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario) {
        try {
            UsuarioDTOs creado = usuarioService.crearUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear el usuario: " + e.getMessage());
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTOs> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        UsuarioDTOs usuarioActualizado = usuarioService.actualizarUsuario(id, usuario);
        return usuarioActualizado != null ? ResponseEntity.ok(usuarioActualizado) : ResponseEntity.notFound().build();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        return usuarioService.eliminarUsuario(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // JWT
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "/jwt-decode", produces = MediaType.APPLICATION_JSON_VALUE)
    public UsuarioDTOs usuarioDesdeToken(Authentication authentication) throws Exception {
        return usuarioService.autorizacion(authentication);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "/error", produces = MediaType.APPLICATION_JSON_VALUE)
    public UsuarioDTOs error(Authentication authentication) throws Exception {
        return usuarioService.autorizacion(authentication);
    }
}