package co.edu.javeriana.tufinca.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import co.edu.javeriana.tufinca.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);
    
    // Custom query to find all users including deleted ones (bypassing @Where)
    @Query(value = "SELECT * FROM usuario", nativeQuery = true)
    List<Usuario> findAllIncludingDeleted();
}
