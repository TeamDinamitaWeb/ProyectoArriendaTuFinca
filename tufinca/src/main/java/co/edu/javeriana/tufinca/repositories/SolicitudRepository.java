package co.edu.javeriana.tufinca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.javeriana.tufinca.entities.SolicitudArriendo;

public interface SolicitudRepository extends JpaRepository<SolicitudArriendo, Long> {
}
