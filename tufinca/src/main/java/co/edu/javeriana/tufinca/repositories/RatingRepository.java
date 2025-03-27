package co.edu.javeriana.tufinca.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.javeriana.tufinca.entities.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    // Custom query to find all ratings including deleted ones (bypassing @Where)
    @Query(value = "SELECT * FROM rating", nativeQuery = true)
    List<Rating> findAllIncludingDeleted();
}
