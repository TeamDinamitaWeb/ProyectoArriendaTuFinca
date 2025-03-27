package co.edu.javeriana.tufinca.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.javeriana.tufinca.DTOS.PagoDTO;
import co.edu.javeriana.tufinca.services.PagoService;

@RestController
@RequestMapping("/pagos")
@CrossOrigin(origins = "*")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping
    public ResponseEntity<List<PagoDTO>> obtenerTodos() {
        return ResponseEntity.ok(pagoService.obtenerTodos());
    }
    
    @GetMapping("/all-including-deleted")
    public ResponseEntity<List<PagoDTO>> obtenerTodosInclusoEliminados() {
        return ResponseEntity.ok(pagoService.obtenerTodosInclusoEliminados());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoDTO> obtenerPorId(@PathVariable Long id) {
        PagoDTO pago = pagoService.obtenerPorId(id);
        return pago != null ? ResponseEntity.ok(pago) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crearPago(@RequestBody PagoDTO pagoDTO) {
        try {
            PagoDTO pagoCreado = pagoService.crearPago(pagoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(pagoCreado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear el pago: " + e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPago(@PathVariable Long id, @RequestBody PagoDTO pagoDTO) {
        try {
            PagoDTO pagoActualizado = pagoService.actualizarPago(id, pagoDTO);
            if (pagoActualizado != null) {
                return ResponseEntity.ok(pagoActualizado);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el pago: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPago(@PathVariable Long id) {
        pagoService.eliminarPago(id);
        return ResponseEntity.noContent().build();
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error en el servidor: " + e.getMessage());
    }
}
