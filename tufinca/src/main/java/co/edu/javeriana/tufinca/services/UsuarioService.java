package co.edu.javeriana.tufinca.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.javeriana.tufinca.DTOS.UsuarioDTOs;
import co.edu.javeriana.tufinca.entities.Usuario;
import co.edu.javeriana.tufinca.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Convertir Usuario a UsuarioDTO
    private UsuarioDTOs convertirAUsuarioDTO(Usuario usuario) {
        return new UsuarioDTOs(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getCorreo(),
                usuario.getTipoUsuario(),
                usuario.getStatus()
        );
    }

    // Obtener todos los usuarios
    public List<UsuarioDTOs> obtenerTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        System.out.println("Usuarios obtenidos: " + usuarios.size());
        return usuarios.stream()
                       .map(this::convertirAUsuarioDTO)
                       .collect(Collectors.toList());
    }
    
    // Obtener todos los usuarios incluyendo eliminados
    public List<UsuarioDTOs> obtenerTodosInclusoEliminados() {
        List<Usuario> usuarios = usuarioRepository.findAllIncludingDeleted();
        System.out.println("Todos los usuarios (incluidos eliminados): " + usuarios.size());
        return usuarios.stream()
                       .map(this::convertirAUsuarioDTO)
                       .collect(Collectors.toList());
    }

    // Obtener usuario por ID
    public UsuarioDTOs obtenerPorId(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.map(this::convertirAUsuarioDTO).orElse(null);
    }

    // Crear usuario
    public UsuarioDTOs crearUsuario(Usuario usuario) {
        Usuario nuevoUsuario = usuarioRepository.save(usuario);
        return convertirAUsuarioDTO(nuevoUsuario);
    }

    // Actualizar usuario
    public UsuarioDTOs actualizarUsuario(Long id, Usuario usuarioActualizado) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);

        if (usuarioExistente.isPresent()) {
            Usuario usuario = usuarioExistente.get();
            usuario.setNombre(usuarioActualizado.getNombre());
            usuario.setApellido(usuarioActualizado.getApellido());
            usuario.setCorreo(usuarioActualizado.getCorreo());
            usuario.setTipoUsuario(usuarioActualizado.getTipoUsuario());
            
            // Actualizar contraseña si se proporciona
            if (usuarioActualizado.getContrasena() != null) {
                usuario.setContrasena(usuarioActualizado.getContrasena());
            }
            
            // Actualizar el status si se proporciona.
            if (usuarioActualizado.getStatus() != null) {
                usuario.setStatus(usuarioActualizado.getStatus());
            }
            
            usuarioRepository.save(usuario);
            return convertirAUsuarioDTO(usuario);
        }
        return null;
    }

    // Eliminar usuario (borrado lógico)
    public boolean eliminarUsuario(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
