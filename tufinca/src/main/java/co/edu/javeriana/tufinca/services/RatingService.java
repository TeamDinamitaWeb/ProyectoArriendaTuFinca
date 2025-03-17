package co.edu.javeriana.tufinca.services;

import co.edu.javeriana.tufinca.DTOS.RatingDTO;
import co.edu.javeriana.tufinca.entities.Rating;
import co.edu.javeriana.tufinca.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    private RatingDTO convertToDTO(Rating rating) {
        return new RatingDTO(
        );
    }

    private Rating convertToEntity(RatingDTO ratingDTO) {
        return new Rating(
        );
    }

    public List<RatingDTO> obtenerTodos() {
        return ratingRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public RatingDTO obtenerPorId(Long id) {
        return ratingRepository.findById(id).map(this::convertToDTO).orElse(null);
    }

    public RatingDTO crearRating(RatingDTO ratingDTO) {
        Rating rating = convertToEntity(ratingDTO);
        return convertToDTO(ratingRepository.save(rating));
    }

    public void eliminarRating(Long id) {
        ratingRepository.deleteById(id);
    }
}
