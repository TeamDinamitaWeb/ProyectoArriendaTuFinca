package co.edu.javeriana.tufinca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.javeriana.tufinca.entities.Pago;

public interface PagoRepository extends JpaRepository<Pago, Long> {
}
