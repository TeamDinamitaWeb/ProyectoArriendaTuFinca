package co.edu.javeriana.tufinca.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.javeriana.tufinca.DTOS.RatingDTO;
import co.edu.javeriana.tufinca.entities.Rating;
import co.edu.javeriana.tufinca.repositories.RatingRepository;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    private RatingDTO convertToDTO(Rating rating) {
        RatingDTO ratingDTO = new RatingDTO();
        ratingDTO.setId(rating.getId());
        ratingDTO.setNombreUsuario(rating.getArrendatario().getNombre() + " " + rating.getArrendatario().getApellido());
        ratingDTO.setNombrePropiedad("Propiedad " + rating.getSolicitud().getPropiedad().getId());
        ratingDTO.setCalificacion(rating.getCalificacionFinca());
        ratingDTO.setComentario(rating.getComentarioFinca());
        return ratingDTO;
    }

    private Rating convertToEntity(RatingDTO ratingDTO) {
        Rating rating = new Rating();
        rating.setId(ratingDTO.getId());
        // These would need to be fetched from their respective repositories
        // Usuario, Propiedad, Solicitud references would need to be set here
        rating.setCalificacionFinca(ratingDTO.getCalificacion());
        rating.setComentarioFinca(ratingDTO.getComentario());
        return rating;
    }

    public List<RatingDTO> obtenerTodos() {
        return ratingRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<RatingDTO> obtenerTodosInclusoEliminados() {
        List<Rating> ratings = ratingRepository.findAllIncludingDeleted();
        System.out.println("Todas las calificaciones (incluidas eliminadas): " + ratings.size());
        return ratings.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
    }

    public RatingDTO obtenerPorId(Long id) {
        return ratingRepository.findById(id).map(this::convertToDTO).orElse(null);
    }

    public RatingDTO crearRating(RatingDTO ratingDTO) {
        Rating rating = convertToEntity(ratingDTO);
        return convertToDTO(ratingRepository.save(rating));
    }

    public RatingDTO actualizarRating(Long id, RatingDTO ratingDTO) {
        Optional<Rating> ratingExistente = ratingRepository.findById(id);
        
        if (ratingExistente.isPresent()) {
            Rating rating = convertToEntity(ratingDTO);
            rating.setId(id);
            // Mantener las relaciones existentes
            Rating existente = ratingExistente.get();
            rating.setSolicitud(existente.getSolicitud());
            rating.setArrendador(existente.getArrendador());
            rating.setArrendatario(existente.getArrendatario());
            // Solo actualizar valores editables
            rating.setCalificacionArrendatario(existente.getCalificacionArrendatario());
            rating.setComentarioArrendatario(existente.getComentarioArrendatario());
            return convertToDTO(ratingRepository.save(rating));
        }
        return null;
    }

    public void eliminarRating(Long id) {
        ratingRepository.deleteById(id);
    }
}
