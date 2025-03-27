package co.edu.javeriana.tufinca.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.javeriana.tufinca.DTOS.PagoDTO;
import co.edu.javeriana.tufinca.entities.Pago;
import co.edu.javeriana.tufinca.entities.SolicitudArriendo;
import co.edu.javeriana.tufinca.repositories.PagoRepository;
import co.edu.javeriana.tufinca.repositories.SolicitudRepository;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;
    
    @Autowired
    private SolicitudRepository solicitudRepository;

    // Convertir Entidad -> DTO
    private PagoDTO convertToDTO(Pago pago) {
        PagoDTO pagoDTO = new PagoDTO();
        pagoDTO.setId(pago.getId());
        pagoDTO.setValor(pago.getValor().doubleValue());
        pagoDTO.setBanco(pago.getBanco());
        pagoDTO.setNumeroCuenta(pago.getNumeroCuenta());
        pagoDTO.setFechaPago(pago.getFechaPago());
        pagoDTO.setEstado(pago.getEstado().toString());
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
        
        // Buscar y asignar la solicitud por ID si está disponible
        if (pagoDTO.getSolicitudId() != null) {
            Optional<SolicitudArriendo> solicitud = solicitudRepository.findById(pagoDTO.getSolicitudId());
            if (solicitud.isPresent()) {
                pago.setSolicitud(solicitud.get());
            } else {
                throw new IllegalArgumentException("La solicitud con ID " + pagoDTO.getSolicitudId() + " no existe");
            }
        } else {
            throw new IllegalArgumentException("El ID de solicitud es obligatorio para crear un pago");
        }
        
        // Validar y establecer valor
        if (pagoDTO.getValor() <= 0) {
            throw new IllegalArgumentException("El valor del pago debe ser mayor que cero");
        }
        pago.setValor(java.math.BigDecimal.valueOf(pagoDTO.getValor()));
        
        // Validar banco
        if (pagoDTO.getBanco() == null || pagoDTO.getBanco().trim().isEmpty()) {
            throw new IllegalArgumentException("El banco es obligatorio");
        }
        pago.setBanco(pagoDTO.getBanco());
        
        // Validar número de cuenta
        if (pagoDTO.getNumeroCuenta() == null || pagoDTO.getNumeroCuenta().trim().isEmpty()) {
            throw new IllegalArgumentException("El número de cuenta es obligatorio");
        }
        pago.setNumeroCuenta(pagoDTO.getNumeroCuenta());
        
        // Fecha de pago
        if (pagoDTO.getFechaPago() == null) {
            pago.setFechaPago(LocalDateTime.now());
        } else {
            pago.setFechaPago(pagoDTO.getFechaPago());
        }
        
        // Manejo de enum: si no hay estado o es inválido, usar el valor por defecto
        try {
            if (pagoDTO.getEstado() != null && !pagoDTO.getEstado().isEmpty()) {
                pago.setEstado(Pago.EstadoPago.valueOf(pagoDTO.getEstado()));
            } else {
                pago.setEstado(Pago.EstadoPago.PENDIENTE);
            }
        } catch (Exception e) {
            pago.setEstado(Pago.EstadoPago.PENDIENTE); // Valor por defecto
        }
        
        // Establecer status para borrado lógico (0 por defecto)
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
