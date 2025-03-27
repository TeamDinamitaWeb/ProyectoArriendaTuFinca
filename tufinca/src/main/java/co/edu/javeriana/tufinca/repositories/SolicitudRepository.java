package co.edu.javeriana.tufinca.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.javeriana.tufinca.entities.SolicitudArriendo;

public interface SolicitudRepository extends JpaRepository<SolicitudArriendo, Long> {
    // Custom query to find all solicitudes including deleted ones (bypassing @Where)
    @Query(value = "SELECT * FROM solicitud_arriendo", nativeQuery = true)
    List<SolicitudArriendo> findAllIncludingDeleted();
}
