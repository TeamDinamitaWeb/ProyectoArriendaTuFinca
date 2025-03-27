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

import co.edu.javeriana.tufinca.DTOS.SolicitudDTO;
import co.edu.javeriana.tufinca.services.SolicitudService;

@RestController
@RequestMapping("/solicitudes")
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
    public ResponseEntity<SolicitudDTO> crearSolicitud(@RequestBody SolicitudDTO solicitudDTO) {
        return ResponseEntity.ok(solicitudService.crearSolicitud(solicitudDTO));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<SolicitudDTO> actualizarSolicitud(@PathVariable Long id, @RequestBody SolicitudDTO solicitudDTO) {
        SolicitudDTO solicitudActualizada = solicitudService.actualizarSolicitud(id, solicitudDTO);
        return solicitudActualizada != null ? ResponseEntity.ok(solicitudActualizada) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSolicitud(@PathVariable Long id) {
        solicitudService.eliminarSolicitud(id);
        return ResponseEntity.noContent().build();
    }
}
