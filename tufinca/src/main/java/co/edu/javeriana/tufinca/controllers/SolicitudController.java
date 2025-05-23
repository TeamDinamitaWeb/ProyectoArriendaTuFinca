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

import co.edu.javeriana.tufinca.DTOS.SolicitudDTO;
import co.edu.javeriana.tufinca.services.SolicitudService;

@RestController
@RequestMapping("/api/solicitudes")
@CrossOrigin(origins = "*")
public class SolicitudController {

    @Autowired
    private SolicitudService solicitudService;

    @GetMapping
    public ResponseEntity<List<SolicitudDTO>> obtenerTodos() {
        return ResponseEntity.ok(solicitudService.obtenerTodos());
    }
    
    @GetMapping("/all-including-deleted")
    public ResponseEntity<List<SolicitudDTO>> obtenerTodosInclusoEliminados() {
        return ResponseEntity.ok(solicitudService.obtenerTodosInclusoEliminados());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolicitudDTO> obtenerPorId(@PathVariable Long id) {
        SolicitudDTO solicitud = solicitudService.obtenerPorId(id);
        return solicitud != null ? ResponseEntity.ok(solicitud) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crearSolicitud(@RequestBody SolicitudDTO solicitudDTO) {
        try {
            SolicitudDTO solicitudCreada = solicitudService.crearSolicitud(solicitudDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(solicitudCreada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear la solicitud: " + e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarSolicitud(@PathVariable Long id, @RequestBody SolicitudDTO solicitudDTO) {
        try {
            SolicitudDTO solicitudActualizada = solicitudService.actualizarSolicitud(id, solicitudDTO);
            if (solicitudActualizada != null) {
                return ResponseEntity.ok(solicitudActualizada);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar la solicitud: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSolicitud(@PathVariable Long id) {
        solicitudService.eliminarSolicitud(id);
        return ResponseEntity.noContent().build();
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error en el servidor: " + e.getMessage());
    }
}
