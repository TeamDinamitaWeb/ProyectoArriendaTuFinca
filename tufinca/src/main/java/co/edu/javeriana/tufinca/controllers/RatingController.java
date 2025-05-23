package co.edu.javeriana.tufinca.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.javeriana.tufinca.DTOS.RatingDTO;
import co.edu.javeriana.tufinca.services.RatingService;

@RestController
@RequestMapping("/api/ratings")
@CrossOrigin(origins = "*")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @GetMapping
    public ResponseEntity<List<RatingDTO>> obtenerTodos() {
        return ResponseEntity.ok(ratingService.obtenerTodos());
    }
    
    @GetMapping("/all-including-deleted")
    public ResponseEntity<List<RatingDTO>> obtenerTodosInclusoEliminados() {
        return ResponseEntity.ok(ratingService.obtenerTodosInclusoEliminados());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RatingDTO> obtenerPorId(@PathVariable Long id) {
        RatingDTO rating = ratingService.obtenerPorId(id);
        return rating != null ? ResponseEntity.ok(rating) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<RatingDTO> crearRating(@RequestBody RatingDTO ratingDTO) {
        return ResponseEntity.ok(ratingService.crearRating(ratingDTO));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<RatingDTO> actualizarRating(@PathVariable Long id, @RequestBody RatingDTO ratingDTO) {
        RatingDTO ratingActualizado = ratingService.actualizarRating(id, ratingDTO);
        return ratingActualizado != null ? ResponseEntity.ok(ratingActualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRating(@PathVariable Long id) {
        ratingService.eliminarRating(id);
        return ResponseEntity.noContent().build();
    }
}
