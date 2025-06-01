package co.edu.javeriana.tufinca.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.javeriana.tufinca.DTOS.RatingDTO;
import co.edu.javeriana.tufinca.entities.Rating;
import co.edu.javeriana.tufinca.entities.SolicitudArriendo;
import co.edu.javeriana.tufinca.entities.Usuario;
import co.edu.javeriana.tufinca.repositories.RatingRepository;
import co.edu.javeriana.tufinca.repositories.SolicitudRepository;
import co.edu.javeriana.tufinca.repositories.UsuarioRepository;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private SolicitudRepository solicitudRepository;

    private RatingDTO convertToDTO(Rating rating) {
        RatingDTO ratingDTO = new RatingDTO();
        ratingDTO.setId(rating.getId());
        
        // Establecer IDs de relaciones
        if (rating.getSolicitud() != null) {
            ratingDTO.setSolicitudId(rating.getSolicitud().getId());
        }
        
        if (rating.getArrendatario() != null) {
            ratingDTO.setArrendatarioId(rating.getArrendatario().getId());
            ratingDTO.setNombreUsuario(rating.getArrendatario().getNombre() + " " + rating.getArrendatario().getApellido());
        }
        
        if (rating.getArrendador() != null) {
            ratingDTO.setArrendadorId(rating.getArrendador().getId());
        }
        
        if (rating.getSolicitud() != null && rating.getSolicitud().getPropiedad() != null) {
            ratingDTO.setNombrePropiedad("Propiedad " + rating.getSolicitud().getPropiedad().getId());
        }
        
        ratingDTO.setCalificacionFinca(rating.getCalificacionFinca());
        ratingDTO.setComentarioFinca(rating.getComentarioFinca());
        ratingDTO.setCalificacionArrendatario(rating.getCalificacionArrendatario());
        ratingDTO.setComentarioArrendatario(rating.getComentarioArrendatario());
        ratingDTO.setStatus(rating.getStatus());
        
        return ratingDTO;
    }

    private Rating convertToEntity(RatingDTO ratingDTO) {
        Rating rating = new Rating();
        rating.setId(ratingDTO.getId());
        
        // Buscar y asignar la solicitud por ID
        if (ratingDTO.getSolicitudId() != null) {
            Optional<SolicitudArriendo> solicitud = solicitudRepository.findById(ratingDTO.getSolicitudId());
            if (solicitud.isPresent()) {
                rating.setSolicitud(solicitud.get());
            } else {
                throw new IllegalArgumentException("La solicitud con ID " + ratingDTO.getSolicitudId() + " no existe");
            }
        }
        
        // Buscar y asignar el arrendador por ID
        if (ratingDTO.getArrendadorId() != null) {
            Optional<Usuario> arrendador = usuarioRepository.findById(ratingDTO.getArrendadorId());
            if (arrendador.isPresent()) {
                rating.setArrendador(arrendador.get());
            } else {
                throw new IllegalArgumentException("El arrendador con ID " + ratingDTO.getArrendadorId() + " no existe");
            }
        }
        
        // Buscar y asignar el arrendatario por ID
        if (ratingDTO.getArrendatarioId() != null) {
            Optional<Usuario> arrendatario = usuarioRepository.findById(ratingDTO.getArrendatarioId());
            if (arrendatario.isPresent()) {
                rating.setArrendatario(arrendatario.get());
            } else {
                throw new IllegalArgumentException("El arrendatario con ID " + ratingDTO.getArrendatarioId() + " no existe");
            }
        }
        
        // Establecer calificaciones y comentarios
        rating.setCalificacionFinca(ratingDTO.getCalificacionFinca());
        rating.setComentarioFinca(ratingDTO.getComentarioFinca());
        
        // Establecer calificación y comentario del arrendatario si están disponibles
        if (ratingDTO.getCalificacionArrendatario() != null) {
            rating.setCalificacionArrendatario(ratingDTO.getCalificacionArrendatario());
        } else {
            rating.setCalificacionArrendatario(0); // Valor por defecto
        }
        
        rating.setComentarioArrendatario(ratingDTO.getComentarioArrendatario());
        
        // Establecer status para borrado lógico (0 por defecto)
        rating.setStatus(ratingDTO.getStatus() != null ? ratingDTO.getStatus() : 0);
        
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
        try {
            Rating rating = convertToEntity(ratingDTO);
            return convertToDTO(ratingRepository.save(rating));
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al crear la calificación: " + e.getMessage());
        }
    }
    
    public RatingDTO actualizarRating(Long id, RatingDTO ratingDTO) {
        Optional<Rating> ratingExistente = ratingRepository.findById(id);
        
        if (ratingExistente.isPresent()) {
            try {
                Rating rating = convertToEntity(ratingDTO);
                rating.setId(id);
                
                // Mantener relaciones si no se especifican nuevas
                Rating existente = ratingExistente.get();
                if (rating.getSolicitud() == null && existente.getSolicitud() != null) {
                    rating.setSolicitud(existente.getSolicitud());
                }
                if (rating.getArrendador() == null && existente.getArrendador() != null) {
                    rating.setArrendador(existente.getArrendador());
                }
                if (rating.getArrendatario() == null && existente.getArrendatario() != null) {
                    rating.setArrendatario(existente.getArrendatario());
                }
                
                return convertToDTO(ratingRepository.save(rating));
            } catch (IllegalArgumentException e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException("Error al actualizar la calificación: " + e.getMessage());
            }
        }
        return null;
    }

    public void eliminarRating(Long id) {
        ratingRepository.deleteById(id);
    }
}
