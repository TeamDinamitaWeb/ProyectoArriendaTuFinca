package co.edu.javeriana.tufinca.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.javeriana.tufinca.DTOS.PropiedadDTO;
import co.edu.javeriana.tufinca.services.PropiedadService;

@RestController
@RequestMapping("/api/propiedades")
public class PropiedadController {

    @Autowired
    private PropiedadService propiedadService;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping
    public ResponseEntity<List<PropiedadDTO>> obtenerTodos() {
        return ResponseEntity.ok(propiedadService.obtenerTodos());
    }
    
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/all-including-deleted")
    public ResponseEntity<List<PropiedadDTO>> obtenerTodosInclusoEliminados() {
        return ResponseEntity.ok(propiedadService.obtenerTodosInclusoEliminados());
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/{id}")
    public ResponseEntity<PropiedadDTO> obtenerPorId(@PathVariable Long id) {
        PropiedadDTO propiedad = propiedadService.obtenerPorId(id);
        return propiedad != null ? ResponseEntity.ok(propiedad) : ResponseEntity.notFound().build();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping
    public ResponseEntity<?> crearPropiedad(@RequestBody PropiedadDTO propiedadDTO) {
        try {
            PropiedadDTO creada = propiedadService.crearPropiedad(propiedadDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(creada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Datos inválidos: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear propiedad: " + e.getMessage());
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPropiedad(@PathVariable Long id, @RequestBody PropiedadDTO propiedadDTO) {
        try {
            PropiedadDTO actualizada = propiedadService.actualizarPropiedad(id, propiedadDTO);
            return actualizada != null
                    ? ResponseEntity.ok(actualizada)
                    : ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Datos inválidos: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar propiedad: " + e.getMessage());
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPropiedad(@PathVariable Long id) {
        return propiedadService.eliminarPropiedad(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error inesperado: " + e.getMessage());
    }
} 