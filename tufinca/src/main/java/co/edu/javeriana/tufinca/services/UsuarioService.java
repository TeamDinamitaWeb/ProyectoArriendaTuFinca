package co.edu.javeriana.tufinca.services;

import co.edu.javeriana.tufinca.DTOS.UsuarioDTOs;
import co.edu.javeriana.tufinca.entities.Usuario;
import co.edu.javeriana.tufinca.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                usuario.getTipoUsuario()
        );
    }

    // Obtener todos los usuarios
    public List<UsuarioDTOs> obtenerTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream().map(this::convertirAUsuarioDTO).collect(Collectors.toList());
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
