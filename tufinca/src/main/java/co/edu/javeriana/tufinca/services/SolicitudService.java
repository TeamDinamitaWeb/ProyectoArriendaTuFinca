package co.edu.javeriana.tufinca.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.javeriana.tufinca.DTOS.SolicitudDTO;
import co.edu.javeriana.tufinca.entities.Propiedad;
import co.edu.javeriana.tufinca.entities.SolicitudArriendo;
import co.edu.javeriana.tufinca.entities.Usuario;
import co.edu.javeriana.tufinca.repositories.PropiedadRepository;
import co.edu.javeriana.tufinca.repositories.SolicitudRepository;
import co.edu.javeriana.tufinca.repositories.UsuarioRepository;

@Service
public class SolicitudService {

    @Autowired
    private SolicitudRepository solicitudRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PropiedadRepository propiedadRepository;

    private SolicitudDTO convertToDTO(SolicitudArriendo solicitud) {
        SolicitudDTO solicitudDTO = new SolicitudDTO();
        solicitudDTO.setId(solicitud.getId());
        
        // Establecer los IDs de relaciones
        if (solicitud.getArrendatario() != null) {
            solicitudDTO.setArrendatarioId(solicitud.getArrendatario().getId());
            solicitudDTO.setNombreSolicitante(solicitud.getArrendatario().getNombre() + " " + solicitud.getArrendatario().getApellido());
        }
        
        if (solicitud.getPropiedad() != null) {
            solicitudDTO.setPropiedadId(solicitud.getPropiedad().getId());
            solicitudDTO.setNombrePropiedad("Propiedad " + solicitud.getPropiedad().getId());
        }
        
        solicitudDTO.setFechaSolicitud(solicitud.getFechaSolicitud());
        solicitudDTO.setFechaInicio(LocalDateTime.from(solicitud.getFechaInicio().atStartOfDay()));
        solicitudDTO.setFechaFin(LocalDateTime.from(solicitud.getFechaFin().atStartOfDay()));
        solicitudDTO.setCantidadPersonas(solicitud.getCantidadPersonas());
        // Aquí se necesitaría calcular el valor basado en la propiedad o alguna lógica de negocio
        solicitudDTO.setValor(0.0); // Este valor debería calcularse según la lógica del negocio
        solicitudDTO.setEstado(solicitud.getEstado());
        solicitudDTO.setStatus(solicitud.getStatus());
        return solicitudDTO;
    }

    private SolicitudArriendo convertToEntity(SolicitudDTO solicitudDTO) {
        SolicitudArriendo solicitud = new SolicitudArriendo();
        solicitud.setId(solicitudDTO.getId());
        
        // Buscar y asignar el arrendatario por ID
        if (solicitudDTO.getArrendatarioId() != null) {
            Optional<Usuario> arrendatario = usuarioRepository.findById(solicitudDTO.getArrendatarioId());
            if (arrendatario.isPresent()) {
                solicitud.setArrendatario(arrendatario.get());
            } else {
                throw new IllegalArgumentException("El arrendatario con ID " + solicitudDTO.getArrendatarioId() + " no existe");
            }
        } else {
            throw new IllegalArgumentException("El ID del arrendatario es obligatorio");
        }
        
        // Buscar y asignar la propiedad por ID
        if (solicitudDTO.getPropiedadId() != null) {
            Optional<Propiedad> propiedad = propiedadRepository.findById(solicitudDTO.getPropiedadId());
            if (propiedad.isPresent()) {
                solicitud.setPropiedad(propiedad.get());
            } else {
                throw new IllegalArgumentException("La propiedad con ID " + solicitudDTO.getPropiedadId() + " no existe");
            }
        } else {
            throw new IllegalArgumentException("El ID de la propiedad es obligatorio");
        }
        
        try {
            // Establecer fechas
            if (solicitudDTO.getFechaInicio() != null) {
                solicitud.setFechaInicio(solicitudDTO.getFechaInicio().toLocalDate());
            } else {
                throw new IllegalArgumentException("La fecha de llegada es obligatoria");
            }
            
            if (solicitudDTO.getFechaFin() != null) {
                solicitud.setFechaFin(solicitudDTO.getFechaFin().toLocalDate());
            } else {
                throw new IllegalArgumentException("La fecha de salida es obligatoria");
            }
            
            // Establecer fecha de solicitud (usar la actual si no se proporciona)
            solicitud.setFechaSolicitud(solicitudDTO.getFechaSolicitud() != null ? 
                                    solicitudDTO.getFechaSolicitud() : LocalDateTime.now());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error en el formato de fechas: " + e.getMessage());
        }
        
        // Establecer cantidad de personas
        if (solicitudDTO.getCantidadPersonas() != null) {
            solicitud.setCantidadPersonas(solicitudDTO.getCantidadPersonas());
        } else {
            solicitud.setCantidadPersonas(1); // Valor por defecto
        }
        
        // Establecer estado de la solicitud (PENDIENTE por defecto)
        try {
            if (solicitudDTO.getEstado() != null) {
                solicitud.setEstado((solicitudDTO.getEstado()));
            } else {
                solicitud.setEstado(SolicitudArriendo.EstadoSolicitud.PENDIENTE);
            }
        } catch (Exception e) {
            solicitud.setEstado(SolicitudArriendo.EstadoSolicitud.PENDIENTE);
        }
        
        // Establecer status para borrado lógico (0 por defecto)
        solicitud.setStatus(solicitudDTO.getStatus() != null ? solicitudDTO.getStatus() : 0);
        
        return solicitud;
    }

    public List<SolicitudDTO> obtenerTodos() {
        return solicitudRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    
    // Obtener todas las solicitudes incluyendo eliminadas
    public List<SolicitudDTO> obtenerTodosInclusoEliminados() {
        List<SolicitudArriendo> solicitudes = solicitudRepository.findAllIncludingDeleted();
        System.out.println("Todas las solicitudes (incluidas eliminadas): " + solicitudes.size());
        return solicitudes.stream()
                       .map(this::convertToDTO)
                       .collect(Collectors.toList());
    }

    public SolicitudDTO obtenerPorId(Long id) {
        return solicitudRepository.findById(id).map(this::convertToDTO).orElse(null);
    }

    public SolicitudDTO crearSolicitud(SolicitudDTO solicitudDTO) {
        try {
            SolicitudArriendo solicitud = convertToEntity(solicitudDTO);
            return convertToDTO(solicitudRepository.save(solicitud));
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al crear la solicitud: " + e.getMessage());
        }
    }
    
    public SolicitudDTO actualizarSolicitud(Long id, SolicitudDTO solicitudDTO) {
        Optional<SolicitudArriendo> solicitudExistente = solicitudRepository.findById(id);
        
        if (solicitudExistente.isPresent()) {
            try {
                SolicitudArriendo solicitud = convertToEntity(solicitudDTO);
                solicitud.setId(id);
                
                // Mantener relaciones si no se especifican nuevas
                SolicitudArriendo existente = solicitudExistente.get();
                if (solicitud.getArrendatario() == null && existente.getArrendatario() != null) {
                    solicitud.setArrendatario(existente.getArrendatario());
                }
                if (solicitud.getPropiedad() == null && existente.getPropiedad() != null) {
                    solicitud.setPropiedad(existente.getPropiedad());
                }
                
                return convertToDTO(solicitudRepository.save(solicitud));
            } catch (IllegalArgumentException e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException("Error al actualizar la solicitud: " + e.getMessage());
            }
        }
        return null;
    }

    public void eliminarSolicitud(Long id) {
        solicitudRepository.deleteById(id);
    }
}
