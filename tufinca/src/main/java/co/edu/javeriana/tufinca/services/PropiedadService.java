package co.edu.javeriana.tufinca.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.javeriana.tufinca.DTOS.PropiedadDTO;
import co.edu.javeriana.tufinca.entities.Propiedad;
import co.edu.javeriana.tufinca.entities.Usuario;
import co.edu.javeriana.tufinca.repositories.PropiedadRepository;
import co.edu.javeriana.tufinca.repositories.UsuarioRepository;

@Service
public class PropiedadService {

    @Autowired
    private PropiedadRepository propiedadRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    private PropiedadDTO convertToDTO(Propiedad propiedad) {
        PropiedadDTO propiedadDTO = new PropiedadDTO();
        propiedadDTO.setId(propiedad.getId());
        propiedadDTO.setTitulo(propiedad.getTitulo());
        propiedadDTO.setDescripcion(propiedad.getDescripcion());
        propiedadDTO.setDireccion(propiedad.getDireccion());
        propiedadDTO.setMunicipio(propiedad.getMunicipio());
        propiedadDTO.setCapacidad(propiedad.getCapacidad());
        propiedadDTO.setPrecioPorNoche(propiedad.getPrecioPorNoche());
        propiedadDTO.setEstado(propiedad.getEstado());
        propiedadDTO.setStatus(propiedad.getStatus());
        propiedadDTO.setUsuarioId(propiedad.getUsuario().getId());
        return propiedadDTO;
    }

    private Propiedad convertToEntity(PropiedadDTO propiedadDTO) {
        Propiedad propiedad = new Propiedad();
        propiedad.setId(propiedadDTO.getId());
        propiedad.setTitulo(propiedadDTO.getTitulo());
        propiedad.setDescripcion(propiedadDTO.getDescripcion());
        propiedad.setDireccion(propiedadDTO.getDireccion());
        propiedad.setMunicipio(propiedadDTO.getMunicipio());
        propiedad.setCapacidad(propiedadDTO.getCapacidad());
        propiedad.setPrecioPorNoche(propiedadDTO.getPrecioPorNoche());
        propiedad.setEstado(propiedadDTO.getEstado());
        propiedad.setStatus(propiedadDTO.getStatus() != null ? propiedadDTO.getStatus() : 0);

        // 👇 Asignar usuario (arrendador)
        if (propiedadDTO.getUsuarioId() != null) {
            Optional<Usuario> usuario = usuarioRepository.findById(propiedadDTO.getUsuarioId());
            if (usuario.isPresent()) {
                propiedad.setUsuario(usuario.get());
            } else {
                throw new IllegalArgumentException("Usuario (arrendador) con ID " + propiedadDTO.getUsuarioId() + " no existe");
            }
        } else {
            throw new IllegalArgumentException("Debe indicar el ID del arrendador (usuario)");
        }
            return propiedad;
    }

    public List<PropiedadDTO> obtenerTodos() {
        List<Propiedad> propiedades = propiedadRepository.findAll();
        System.out.println("Propiedades obtenidas: " + propiedades.size());
        return propiedades.stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList());
    }
    
    // Obtener todas las propiedades incluyendo eliminadas
    public List<PropiedadDTO> obtenerTodosInclusoEliminados() {
        List<Propiedad> propiedades = propiedadRepository.findAllIncludingDeleted();
        System.out.println("Todas las propiedades (incluidas eliminadas): " + propiedades.size());
        return propiedades.stream()
                         .map(this::convertToDTO)
                         .collect(Collectors.toList());
    }

    public PropiedadDTO obtenerPorId(Long id) {
        Optional<Propiedad> propiedad = propiedadRepository.findById(id);
        return propiedad.map(this::convertToDTO).orElse(null);
    }

    public PropiedadDTO crearPropiedad(PropiedadDTO propiedadDTO) {
        Propiedad propiedad = convertToEntity(propiedadDTO);
        return convertToDTO(propiedadRepository.save(propiedad));
    }

    public PropiedadDTO actualizarPropiedad(Long id, PropiedadDTO propiedadDTO) {
        Optional<Propiedad> propiedadExistente = propiedadRepository.findById(id);
        
        if (propiedadExistente.isPresent()) {
            Propiedad propiedad = propiedadExistente.get();

            // Solo actualizamos los campos individuales
            propiedad.setTitulo(propiedadDTO.getTitulo());
            propiedad.setDescripcion(propiedadDTO.getDescripcion());
            propiedad.setDireccion(propiedadDTO.getDireccion());
            propiedad.setMunicipio(propiedadDTO.getMunicipio());
            propiedad.setCapacidad(propiedadDTO.getCapacidad());
            propiedad.setPrecioPorNoche(propiedadDTO.getPrecioPorNoche());
            propiedad.setEstado(propiedadDTO.getEstado());
            propiedad.setStatus(propiedadDTO.getStatus() != null ? propiedadDTO.getStatus() : propiedad.getStatus());

            // Si llega un nuevo usuarioId, lo actualizamos
            if (propiedadDTO.getUsuarioId() != null) {
                Usuario nuevoUsuario = usuarioRepository.findById(propiedadDTO.getUsuarioId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
                propiedad.setUsuario(nuevoUsuario);
            }

            return convertToDTO(propiedadRepository.save(propiedad));
        }

        return null;
    }

    public boolean eliminarPropiedad(Long id) {
        if (propiedadRepository.existsById(id)) {
            propiedadRepository.deleteById(id);
            return true;
        }
        return false;
    }
}