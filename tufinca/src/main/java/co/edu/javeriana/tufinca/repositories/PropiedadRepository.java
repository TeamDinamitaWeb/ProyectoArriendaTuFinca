package co.edu.javeriana.tufinca.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.javeriana.tufinca.entities.Propiedad;

public interface PropiedadRepository extends JpaRepository<Propiedad, Long> {
    // Custom query to find all properties including deleted ones (bypassing @Where)
    @Query(value = "SELECT * FROM propiedad", nativeQuery = true)
    List<Propiedad> findAllIncludingDeleted();
} 