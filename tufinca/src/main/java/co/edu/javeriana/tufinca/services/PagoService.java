package co.edu.javeriana.tufinca.services;

import co.edu.javeriana.tufinca.DTOS.PagoDTO;
import co.edu.javeriana.tufinca.entities.Pago;
import co.edu.javeriana.tufinca.repositories.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    // Convertir Entidad -> DTO
    private PagoDTO convertToDTO(Pago pago) {
        return new PagoDTO(
        );
    }

    // Convertir DTO -> Entidad
    private Pago convertToEntity(PagoDTO pagoDTO) {
        return new Pago(
        );
    }

    public List<PagoDTO> obtenerTodos() {
        return pagoRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public PagoDTO obtenerPorId(Long id) {
        return pagoRepository.findById(id).map(this::convertToDTO).orElse(null);
    }

    public PagoDTO crearPago(PagoDTO pagoDTO) {
        Pago pago = convertToEntity(pagoDTO);
        return convertToDTO(pagoRepository.save(pago));
    }

    public void eliminarPago(Long id) {
        pagoRepository.deleteById(id);
    }
}
