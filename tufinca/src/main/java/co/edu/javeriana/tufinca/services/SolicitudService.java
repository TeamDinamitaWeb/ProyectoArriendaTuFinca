package co.edu.javeriana.tufinca.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.javeriana.tufinca.DTOS.SolicitudDTO;
import co.edu.javeriana.tufinca.entities.SolicitudArriendo;
import co.edu.javeriana.tufinca.repositories.SolicitudRepository;

@Service
public class SolicitudService {

    @Autowired
    private SolicitudRepository solicitudRepository;

    private SolicitudDTO convertToDTO(SolicitudArriendo solicitud) {
        SolicitudDTO solicitudDTO = new SolicitudDTO();
        solicitudDTO.setId(solicitud.getId());
        solicitudDTO.setNombreSolicitante(solicitud.getArrendatario().getNombre() + " " + solicitud.getArrendatario().getApellido());
        solicitudDTO.setNombrePropiedad("Propiedad " + solicitud.getPropiedad().getId());
        solicitudDTO.setFechaSolicitud(solicitud.getFechaSolicitud());
        solicitudDTO.setFechaLlegada(LocalDateTime.from(solicitud.getFechaInicio().atStartOfDay()));
        solicitudDTO.setFechaSalida(LocalDateTime.from(solicitud.getFechaFin().atStartOfDay()));
        // Aquí se necesitaría calcular el valor basado en la propiedad o alguna lógica de negocio
        solicitudDTO.setValor(0.0); // Este valor debería calcularse según la lógica del negocio
        solicitudDTO.setEstado(solicitud.getEstado().toString());
        return solicitudDTO;
    }

    private SolicitudArriendo convertToEntity(SolicitudDTO solicitudDTO) {
        SolicitudArriendo solicitud = new SolicitudArriendo();
        solicitud.setId(solicitudDTO.getId());
        // Estos objetos deberían obtenerse de sus respectivos repositorios
        // solicitud.setArrendatario(...);
        // solicitud.setPropiedad(...);
        solicitud.setFechaInicio(solicitudDTO.getFechaLlegada().toLocalDate());
        solicitud.setFechaFin(solicitudDTO.getFechaSalida().toLocalDate());
        solicitud.setFechaSolicitud(solicitudDTO.getFechaSolicitud());
        solicitud.setEstado(SolicitudArriendo.EstadoSolicitud.valueOf(solicitudDTO.getEstado()));
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
        SolicitudArriendo solicitud = convertToEntity(solicitudDTO);
        return convertToDTO(solicitudRepository.save(solicitud));
    }
    
    public SolicitudDTO actualizarSolicitud(Long id, SolicitudDTO solicitudDTO) {
        Optional<SolicitudArriendo> solicitudExistente = solicitudRepository.findById(id);
        
        if (solicitudExistente.isPresent()) {
            SolicitudArriendo solicitud = convertToEntity(solicitudDTO);
            solicitud.setId(id);
            // Mantener las relaciones existentes
            SolicitudArriendo existente = solicitudExistente.get();
            solicitud.setArrendatario(existente.getArrendatario());
            solicitud.setPropiedad(existente.getPropiedad());
            solicitud.setCantidadPersonas(existente.getCantidadPersonas());
            return convertToDTO(solicitudRepository.save(solicitud));
        }
        return null;
    }

    public void eliminarSolicitud(Long id) {
        solicitudRepository.deleteById(id);
    }
}
