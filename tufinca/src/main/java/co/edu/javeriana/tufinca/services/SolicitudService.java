package co.edu.javeriana.tufinca.services;

import co.edu.javeriana.tufinca.DTOS.SolicitudDTO;
import co.edu.javeriana.tufinca.entities.SolicitudArriendo;
import co.edu.javeriana.tufinca.repositories.SolicitudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SolicitudService {

    @Autowired
    private SolicitudRepository solicitudRepository;

    private SolicitudDTO convertToDTO(SolicitudArriendo solicitud) {
        return new SolicitudDTO(
        );
    }

    private SolicitudArriendo convertToEntity(SolicitudDTO solicitudDTO) {
        return new SolicitudArriendo(
        );
    }

    public List<SolicitudDTO> obtenerTodos() {
        return solicitudRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public SolicitudDTO obtenerPorId(Long id) {
        return solicitudRepository.findById(id).map(this::convertToDTO).orElse(null);
    }

    public SolicitudDTO crearSolicitud(SolicitudDTO solicitudDTO) {
        SolicitudArriendo solicitud = convertToEntity(solicitudDTO);
        return convertToDTO(solicitudRepository.save(solicitud));
    }

    public void eliminarSolicitud(Long id) {
        solicitudRepository.deleteById(id);
    }
}
