package co.edu.javeriana.tufinca.controllers;

import co.edu.javeriana.tufinca.DTOS.SolicitudDTO;
import co.edu.javeriana.tufinca.services.SolicitudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/solicitudes")
public class SolicitudController {

    @Autowired
    private SolicitudService solicitudService;

    @GetMapping
    public List<SolicitudDTO> obtenerTodos() {
        return solicitudService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public SolicitudDTO obtenerPorId(@PathVariable Long id) {
        return solicitudService.obtenerPorId(id);
    }

    @PostMapping
    public SolicitudDTO crearSolicitud(@RequestBody SolicitudDTO solicitudDTO) {
        return solicitudService.crearSolicitud(solicitudDTO);
    }

    @DeleteMapping("/{id}")
    public void eliminarSolicitud(@PathVariable Long id) {
        solicitudService.eliminarSolicitud(id);
    }
}
