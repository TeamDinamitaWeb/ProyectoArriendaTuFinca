package co.edu.javeriana.tufinca.controllers;

import co.edu.javeriana.tufinca.DTOS.RatingDTO;
import co.edu.javeriana.tufinca.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @GetMapping
    public List<RatingDTO> obtenerTodos() {
        return ratingService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public RatingDTO obtenerPorId(@PathVariable Long id) {
        return ratingService.obtenerPorId(id);
    }

    @PostMapping
    public RatingDTO crearRating(@RequestBody RatingDTO ratingDTO) {
        return ratingService.crearRating(ratingDTO);
    }

    @DeleteMapping("/{id}")
    public void eliminarRating(@PathVariable Long id) {
        ratingService.eliminarRating(id);
    }
}
