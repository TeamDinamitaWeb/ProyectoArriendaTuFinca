package co.edu.javeriana.tufinca.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.javeriana.tufinca.entities.Pago;

public interface PagoRepository extends JpaRepository<Pago, Long> {
    // Custom query to find all pagos including deleted ones (bypassing @Where)
    @Query(value = "SELECT * FROM pago", nativeQuery = true)
    List<Pago> findAllIncludingDeleted();
}
