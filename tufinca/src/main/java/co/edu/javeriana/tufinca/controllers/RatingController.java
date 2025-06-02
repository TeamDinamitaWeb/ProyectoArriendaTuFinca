package co.edu.javeriana.tufinca.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import co.edu.javeriana.tufinca.DTOS.RatingDTO;
import co.edu.javeriana.tufinca.services.RatingService;

@RestController
@RequestMapping("/api/ratings")
@CrossOrigin(origins = "*")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping
    public ResponseEntity<List<RatingDTO>> obtenerTodos() {
        return ResponseEntity.ok(ratingService.obtenerTodos());
    }
    
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/all-including-deleted")
    public ResponseEntity<List<RatingDTO>> obtenerTodosInclusoEliminados() {
        return ResponseEntity.ok(ratingService.obtenerTodosInclusoEliminados());
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/{id}")
    public ResponseEntity<RatingDTO> obtenerPorId(@PathVariable Long id) {
        RatingDTO rating = ratingService.obtenerPorId(id);
        return rating != null ? ResponseEntity.ok(rating) : ResponseEntity.notFound().build();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping
    public ResponseEntity<?> crearRating(@RequestBody RatingDTO ratingDTO) {
        try {
            RatingDTO creada = ratingService.crearRating(ratingDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(creada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error en datos de entrada: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear calificación: " + e.getMessage());
        }
    }
    
    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarRating(@PathVariable Long id, @RequestBody RatingDTO ratingDTO) {
        try {
            RatingDTO actualizada = ratingService.actualizarRating(id, ratingDTO);
            return actualizada != null
                    ? ResponseEntity.ok(actualizada)
                    : ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error en datos de entrada: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar calificación: " + e.getMessage());
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRating(@PathVariable Long id) {
        ratingService.eliminarRating(id);
        return ResponseEntity.noContent().build();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error inesperado: " + e.getMessage());
    }
}
