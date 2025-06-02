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
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTOs>> obtenerTodos() {
        return ResponseEntity.ok(usuarioService.obtenerTodos());
    }
    
    @GetMapping("/all-including-deleted")
    public ResponseEntity<List<UsuarioDTOs>> obtenerTodosInclusoEliminados() {
        return ResponseEntity.ok(usuarioService.obtenerTodosInclusoEliminados());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTOs> obtenerPorId(@PathVariable Long id) {
        UsuarioDTOs usuario = usuarioService.obtenerPorId(id);
        return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario) {
        try {
            UsuarioDTOs creado = usuarioService.crearUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear el usuario: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTOs> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        UsuarioDTOs usuarioActualizado = usuarioService.actualizarUsuario(id, usuario);
        return usuarioActualizado != null ? ResponseEntity.ok(usuarioActualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        return usuarioService.eliminarUsuario(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // JWT
    @PostMapping(value = "/jwt-decode", produces = MediaType.APPLICATION_JSON_VALUE)
    public UsuarioDTOs usuarioDesdeToken(Authentication authentication) throws Exception {
        return usuarioService.autorizacion(authentication);
    }

    @GetMapping(value = "/error", produces = MediaType.APPLICATION_JSON_VALUE)
    public UsuarioDTOs error(Authentication authentication) throws Exception {
        return usuarioService.autorizacion(authentication);
    }
}