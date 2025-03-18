package co.edu.javeriana.tufinca.controllers;

import co.edu.javeriana.tufinca.DTOS.UsuarioDTOs;
import co.edu.javeriana.tufinca.entities.Usuario;
import co.edu.javeriana.tufinca.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTOs>> obtenerTodos() {
        return ResponseEntity.ok(usuarioService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTOs> obtenerPorId(@PathVariable Long id) {
        UsuarioDTOs usuario = usuarioService.obtenerPorId(id);
        return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<UsuarioDTOs> crearUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.crearUsuario(usuario));
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
}
