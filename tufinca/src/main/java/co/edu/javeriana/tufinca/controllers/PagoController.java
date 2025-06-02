package co.edu.javeriana.tufinca.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.javeriana.tufinca.DTOS.PagoDTO;
import co.edu.javeriana.tufinca.services.PagoService;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    // Obtener todos los pagos activos
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping
    public ResponseEntity<List<PagoDTO>> obtenerTodos() {
        return ResponseEntity.ok(pagoService.obtenerTodos());
    }

    // Obtener todos los pagos, incluyendo eliminados
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/all-including-deleted")
    public ResponseEntity<List<PagoDTO>> obtenerTodosInclusoEliminados() {
        return ResponseEntity.ok(pagoService.obtenerTodosInclusoEliminados());
    }

    // Obtener pago por ID
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/{id}")
    public ResponseEntity<PagoDTO> obtenerPorId(@PathVariable Long id) {
        PagoDTO pago = pagoService.obtenerPorId(id);
        return pago != null ? ResponseEntity.ok(pago) : ResponseEntity.notFound().build();
    }

    // Crear nuevo pago
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping
    public ResponseEntity<?> crearPago(@RequestBody PagoDTO pagoDTO) {
        try {
            PagoDTO creado = pagoService.crearPago(pagoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Datos inválidos: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear el pago: " + e.getMessage());
        }
    }

    // Actualizar pago
    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPago(@PathVariable Long id, @RequestBody PagoDTO pagoDTO) {
        try {
            PagoDTO actualizado = pagoService.actualizarPago(id, pagoDTO);
            return actualizado != null
                    ? ResponseEntity.ok(actualizado)
                    : ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Datos inválidos: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el pago: " + e.getMessage());
        }
    }

    // Eliminar pago (borrado lógico)
    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPago(@PathVariable Long id) {
        pagoService.eliminarPago(id);
        return ResponseEntity.noContent().build();
    }

    // Manejador global de excepciones
    @CrossOrigin(origins = "http://localhost:4200")
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error inesperado: " + e.getMessage());
    }
}