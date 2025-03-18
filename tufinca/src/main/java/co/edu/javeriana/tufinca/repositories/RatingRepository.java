package co.edu.javeriana.tufinca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.javeriana.tufinca.entities.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {
}
