package co.edu.javeriana.tufinca.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.javeriana.tufinca.DTOS.PagoDTO;
import co.edu.javeriana.tufinca.entities.Pago;
import co.edu.javeriana.tufinca.entities.Propiedad;
import co.edu.javeriana.tufinca.entities.SolicitudArriendo;
import co.edu.javeriana.tufinca.entities.Usuario;
import co.edu.javeriana.tufinca.repositories.PagoRepository;
import co.edu.javeriana.tufinca.repositories.PropiedadRepository;
import co.edu.javeriana.tufinca.repositories.SolicitudRepository;
import co.edu.javeriana.tufinca.repositories.UsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;
    
    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private PropiedadRepository propiedadRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PersistenceContext
    private EntityManager entityManager;


    // Convertir Entidad -> DTO
    private PagoDTO convertToDTO(Pago pago) {
        PagoDTO pagoDTO = new PagoDTO();
        pagoDTO.setId(pago.getId());
        pagoDTO.setValor(pago.getValor());
        pagoDTO.setBanco(pago.getBanco());
        pagoDTO.setNumeroCuenta(pago.getNumeroCuenta());
        pagoDTO.setFechaPago(pago.getFechaPago());
        pagoDTO.setEstado(pago.getEstado());
        pagoDTO.setStatus(pago.getStatus());
        
        // Agregar ID de solicitud si está disponible
        if (pago.getSolicitud() != null) {
            pagoDTO.setSolicitudId(pago.getSolicitud().getId());
        }
        
        return pagoDTO;
    }

    // Convertir DTO -> Entidad
    private Pago convertToEntity(PagoDTO pagoDTO) {
        Pago pago = new Pago();
        pago.setId(pagoDTO.getId());

        // Validar solicitud y buscarla
        if (pagoDTO.getSolicitudId() != null) {
            Optional<SolicitudArriendo> solicitudOpt = solicitudRepository.findById(pagoDTO.getSolicitudId());
            if (solicitudOpt.isPresent()) {
                SolicitudArriendo solicitud = solicitudOpt.get();

                pago.setSolicitud(solicitud);

                pago.setSolicitud(solicitud);

                // Asignar propiedad desde la solicitud
                if (solicitud.getPropiedad() != null) {
                    pago.setPropiedad(solicitud.getPropiedad());
                } else {
                    throw new IllegalArgumentException("La solicitud no tiene una propiedad asociada.");
                }

                // Asignar usuario desde la solicitud
                if (solicitud.getArrendatario() != null) {
                    pago.setUsuario(solicitud.getArrendatario());
                }
            } else {
                throw new IllegalArgumentException("La solicitud con ID " + pagoDTO.getSolicitudId() + " no existe.");
            }
        } else {
            throw new IllegalArgumentException("El ID de solicitud es obligatorio para crear un pago.");
        }

        // Validar y establecer valor
        if (pagoDTO.getValor() == null || pagoDTO.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El valor del pago debe ser mayor que cero.");
        }
        pago.setValor(pagoDTO.getValor());

        // Validar y establecer banco
        if (pagoDTO.getBanco() == null || pagoDTO.getBanco().trim().isEmpty()) {
            throw new IllegalArgumentException("El banco es obligatorio.");
        }
        pago.setBanco(pagoDTO.getBanco());

        // Validar y establecer número de cuenta
        if (pagoDTO.getNumeroCuenta() == null || pagoDTO.getNumeroCuenta().trim().isEmpty()) {
            throw new IllegalArgumentException("El número de cuenta es obligatorio.");
        }
        pago.setNumeroCuenta(pagoDTO.getNumeroCuenta());

        // Fecha de pago (usar actual si no viene del frontend)
        if (pagoDTO.getFechaPago() != null) {
            pago.setFechaPago(pagoDTO.getFechaPago());
        } else {
            pago.setFechaPago(LocalDateTime.now());
        }

        // Estado del pago
        if (pagoDTO.getEstado() != null) {
            pago.setEstado(pagoDTO.getEstado());
        } else {
            pago.setEstado(Pago.EstadoPago.PENDIENTE);
        }

        // Borrado lógico
        pago.setStatus(pagoDTO.getStatus() != null ? pagoDTO.getStatus() : 0);

        return pago;
    }



    public List<PagoDTO> obtenerTodos() {
        return pagoRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    
    // Obtener todos los pagos incluyendo eliminados
    public List<PagoDTO> obtenerTodosInclusoEliminados() {
        List<Pago> pagos = pagoRepository.findAllIncludingDeleted();
        System.out.println("Todos los pagos (incluidos eliminados): " + pagos.size());
        return pagos.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
    }

    public PagoDTO obtenerPorId(Long id) {
        return pagoRepository.findById(id).map(this::convertToDTO).orElse(null);
    }

    public PagoDTO crearPago(PagoDTO pagoDTO) {
        try {
            Pago pago = convertToEntity(pagoDTO);
            return convertToDTO(pagoRepository.save(pago));
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el pago: " + e.getMessage());
        }
    }
    
    public PagoDTO actualizarPago(Long id, PagoDTO pagoDTO) {
        Optional<Pago> pagoExistente = pagoRepository.findById(id);
        
        if (pagoExistente.isPresent()) {
            try {
                Pago pago = convertToEntity(pagoDTO);
                pago.setId(id);
                
                // Si no se proporcionó una solicitud nueva, mantener la misma
                if (pago.getSolicitud() == null && pagoExistente.get().getSolicitud() != null) {
                    pago.setSolicitud(pagoExistente.get().getSolicitud());
                }
                
                return convertToDTO(pagoRepository.save(pago));
            } catch (IllegalArgumentException e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException("Error al actualizar el pago: " + e.getMessage());
            }
        }
        return null;
    }

    public void eliminarPago(Long id) {
        pagoRepository.deleteById(id);
    }
}
