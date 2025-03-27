package co.edu.javeriana.tufinca.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.javeriana.tufinca.DTOS.PagoDTO;
import co.edu.javeriana.tufinca.entities.Pago;
import co.edu.javeriana.tufinca.repositories.PagoRepository;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    // Convertir Entidad -> DTO
    private PagoDTO convertToDTO(Pago pago) {
        PagoDTO pagoDTO = new PagoDTO();
        pagoDTO.setId(pago.getId());
        pagoDTO.setValor(pago.getValor().doubleValue());
        pagoDTO.setBanco(pago.getBanco());
        pagoDTO.setNumeroCuenta(pago.getNumeroCuenta());
        pagoDTO.setFechaPago(pago.getFechaPago());
        pagoDTO.setEstado(pago.getEstado().toString());
        return pagoDTO;
    }

    // Convertir DTO -> Entidad
    private Pago convertToEntity(PagoDTO pagoDTO) {
        Pago pago = new Pago();
        pago.setId(pagoDTO.getId());
        // SolicitudArriendo would need to be fetched from a repository
        pago.setValor(java.math.BigDecimal.valueOf(pagoDTO.getValor()));
        pago.setBanco(pagoDTO.getBanco());
        pago.setNumeroCuenta(pagoDTO.getNumeroCuenta());
        pago.setFechaPago(pagoDTO.getFechaPago());
        pago.setEstado(Pago.EstadoPago.valueOf(pagoDTO.getEstado()));
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
        Pago pago = convertToEntity(pagoDTO);
        return convertToDTO(pagoRepository.save(pago));
    }
    
    public PagoDTO actualizarPago(Long id, PagoDTO pagoDTO) {
        Optional<Pago> pagoExistente = pagoRepository.findById(id);
        
        if (pagoExistente.isPresent()) {
            Pago pago = convertToEntity(pagoDTO);
            pago.setId(id);
            // Si hay una relación con SolicitudArriendo, mantener la misma
            pago.setSolicitud(pagoExistente.get().getSolicitud());
            return convertToDTO(pagoRepository.save(pago));
        }
        return null;
    }

    public void eliminarPago(Long id) {
        pagoRepository.deleteById(id);
    }
}
