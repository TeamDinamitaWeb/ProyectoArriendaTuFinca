package co.edu.javeriana.tufinca.controllers;

import co.edu.javeriana.tufinca.DTOS.PagoDTO;
import co.edu.javeriana.tufinca.services.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping
    public List<PagoDTO> obtenerTodos() {
        return pagoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public PagoDTO obtenerPorId(@PathVariable Long id) {
        return pagoService.obtenerPorId(id);
    }

    @PostMapping
    public PagoDTO crearPago(@RequestBody PagoDTO pagoDTO) {
        return pagoService.crearPago(pagoDTO);
    }

    @DeleteMapping("/{id}")
    public void eliminarPago(@PathVariable Long id) {
        pagoService.eliminarPago(id);
    }
}
