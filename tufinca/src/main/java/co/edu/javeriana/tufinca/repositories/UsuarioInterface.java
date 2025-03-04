package co.edu.javeriana.tufinca.repositories;

import org.springframework.data.repository.CrudRepository;

import co.edu.javeriana.tufinca.entities.Usuario;

public interface UsuarioInterface extends CrudRepository<Usuario, Long>{

}