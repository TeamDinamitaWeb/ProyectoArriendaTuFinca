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

import co.edu.javeriana.tufinca.DTOS.PropiedadDTO;
import co.edu.javeriana.tufinca.services.PropiedadService;

@RestController
@RequestMapping("/propiedades")
@CrossOrigin(origins = "*")
public class PropiedadController {

    @Autowired
    private PropiedadService propiedadService;

    @GetMapping
    public ResponseEntity<List<PropiedadDTO>> obtenerTodos() {
        return ResponseEntity.ok(propiedadService.obtenerTodos());
    }
    
    @GetMapping("/all-including-deleted")
    public ResponseEntity<List<PropiedadDTO>> obtenerTodosInclusoEliminados() {
        return ResponseEntity.ok(propiedadService.obtenerTodosInclusoEliminados());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropiedadDTO> obtenerPorId(@PathVariable Long id) {
        PropiedadDTO propiedad = propiedadService.obtenerPorId(id);
        return propiedad != null ? ResponseEntity.ok(propiedad) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<PropiedadDTO> crearPropiedad(@RequestBody PropiedadDTO propiedadDTO) {
        return ResponseEntity.ok(propiedadService.crearPropiedad(propiedadDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PropiedadDTO> actualizarPropiedad(@PathVariable Long id, @RequestBody PropiedadDTO propiedadDTO) {
        PropiedadDTO propiedadActualizada = propiedadService.actualizarPropiedad(id, propiedadDTO);
        return propiedadActualizada != null ? ResponseEntity.ok(propiedadActualizada) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPropiedad(@PathVariable Long id) {
        return propiedadService.eliminarPropiedad(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
} 